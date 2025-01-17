package dk.anigif.kmp.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * A bit like [onSubscription] (but opposite), where [action] is called right before the sharing flow is stopped because
 * of no more subscribers. This is in contradiction to [onCompletion] where the action is called after the flow is
 * stopped and thus any calls might not finish since the scope if "partial" cancelled. Be aware that this however hasn't
 * been tested with more than one subscriber even though it hopefully works with more than one as well
 */
fun <DataType> SharedFlow<DataType>.onUnsubscription(
    scope: CoroutineScope,
    action: suspend () -> Unit
): SharedFlow<DataType> {
    return shareIn(
        scope = scope,
        started = { subscriptionCount ->
            channelFlow {
                val channel = Channel<SharingCommand>(
                    capacity = 1,
                    // Drop events while we wait for unsubscribe action to avoid stacking up events that's irrelevant
                    // because they got superseded
                    onBufferOverflow = BufferOverflow.DROP_OLDEST
                )

                launch {
                    var lastCommand: SharingCommand? = null
                    while (true) {
                        val command = channel.receive()

                        // Drop while not started, just like WhileSubscribed. We don't want to call action() as the
                        // first thing!
                        if (lastCommand == null && command != SharingCommand.START) {
                            continue
                        }

                        // DistinctUntilChanged. Also to avoid calling unsubscribe action several times in a row
                        if (command == lastCommand) {
                            continue
                        }

                        when (command) {
                            SharingCommand.START -> {}
                            SharingCommand.STOP -> action()
                            SharingCommand.STOP_AND_RESET_REPLAY_CACHE -> {}
                        }

                        send(command)

                        lastCommand = command
                    }
                }

                subscriptionCount
                    .map { count ->
                        if (count > 0) {
                            SharingCommand.START
                        } else {
                            SharingCommand.STOP
                        }
                    }
                    .collect { command ->
                        channel.send(command)
                    }
            }
        },
        replay = 0
    )
}

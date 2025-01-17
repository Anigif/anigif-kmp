package dk.anigif.kmp.flow

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Works a bit like [SharingStarted.WhileSubscribed], but has a delay after each startup such that the flow cannot be
 * stopped right away
 */
class WhileSubscribedWithMinimumLifetime(private val minimumMilliseconds: Long) : SharingStarted {

    override fun command(subscriptionCount: StateFlow<Int>): Flow<SharingCommand> = channelFlow {
        val channel = Channel<SharingCommand>(
            capacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST // Drop events while delaying, ignoring e.g. stop events
        )

        launch {
            var lastCommand: SharingCommand? = null
            while (true) {
                val command = channel.receive()

                // DistinctUntilChanged. But also important that we don't delay more than necessary if there's
                // several start commands in a row
                if (command == lastCommand) {
                    continue
                }

                send(command)

                when (command) {
                    SharingCommand.START ->
                        // Make sure there can't be sent any other events (mainly STOP) for a some amount of time after
                        // a start command
                        delay(minimumMilliseconds)
                    SharingCommand.STOP,
                    SharingCommand.STOP_AND_RESET_REPLAY_CACHE -> {
                    }
                }

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
}

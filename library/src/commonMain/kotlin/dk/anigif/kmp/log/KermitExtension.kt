package dk.anigif.kmp.log

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity

object KermitExtension {

    /**
     * Log a case that was unexpected and for instance should be sent to Crashlytics. This just creates a log entry with
     * severity of [Severity.Error] and a [UnexpectedErrorForLogging] (with [throwable] as cause) representing the
     * callstack of this log statement. Make sure to use a relevant [LogWriter] (like [UnexpectedLogWriter]) to add any
     * special handling of the log entry.
     *
     * Note that this is inline to ensure we get the correct place of cause, but this won't give the correct line
     * number. See youtrack.jetbrains.com/issue/KT-8628 for more information
     */
    @Suppress("NOTHING_TO_INLINE", "KotlinRedundantDiagnosticSuppress")
    inline fun Logger.unexpected(messageString: String, throwable: Throwable? = null, tag: String = this.tag) {
        log(
            severity = Severity.Error,
            tag = tag,
            throwable = UnexpectedErrorForLogging(message = messageString, cause = throwable),
            message = messageString
        )
    }
}

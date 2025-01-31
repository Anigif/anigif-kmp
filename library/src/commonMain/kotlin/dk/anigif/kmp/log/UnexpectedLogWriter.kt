package dk.anigif.kmp.log

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity

/**
 * A log writer that processes logs provided by [KermitExtension.unexpected]. Use [onLog] to handle the log events in
 * order to for instance send them to Crashlytics
 */
class UnexpectedLogWriter(
    private val onLog: (throwable: UnexpectedErrorForLogging) -> Unit
) : LogWriter() {

    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        if (throwable !is UnexpectedErrorForLogging) {
            return
        }

        onLog(throwable)
    }

    override fun isLoggable(tag: String, severity: Severity): Boolean {
        return severity >= Severity.Error
    }
}

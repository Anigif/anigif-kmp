package dk.anigif.kmp.log

/**
 * Representing an unexpected event. This is useful for getting the stacktrace and for instance be able to send it as a
 * non-fatal event to Crashlytics. Don't use it directly, but instead use [KermitExtension.unexpected]
 */
class UnexpectedErrorForLogging(message: String, cause: Throwable?) : Throwable(message, cause)

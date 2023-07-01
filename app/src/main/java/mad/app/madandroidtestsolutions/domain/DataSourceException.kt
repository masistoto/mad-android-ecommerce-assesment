package mad.app.madandroidtestsolutions.domain

sealed class DataSourceException(
    val messageResource: Any?
) : RuntimeException() {
    class Unexpected(messageResource: String?) : DataSourceException(messageResource)
    class Server(error: Error?) : DataSourceException(error)
}
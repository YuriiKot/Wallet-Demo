package xyz.argent.candidateassessment.basePresentation

sealed class ResourceState<out R> {
    object Initial : ResourceState<Nothing>()
    object Loading : ResourceState<Nothing>()

    class Success<R>(
        val value: R,
    ) : ResourceState<R>()

    class Error<R>(
        val error: Throwable,
    ) : ResourceState<R>()
}

suspend fun <R> catchResource(f: suspend () -> R): ResourceState<R> {
    return try {
        ResourceState.Success(f())
    } catch (e: Throwable) {
        ResourceState.Error(e)
    }
}
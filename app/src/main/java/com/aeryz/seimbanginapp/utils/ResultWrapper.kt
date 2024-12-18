package com.aeryz.seimbanginapp.utils

import com.aeryz.seimbanginapp.utils.exception.ApiException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException

sealed class ResultWrapper<T>(
    val payload: T? = null,
    val message: String? = null,
    val exception: Exception? = null
) {
    class Success<T>(data: T) : ResultWrapper<T>(data)
    class Error<T>(exception: Exception?, data: T? = null) :
        ResultWrapper<T>(data, exception = exception)

    class Empty<T>(data: T? = null) : ResultWrapper<T>(data)
    class Loading<T>(data: T? = null) : ResultWrapper<T>(data)
}

fun <T> ResultWrapper<T>.proceedWhen(
    doOnSuccess: ((resource: ResultWrapper<T>) -> Unit)? = null,
    doOnError: ((resource: ResultWrapper<T>) -> Unit)? = null,
    doOnLoading: ((resource: ResultWrapper<T>) -> Unit)? = null,
    doOnEmpty: ((resource: ResultWrapper<T>) -> Unit)? = null
) {
    when (this) {
        is ResultWrapper.Success -> doOnSuccess?.invoke(this)
        is ResultWrapper.Error -> doOnError?.invoke(this)
        is ResultWrapper.Loading -> doOnLoading?.invoke(this)
        is ResultWrapper.Empty -> doOnEmpty?.invoke(this)
    }
}

suspend fun <T> proceed(block: suspend () -> T): ResultWrapper<T> {
    return try {
        val result = block.invoke()
        if (result is Collection<*> && result.isEmpty()) {
            ResultWrapper.Empty(result)
        } else {
            ResultWrapper.Success(result)
        }
    } catch (e: Exception) {
        ResultWrapper.Error<T>(exception = Exception(e))
    }
}

fun <T> proceedFlow(block: suspend () -> T): Flow<ResultWrapper<T>> {
    return flow<ResultWrapper<T>> {
        val result = block.invoke()
        if (result != null) {
            emit(
                when {
                    result is Collection<*> && result.isEmpty() -> ResultWrapper.Empty(result)
                    result.hasEmptyDataProperty() -> ResultWrapper.Empty(result)
                    else -> ResultWrapper.Success(result)
                }
            )
        }
    }.catch { e ->
        val exception = when (e) {
            is HttpException -> ApiException(e.message().orEmpty(), e.code(), e.response())
            else -> Exception(e)
        }
        emit(ResultWrapper.Error<T>(exception = exception))
    }.onStart {
        emit(ResultWrapper.Loading())
    }
}

fun Any.hasEmptyDataProperty(): Boolean {
    return this::class.java.declaredFields.any { field ->
        field.isAccessible = true
        field.name == "data" && (field.get(this) as? Collection<*>)?.isEmpty() == true
    }
}

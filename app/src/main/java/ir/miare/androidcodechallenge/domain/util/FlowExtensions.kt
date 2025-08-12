package ir.miare.androidcodechallenge.domain.util

import ir.miare.androidcodechallenge.data.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

fun <T> safeApiCallFlow(apiCall: suspend () -> T): Flow<Result<T>> = flow {
    emit(Result.Loading)
    try {
        val data = apiCall()
        emit(Result.Success(data))
    } catch (e: IOException) {
        emit(Result.Error("Network Error: ${e.localizedMessage ?: "Unknown error"}"))
    } catch (e: Exception) {
        emit(Result.Error("Unexpected Error: ${e.localizedMessage ?: "Unknown error"}"))
    }
}

package ir.miare.androidcodechallenge.core.presentation

sealed interface UiState<T> {
    class Idle<T> : UiState<T>
    class Loading<T> : UiState<T>
    data class Success<T>(val data: T) : UiState<T>
    data class Failure<T>(val error: UiText) : UiState<T>
}
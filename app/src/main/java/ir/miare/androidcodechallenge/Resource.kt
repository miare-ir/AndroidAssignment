package ir.miare.androidcodechallenge

sealed class Resource<T>(
    val code: Int? = null,
    val message: String? = null,
    val data: T? = null
) {
    class Success<T>(code:Int? = null, data: T) : Resource<T>(code,null, data)
    class Error<T>(code:Int? = null, message: String? = null, data: T? = null) : Resource<T>(code, message, data)
}

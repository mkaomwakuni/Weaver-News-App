package dev.mkao.weaver.util

sealed class Assets<T>(val data:T? = null, val message :String? = null)
{
	class ErrorResponse<T>(message: String?) : Assets<T>(message = message)
	class SuccessResponse<T>(data: T?) :Assets<T>(data = data)
}
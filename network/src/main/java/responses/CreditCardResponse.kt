package responses

data class ApiResponse<T>(
    val success: Boolean, // Indicates if the API call was successful
    val message: String, // A message, typically used for error or success messages
    val errorCode: Int? = null, // An optional error code, used in case of an error
    val errorMessage: String? = null, // An optional error message, used in case of an error
    val data: T // The actual data returned by the API, of generic type T
)
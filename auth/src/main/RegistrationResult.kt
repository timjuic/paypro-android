
sealed class RegistrationResult {
    data class Success(val message: String) : RegistrationResult()
    data class Error(
        val message: String,
        val errorCode: Int,
        val errorMessage: String
    ) : RegistrationResult()

    companion object {
        fun fromJson(json: JSONObject): RegistrationResult {
            val success = json.getAsBoolean("success", false)
            val message = json.getAsString("message", "")
            val errorCode = json.getAsInt("error_code", -1)
            val errorMessage = json.getAsString("error_message", "")

            return if (success) {
                Success(message)
            } else {
                Error(message, errorCode, errorMessage)
            }
        }
    }
}

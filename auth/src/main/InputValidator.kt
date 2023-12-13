import java.util.regex.Pattern

class InputValidator {
    fun isUsernameValid(username: String): Boolean {
        // Implement username validation logic
        return username.isNotEmpty()
    }

    fun isEmailValid(email: String): Boolean {
        // Implement email validation using regex
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }

    fun isPasswordValid(password: String, passwordRepeat: String): Boolean {
        // Implement password validation logic (at least 8 characters)
        return password.length >= 8 && password == passwordRepeat
    }
}

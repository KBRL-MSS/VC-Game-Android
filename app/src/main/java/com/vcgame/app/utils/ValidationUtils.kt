package com.vcgame.app.utils

object ValidationUtils {

    /**
     * Validates a password based on common criteria:
     * - Minimum 8 characters.
     * - Contains at least one letter (uppercase or lowercase).
     * - Contains at least one digit.
     * @return null if valid, or an error message string if invalid.
     */
    fun validatePassword(password: String): String? {
        if (password.length < 8) {
            return "Password must be at least 8 characters long."
        }
        if (!password.contains(Regex("[A-Za-z]"))) {
            return "Password must contain at least one letter."
        }
        if (!password.contains(Regex("[0-9]"))) {
            return "Password must contain at least one digit."
        }
        return null // Password is valid
    }

    fun validateUsername(username: String): String? {
        if (username.isEmpty()) {
            return "Username cannot be empty."
        }
        // Regex: ^[a-zA-Z.]+$
        // ^        - start of the string
        // [a-zA-Z.] - matches any letter (upper or lower case) or a dot
        // +        - one or more times
        // $        - end of the string
        if (!username.matches(Regex("^[a-zA-Z.]+$"))) {
            return "Username can only contain alphabets and dots."
        }
        return null // Username is valid
    }
}

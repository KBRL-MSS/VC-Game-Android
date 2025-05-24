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
}

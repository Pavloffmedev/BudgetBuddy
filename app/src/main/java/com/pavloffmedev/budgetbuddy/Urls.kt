package com.pavloffmedev.budgetbuddy

object Urls {
    private const val DOMAIN = "https://adcoinapi.su/API6/budget_buddy"

    const val SEND_CODE = "$DOMAIN/account/send_code.php"
    const val CONFIRM_CODE = "$DOMAIN/account/validate_code.php"
    const val GET_USER_DATA = "$DOMAIN/account/get_user_data.php"
    const val APPLY_START_SETTINGS = "$DOMAIN/settings/apply_start_settings.php"
}
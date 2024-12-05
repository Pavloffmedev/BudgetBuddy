package com.pavloffmedev.budgetbuddy

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.OvershootInterpolator
import android.view.inputmethod.InputMethodManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.regex.Pattern

/**
 * Преобразование SQL datetime в формат "Сегодня, 12:12"
 */
fun String.formatTime(todayText: String, yesterdayText: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormatTime = SimpleDateFormat("HH:mm", Locale.getDefault())
    val outputFormatDate = SimpleDateFormat("dd MMMM", Locale.getDefault())

    val transactionDate = inputFormat.parse(this) ?: return this
    val currentDate = Calendar.getInstance()

    val transactionCalendar = Calendar.getInstance().apply {
        time = transactionDate
    }

    return when {
        currentDate.get(Calendar.YEAR) == transactionCalendar.get(Calendar.YEAR) &&
                currentDate.get(Calendar.DAY_OF_YEAR) == transactionCalendar.get(Calendar.DAY_OF_YEAR) ->
            "$todayText, ${outputFormatTime.format(transactionDate)}"

        currentDate.get(Calendar.YEAR) == transactionCalendar.get(Calendar.YEAR) &&
                currentDate.get(Calendar.DAY_OF_YEAR) - transactionCalendar.get(Calendar.DAY_OF_YEAR) == 1 ->
            "$yesterdayText, ${outputFormatTime.format(transactionDate)}"

        else -> "${outputFormatDate.format(transactionDate)}, ${outputFormatTime.format(transactionDate)}"
    }
}

/**
 * Вернет -1, если не выбрано
 */
fun ChipGroup.getCheckedIndex(): Int {
    val checkedId = this.checkedChipId
    return if (checkedId == View.NO_ID) {
        -1
    }
    else {
        this.indexOfChild(findViewById<Chip>(checkedChipId))
    }
}


/**
 * Валидировать электронную почту
 */
fun isEmailValid(email: String): Boolean {
    return Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    ).matcher(email).matches()
}


/**
 * Проигрывание анимауии нажатия
 */
fun View.startAnimationClick() {
    animate().setInterpolator(OvershootInterpolator()).scaleX(0.95f).scaleY(0.95f).setDuration(200)
        .withEndAction {
            animate().setInterpolator(OvershootInterpolator()).scaleX(1f).scaleY(1f)
                .setDuration(200).start()
        }
}


/**
 * Анимация появления лэйаута как в профиле пользователя при постановке лайка или подписки
 */
fun View.startAnimationOpenLayout() {
    visible(true)
    scaleX = 0.5f
    scaleY = 0.5f
    alpha = 0f
    animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(400)
        .setInterpolator(OvershootInterpolator()).start()
}


/**
 * Анимация исчезновения лэйаута как в профиле пользователя при постановке лайка или подписки
 */
fun View.startAnimationCloseLayout() {
    animate().scaleX(0.5f).scaleY(0.5f).alpha(0f)
        .setDuration(400).setInterpolator(OvershootInterpolator()).withEndAction {
            visible(false)
        }.start()
}


/**
 * Изменение видимости View путем передачи булевого значения
 */
fun View.visible(status: Boolean) {
    visibility = if (status) {
        View.VISIBLE
    } else {
        View.GONE
    }
}


/**
 * Закрыть клавиатуру
 */
fun Activity.closeKeyboard() {
    this.currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
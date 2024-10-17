package com.pavloffmedev.budgetbuddy

import android.view.View
import android.view.animation.OvershootInterpolator
import java.util.regex.Pattern


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
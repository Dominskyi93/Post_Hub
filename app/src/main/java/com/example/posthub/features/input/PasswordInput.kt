package com.example.posthub.features.input

import android.content.Context
import android.util.AttributeSet
import com.example.posthub.R

class PasswordInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : CustomInputLayout(context, attrs, defStyleAttr) {

    override val errorMessageId: Int = R.string.password_incorrect_message

    override fun innerIsValid(): Boolean {
        return text().matches(Regex(PASSWORD_PATTERN))
    }

    companion object {
        private const val PASSWORD_PATTERN =
            "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{6,}\$"
    }
}
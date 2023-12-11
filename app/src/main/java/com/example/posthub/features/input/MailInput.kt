package com.example.posthub.features.input

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import com.example.posthub.R

class MailInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : CustomInputLayout(context, attrs, defStyleAttr) {
    override val errorMessageId = R.string.incorrect_email_format

    override fun innerIsValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(text()).matches()
    }
}
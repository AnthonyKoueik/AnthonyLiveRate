package com.anthony.revolut.utils

import android.text.Editable
import android.text.TextWatcher
import java.math.BigDecimal


/**
 * Created by Anthony Koueik on 12/6/2019.
 * KOA
 * anthony.koueik@gmail.com
 *
 * watch the amount entered and update the current amount in the view Model
 */
class EditTextInputWatcher(private val newAmount: (Double) -> Unit) :
    TextWatcher {

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (!s.isNullOrEmpty()) {
            try {
                newAmount(s.toString().toDouble())
            } catch (x: Exception) {
                newAmount(0.0)
            }
        }else {
            newAmount(0.0)
        }
    }

}
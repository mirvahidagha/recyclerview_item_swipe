package com.bank.actionmode

import android.content.res.Resources
import android.util.TypedValue
import java.time.LocalDateTime


val today
    get() = LocalDateTime.now().toString()


val Number.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

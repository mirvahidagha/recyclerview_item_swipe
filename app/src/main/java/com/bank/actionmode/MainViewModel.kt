package com.bank.actionmode

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bank.actionmode.MainActivity.Companion.DESIRED_HEIGHT_CROP_PERCENT
import com.bank.actionmode.MainActivity.Companion.DESIRED_WIDTH_CROP_PERCENT

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val imageCropPercentages = MutableLiveData<Pair<Int, Int>>()
        .apply { value = Pair(DESIRED_HEIGHT_CROP_PERCENT, DESIRED_WIDTH_CROP_PERCENT) }

    var result = MutableLiveData<String?>()

    fun initialState() {
        result.value = null
    }

}
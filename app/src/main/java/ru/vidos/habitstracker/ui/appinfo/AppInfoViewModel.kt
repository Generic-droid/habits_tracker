package ru.vidos.habitstracker.ui.appinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppInfoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is appinfo Fragment"
    }
    val text: LiveData<String> = _text
}
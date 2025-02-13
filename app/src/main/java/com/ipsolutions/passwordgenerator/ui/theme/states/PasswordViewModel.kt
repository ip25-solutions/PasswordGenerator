package com.ipsolutions.passwordgenerator.ui.theme.states


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class PasswordViewModel : ViewModel() {
    private val _password = MutableStateFlow("🔒 Generar contraseña")
    val password: StateFlow<String> = _password

    private val _passwordLength = MutableStateFlow(12)
    val passwordLength: StateFlow<Int> = _passwordLength

    private val _includeNumbers = MutableStateFlow(true)
    val includeNumbers: StateFlow<Boolean> = _includeNumbers

    private val _includeSymbols = MutableStateFlow(true)
    val includeSymbols: StateFlow<Boolean> = _includeSymbols

    fun updateLength(length: Int) {
        _passwordLength.value = length
    }

    fun toggleNumbers() {
        _includeNumbers.value = !_includeNumbers.value
    }

    fun toggleSymbols() {
        _includeSymbols.value = !_includeSymbols.value
    }

    fun generatePassword() {
        val chars = mutableListOf<CharRange>('A'..'Z', 'a'..'z')

        if (_includeNumbers.value) chars.add('0'..'9')
        val symbolList = "!@#$%^&*()_+-=[]{}".toList()
        val allChars = chars.flatten() + if (_includeSymbols.value) symbolList else emptyList()

        _password.value = (1.._passwordLength.value)
            .map { allChars[Random.nextInt(allChars.size)] }
            .joinToString("")
    }
}

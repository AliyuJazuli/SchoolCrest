package com.hydev.schoolcrest.core.theme

import com.hydev.schoolcrest.core.model.AppThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Global reactive theme manager holding the current app-wide theme preference.
 */
object ThemeState {
    private val _themeMode = MutableStateFlow(AppThemeMode.SYSTEM_DEFAULT)
    val themeMode: StateFlow<AppThemeMode> = _themeMode.asStateFlow()

    fun setThemeMode(mode: AppThemeMode) {
        _themeMode.value = mode
    }
}

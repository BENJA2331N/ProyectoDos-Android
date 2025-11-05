package com.example.milsaboresapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaboresapp.navigation.AppRoute
import com.example.milsaboresapp.navigation.NavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _navEvents = MutableSharedFlow<NavigationEvent>()
    val navEvents = _navEvents.asSharedFlow()

    fun navigateTo(appRoute: AppRoute) {
        viewModelScope.launch {
            _navEvents.emit(NavigationEvent.NavigateTo(appRoute))
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navEvents.emit(NavigationEvent.PopBackStack)
        }
    }

    fun navigateUp() {
        viewModelScope.launch {
            _navEvents.emit(NavigationEvent.NavigateUp)
        }
    }
}
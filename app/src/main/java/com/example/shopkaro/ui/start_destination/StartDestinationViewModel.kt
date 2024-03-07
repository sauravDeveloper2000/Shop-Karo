package com.example.shopkaro.ui.start_destination

import androidx.lifecycle.ViewModel
import com.example.shopkaro.navigation.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StartDestinationViewModel @Inject constructor() : ViewModel() {
    var _startDestination = MutableStateFlow<Destinations>(value = Destinations.PreAuth)
        private set

    fun definesStartDestination(
        destinations: Destinations
    ) {
        _startDestination.value = destinations
    }
}
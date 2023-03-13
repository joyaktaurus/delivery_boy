package app.com.patricksdeliveryboy.shiftDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.com.patricksdeliveryboy.repository.ShiftRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShiftDetailsViewModel @Inject constructor(repository: ShiftRepository): ViewModel() {
    val dataListFlow = repository.getListingStream().cachedIn(viewModelScope)
}
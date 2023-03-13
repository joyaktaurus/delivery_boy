package app.com.patricksdeliveryboy.weeklyEarnings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.com.patricksdeliveryboy.repository.EarnedDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeeklyEarningsViewModel @Inject constructor(repository: EarnedDetailsRepository) : ViewModel() {
    val dataListFlow = repository.getListingStream().cachedIn(viewModelScope)
}
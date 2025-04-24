package ir.miare.androidcodechallenge

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel(), MainContract {

    private val mutableState = MutableStateFlow(MainContract.State())
    override val state: StateFlow<MainContract.State> = mutableState.asStateFlow()

    override fun event(event: MainContract.Event) = when(event) {
        is MainContract.Event.OnSortTypeSelected -> {
            mutableState.update { prevState ->
                prevState.copy(
                    selectedSortType = event.sortType
                )
            }
        }
    }

    init {

    }

}
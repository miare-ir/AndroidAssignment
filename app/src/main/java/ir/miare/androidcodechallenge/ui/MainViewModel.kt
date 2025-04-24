package ir.miare.androidcodechallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.Resource
import ir.miare.androidcodechallenge.domain.FakeData
import ir.miare.androidcodechallenge.domain.GetRankingsUseCase
import ir.miare.androidcodechallenge.domain.Player
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRankingsUseCase: GetRankingsUseCase
) : ViewModel(), MainContract {

    private val mutableState = MutableStateFlow(MainContract.State())
    override val state: StateFlow<MainContract.State> = mutableState.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _sortedList = MutableStateFlow<List<FakeData>>(emptyList())
    val sortedList = _sortedList.asStateFlow()

    override fun event(event: MainContract.Event) = when(event) {
        is MainContract.Event.OnSortTypeSelected -> {
            mutableState.update { prevState ->
                prevState.copy(
                    selectedSortType = event.sortType
                )
            }

            when(event.sortType){
                SortType.RANK -> {
                    _sortedList.update { mutableState.value.topPlayers.sortedByDescending {
                        it.league.rank
                    } }
                }
                SortType.MOST -> {
                    _sortedList.update { mutableState.value.topPlayers.sortedByDescending {
                        it.players.maxOf { it.totalGoal }
                    } }
                }
                SortType.AVERAGE -> {
                    _sortedList.update { mutableState.value.topPlayers.sortedByDescending {
                        it.players.sumOf { it.totalGoal }.div(it.league.totalMatches.toFloat())
                    } }
                }
                SortType.None -> {
                    _sortedList.update { mutableState.value.topPlayers }
                }
            }
        }
    }

    init {
        getRankings()
    }

    private fun getRankings() {
        getRankingsUseCase()
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        result.message?.let {
                            _errorMessage.emit(it)
                        }
                    }

                    is Resource.Success -> {

                        result.data?.let {
                            mutableState.update { prevState ->
                                prevState.copy(
                                    topPlayers = it
                                )
                            }
                            _sortedList.update { mutableState.value.topPlayers }
                        }

                    }
                }
            }
            .catch { exception ->
                exception.message?.let {
                    _errorMessage.emit(it)
                }
            }
            .launchIn(viewModelScope)
    }

}
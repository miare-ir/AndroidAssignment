package ir.miare.androidcodechallenge.presentation.league_data_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.data.model.LeagueData
import ir.miare.androidcodechallenge.domain.use_case.GetLeagueDataUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getLeagueDataUseCase: GetLeagueDataUseCase
) : ViewModel() {

    private val _sort = MutableStateFlow<RankingSort>(RankingSort.None)
    val sort: StateFlow<RankingSort> = _sort.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val leaguesPagingFlow: Flow<PagingData<LeagueData>> =
        sort.flatMapLatest { sort ->
            getLeagueDataUseCase(pageSize = 5, sort = sort)
        }.cachedIn(viewModelScope)

    fun setSort(newSort: RankingSort) {
        _sort.value = newSort
    }
}

package ir.miare.androidcodechallenge.presentation.league_data_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.data.model.LeagueData
import ir.miare.androidcodechallenge.domain.use_case.GetLeagueDataUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getLeagueDataUseCase: GetLeagueDataUseCase
) : ViewModel() {

    val leaguesPagingFlow: Flow<PagingData<LeagueData>> =
        getLeagueDataUseCase(pageSize = 1) // load 5 per page
            .cachedIn(viewModelScope)
}

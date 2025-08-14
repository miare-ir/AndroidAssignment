package ir.miare.androidcodechallenge.presentation.followed_players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.data.model.Player
import ir.miare.androidcodechallenge.data.model.Team
import ir.miare.androidcodechallenge.domain.use_case.GetFollowedPlayersUseCase
import ir.miare.androidcodechallenge.domain.use_case.UnfollowPlayerUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowedPlayersViewModel @Inject constructor(
    private val getFollowedPlayersUseCase: GetFollowedPlayersUseCase,
    private val unfollowPlayerUseCase: UnfollowPlayerUseCase
) : ViewModel() {

    val followedPlayers: StateFlow<List<Player>> = getFollowedPlayersUseCase()
        .map { entities ->
            entities.map { entity ->
                Player(
                    name = entity.playerName,
                    totalGoal = entity.totalGoal,
                    team = Team(
                        name = entity.teamName,
                        rank = entity.teamRank
                    ),
                    isFollowed = true
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun unfollow(playerName: String) {
        viewModelScope.launch {
            unfollowPlayerUseCase(playerName)
        }
    }
}

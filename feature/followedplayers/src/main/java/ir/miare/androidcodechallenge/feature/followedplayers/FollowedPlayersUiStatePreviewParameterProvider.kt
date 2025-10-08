package ir.miare.androidcodechallenge.feature.followedplayers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.Team

class FollowedPlayersUiStatePreviewParameterProvider : PreviewParameterProvider<FollowedPlayersUiState> {
    override val values: Sequence<FollowedPlayersUiState> = sequenceOf(
        FollowedPlayersUiState.Success(
            players = demoPlayers
        ),
    )
}

val demoPlayers = listOf(
    Player(
        name = "Kylian Mbappé",
        team = Team(name = "Paris Saint-Germain", rank = 1),
        totalGoal = 27,
        isFollowed = true
    ),
    Player(
        name = "Erling Haaland",
        team = Team(name = "Manchester City", rank = 2),
        totalGoal = 25
    ),
    Player(
        name = "Robert Lewandowski",
        team = Team(name = "Barcelona", rank = 3),
        totalGoal = 23
    ),
    Player(
        name = "Harry Kane",
        team = Team(name = "Bayern Munich", rank = 1),
        totalGoal = 29,
        isFollowed = true
    ),
    Player(
        name = "Vinícius Júnior",
        team = Team(name = "Real Madrid", rank = 1),
        totalGoal = 21
    ),
    Player(
        name = "Bukayo Saka",
        team = Team(name = "Arsenal", rank = 2),
        totalGoal = 18
    ),
    Player(
        name = "Lautaro Martínez",
        team = Team(name = "Inter Milan", rank = 1),
        totalGoal = 22
    ),
    Player(
        name = "Mohamed Salah",
        team = Team(name = "Liverpool", rank = 3),
        totalGoal = 20,
        isFollowed = true
    ),
    Player(
        name = "Jude Bellingham",
        team = Team(name = "Real Madrid", rank = 1),
        totalGoal = 19
    ),
    Player(
        name = "Antoine Griezmann",
        team = Team(name = "Atletico Madrid", rank = 4),
        totalGoal = 17
    )
)

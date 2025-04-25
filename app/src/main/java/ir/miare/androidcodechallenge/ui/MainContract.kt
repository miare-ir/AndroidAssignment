package ir.miare.androidcodechallenge.ui

import ir.miare.androidcodechallenge.domain.FakeData
import ir.miare.androidcodechallenge.domain.Player

interface MainContract :
    UnidirectionalViewModel<MainContract.Event, MainContract.State> {

    data class State(
        var selectedSortType: SortType = SortType.None,
        var topPlayers: List<FakeData> = emptyList(),
        var selectedPlayer: Player? = null
    )

    sealed class Event {
        data class OnSortTypeSelected(var sortType: SortType): Event()
        data class OnPlayerClicked(var player: Player): Event()
    }
}

package ir.miare.androidcodechallenge.ui

import ir.miare.androidcodechallenge.domain.FakeData

interface MainContract :
    UnidirectionalViewModel<MainContract.Event, MainContract.State> {

    data class State(
        var selectedSortType: SortType = SortType.None,
        var topPlayers: List<FakeData> = emptyList()
    )

    sealed class Event {
        data class OnSortTypeSelected(var sortType: SortType): Event()
    }
}

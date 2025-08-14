package ir.miare.androidcodechallenge.domain.usecase

import android.util.Log
import ir.miare.androidcodechallenge.domain.model.PlayerWithContext

object PaginatePlayers {
    private const val PAGE_SIZE = 20
    private var page = 0
    fun paginate(players: List<PlayerWithContext>): List<PlayerWithContext> {
        page += 1
        val size = page * PAGE_SIZE
        return (if (size >= players.size) players
        else players.subList(0, page * PAGE_SIZE))
    }

    fun reset() {
        page = 0
    }
}

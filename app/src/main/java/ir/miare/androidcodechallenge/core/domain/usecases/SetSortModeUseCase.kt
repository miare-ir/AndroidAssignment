package ir.miare.androidcodechallenge.core.domain.usecases

import ir.miare.androidcodechallenge.core.data.repository.PlayerRepository
import ir.miare.androidcodechallenge.core.model.SortMode
import javax.inject.Inject

class SetSortModeUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(mode: SortMode) = repository.setSortMode(mode)
}

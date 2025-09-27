package ir.miare.androidcodechallenge.core.domain.usecases

import ir.miare.androidcodechallenge.core.data.repository.PlayerRepository
import ir.miare.androidcodechallenge.core.model.SortMode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSortModeUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    operator fun invoke(): Flow<SortMode> = repository.sortMode
}

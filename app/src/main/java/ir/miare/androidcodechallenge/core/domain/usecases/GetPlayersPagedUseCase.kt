package ir.miare.androidcodechallenge.core.domain.usecases

import ir.miare.androidcodechallenge.core.data.repository.PlayerRepository
import ir.miare.androidcodechallenge.core.model.networkmodel.PlayerModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlayersPagedUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    operator fun invoke(pageSize: Int, offset: Int): Flow<List<PlayerModel>> =
        repository.players(pageSize, offset)
}

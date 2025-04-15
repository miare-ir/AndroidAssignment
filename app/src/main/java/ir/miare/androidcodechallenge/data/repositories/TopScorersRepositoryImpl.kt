package ir.miare.androidcodechallenge.data.repositories

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ir.miare.androidcodechallenge.data.mappers.toDomain
import ir.miare.androidcodechallenge.data.models.TopScorersResponse
import ir.miare.androidcodechallenge.domain.models.TopScorers
import ir.miare.androidcodechallenge.domain.repositories.TopScorersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TopScorersRepositoryImpl @Inject constructor(
    private val client: HttpClient,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) : TopScorersRepository {
    override suspend fun getTopScorers(): Result<List<TopScorers>> = withContext(coroutineContext) {
        return@withContext try {
            val response = client.get("topscorers")
            when (response.status) {
                HttpStatusCode.OK -> {
                    val topScorersResponse: List<TopScorersResponse> = response.body()
                    val topScorersList = topScorersResponse.map { it.toDomain() }
                    Result.success(topScorersList)
                }

                else -> {
                    Result.failure(Exception("Error fetching data: ${response.status}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
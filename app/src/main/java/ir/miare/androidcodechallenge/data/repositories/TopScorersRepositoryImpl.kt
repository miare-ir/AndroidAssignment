package ir.miare.androidcodechallenge.data.repositories

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ir.miare.androidcodechallenge.data.mappers.toDomain
import ir.miare.androidcodechallenge.data.models.TopScorersResponse
import ir.miare.androidcodechallenge.domain.models.TopScorers
import ir.miare.androidcodechallenge.domain.repositories.TopScorersRepository
import javax.inject.Inject

class TopScorersRepositoryImpl @Inject constructor(
    private val client: HttpClient
): TopScorersRepository {
    override suspend fun getTopScorers(): Result<TopScorers> {
        return try {
            val response = client.get("https://test_baseurl.com/v2/topscorers")
            when(response.status) {
                HttpStatusCode.OK -> {
                    val topScorersResponse = response.body<TopScorersResponse>()
                    val topScorersList = topScorersResponse.toDomain()
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
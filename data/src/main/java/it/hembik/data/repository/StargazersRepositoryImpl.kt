package it.hembik.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import it.hembik.data.api.HttpClient
import it.hembik.data.datasource.StargazersDatasource
import it.hembik.data.dto.toStargazer
import it.hembik.domain.model.Stargazer
import it.hembik.domain.repository.StargazersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StargazersRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : StargazersRepository {

    override suspend fun getStargazers(
        owner: String,
        repoName: String
    ): Flow<PagingData<Stargazer>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { StargazersDatasource(httpClient, owner, repoName) },
            initialKey = 1
        ).flow.map { pagingData ->
            pagingData.map { stargazerDto ->
                stargazerDto.toStargazer()
            }
        }
    }
}
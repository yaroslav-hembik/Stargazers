package it.hembik.domain.repository

import androidx.paging.PagingData
import it.hembik.domain.model.Stargazer
import kotlinx.coroutines.flow.Flow

interface StargazersRepository {
    suspend fun getStargazers(owner: String, repoName: String): Flow<PagingData<Stargazer>>
}
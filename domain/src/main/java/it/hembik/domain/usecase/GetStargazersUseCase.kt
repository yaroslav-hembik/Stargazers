package it.hembik.domain.usecase

import androidx.paging.PagingData
import it.hembik.domain.model.Stargazer
import it.hembik.domain.repository.StargazersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStargazersUseCase @Inject constructor(
    private val repository: StargazersRepository,
) {
    operator fun invoke(
        owner: String,
        repoName: String
    ): Flow<PagingData<Stargazer>> = flow {
        repository.getStargazers(owner, repoName).collect {
            emit(it)
        }
    }
}
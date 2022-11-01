package it.hembik.stargazers.domain

import io.mockk.mockk
import it.hembik.domain.repository.StargazersRepository
import it.hembik.domain.usecase.GetStargazersUseCase
import it.hembik.stargazers.tools.FakerStargazers
import org.junit.Before

class GetStargazersUseCaseTest {
    private val faker = FakerStargazers()
    private val stargazers = faker.getStargazers()
    private val fakeOwner = "fake-owner"
    private val fakeRepoName = "fake-repo-name"

    private lateinit var useCase: GetStargazersUseCase

    @Before
    fun setUp() {
        val stargazersRepositoryMock = mockk<StargazersRepository>()

        useCase = GetStargazersUseCase(
            stargazersRepositoryMock
        )
    }
}
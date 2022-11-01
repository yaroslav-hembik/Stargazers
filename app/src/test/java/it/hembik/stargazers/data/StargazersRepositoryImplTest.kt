package it.hembik.stargazers.data

import io.mockk.coEvery
import io.mockk.mockk
import it.hembik.data.api.HttpClient
import it.hembik.data.repository.StargazersRepositoryImpl
import it.hembik.domain.repository.StargazersRepository
import it.hembik.stargazers.tools.FakerStargazers
import org.junit.Before

class StargazersRepositoryImplTest {

    private val fakePage = 1
    private val fakeOwner = "fake-owner"
    private val fakeRepoName = "fake-repo-name"

    private val faker = FakerStargazers()
    private lateinit var repository: StargazersRepository

    @Before
    fun setUp() {
        val httpClientMock = mockk<HttpClient>()
        coEvery {
            httpClientMock.api.getStargazers(
                owner = fakeOwner, repo = fakeRepoName, page = fakePage
            )
        } returns faker.getStargazers()

        repository = StargazersRepositoryImpl(
            httpClient = httpClientMock,
        )
    }
}
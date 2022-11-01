package it.hembik.stargazers.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import it.hembik.data.api.HttpClient
import it.hembik.data.datasource.StargazersDatasource
import it.hembik.stargazers.rules.MainCoroutineRule
import it.hembik.stargazers.tools.FakerStargazers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class StargazersDataSourceTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = MainCoroutineRule()

    private val faker = FakerStargazers()
    private val stargazers = faker.getStargazers()
    private lateinit var httpClientMock: HttpClient
    lateinit var stargazersDataSource: StargazersDatasource

    @Before
    fun setup() {
        httpClientMock = mockk()
        stargazersDataSource = StargazersDatasource(httpClientMock, "", "")
    }
}
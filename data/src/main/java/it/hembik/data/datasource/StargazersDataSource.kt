package it.hembik.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import it.hembik.data.api.HttpClient
import it.hembik.data.dto.StargazerDTO
import it.hembik.domain.exceptions.StargazersNotFoundException
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 30
class StargazersDatasource(
    private val httpClient: HttpClient,
    private val owner: String,
    private val repoName: String,
) : PagingSource<Int, StargazerDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StargazerDTO> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        return try {
            val response =
                httpClient.api.getStargazers(owner = owner, repo = repoName, page = params.loadSize)
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = response,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            if (exception.code() == 404) {
                return LoadResult.Error(StargazersNotFoundException())
            } else {
                return LoadResult.Error(exception)
            }
        }

    }

    override fun getRefreshKey(state: PagingState<Int, StargazerDTO>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
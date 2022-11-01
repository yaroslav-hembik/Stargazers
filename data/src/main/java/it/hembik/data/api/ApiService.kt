package it.hembik.data.api

import it.hembik.data.dto.StargazerDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/repos/{owner}/{repo}/stargazers")
    suspend fun getStargazers(
        @Header("Accept") name: String = "application/vnd.github+json",
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int
    ): List<StargazerDTO>
}
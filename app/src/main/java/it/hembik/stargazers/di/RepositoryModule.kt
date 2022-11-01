package it.hembik.stargazers.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.hembik.data.repository.StargazersRepositoryImpl
import it.hembik.domain.repository.StargazersRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindStargazersRepository(impl: StargazersRepositoryImpl): StargazersRepository
}
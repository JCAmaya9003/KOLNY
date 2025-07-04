package com.pdm_proyecto.kolny.di

import com.pdm_proyecto.kolny.data.repository.UsuarioRepository
import com.pdm_proyecto.kolny.data.repository.VisitaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUsuarioRepository(): UsuarioRepository {
        return UsuarioRepository()
    }

    @Provides
    @Singleton
    fun provideVisitaRepository(): VisitaRepository {
        return VisitaRepository()
    }
}

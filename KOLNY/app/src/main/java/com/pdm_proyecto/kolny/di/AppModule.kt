package com.pdm_proyecto.kolny.di

import android.content.Context
import androidx.lifecycle.ViewModel
//import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.pdm_proyecto.kolny.BuildConfig
//import com.pdm_proyecto.kolny.data.database.SupabaseClient
import com.pdm_proyecto.kolny.data.repository.UsuarioRepository
import com.pdm_proyecto.kolny.data.repository.VisitaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import javax.inject.Inject
import javax.inject.Singleton

import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage as SupaStorage

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUsuarioRepository(): UsuarioRepository {
        return UsuarioRepository()
    }

    @Provides @Singleton
    fun provideSupabaseClient(
        @ApplicationContext ctx: Context
    ): SupabaseClient = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_KEY
    ) {
        install(Postgrest)
        install(SupaStorage)
        install(Realtime)
    }

    @Provides @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    class LoginViewModel @Inject constructor(
        private val usuarioRepository: UsuarioRepository
    ) : ViewModel()

    @Provides
    @Singleton
    fun provideVisitaRepository(): VisitaRepository {
        return VisitaRepository()
    }
}

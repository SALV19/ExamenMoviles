package com.example.nefrovida.data.di

import android.content.Context
import com.example.nefrovida.data.local.preferences.CovidPreferences
import com.example.nefrovida.data.remote.api.CovidApi
import com.example.nefrovida.data.remote.api.LaboratoryApi
import com.example.nefrovida.data.repository.CovidCasesRepositoryImpl
import com.example.nefrovida.data.repository.LabAnalysisRepositoryImpl
import com.example.nefrovida.domain.repository.CovidCasesRepository
import com.example.nefrovida.domain.repository.LabAnalysisRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val API_KEY = "un0ITY+/ggMfbxTpwIFx3A==3LQWzP6zqoJIbnDi"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request()
                    .newBuilder()
                    .addHeader("X-Api-Key", API_KEY)
                    .build()

                // Log the final URL used
                println("🔥 URL Used: ${newRequest.url}")

                chain.proceed(newRequest)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCovidCasesApi(retrofit: Retrofit): CovidApi {
        return retrofit.create(CovidApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideCovidPreferences(
        @ApplicationContext context: Context,
        gson: Gson
    ): CovidPreferences {
        return CovidPreferences(context, gson)
    }

    @Provides
    @Singleton
    fun provideCovidCasesRepository(
        api: CovidApi,
        preferences: CovidPreferences
    ): CovidCasesRepository {
        return CovidCasesRepositoryImpl(api, preferences)
    }
}

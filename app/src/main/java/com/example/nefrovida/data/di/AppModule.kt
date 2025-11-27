package com.example.nefrovida.data.di

import com.example.nefrovida.data.remote.api.LaboratoryApi
import com.example.nefrovida.data.repository.LabAnalysisRepositoryImpl
import com.example.nefrovida.domain.repository.LabAnalysisRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val API_KEY = "un0ITY+/ggMfbxTpwIFx3A==3LQWzP6zqoJIbnDi"
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-Api-Key", API_KEY)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideLabAnalysisApi(retrofit: Retrofit): LaboratoryApi {
//        return retrofit.create(LaboratoryApi::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideLabAnalysisRepository(
//        api: LaboratoryApi
//    ): LabAnalysisRepository {
//        return LabAnalysisRepositoryImpl(api)
//    }
}
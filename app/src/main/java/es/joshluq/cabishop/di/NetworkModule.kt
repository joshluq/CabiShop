package es.joshluq.cabishop.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.joshluq.cabishop.BuildConfig
import es.joshluq.cabishop.data.datasource.remote.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    @Named("logging")
    fun provideHttpInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = when {
            BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    @Named("client")
    fun provideOkHttpClient(
        @Named("logging") loggingInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(40L, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    @Named("retrofit")
    fun provideRetrofit(
        @Named("client") client: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://gist.githubusercontent.com/palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/")
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    @Named("apiService")
    fun provideApiService(@Named("retrofit") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}
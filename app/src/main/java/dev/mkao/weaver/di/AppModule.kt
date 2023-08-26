package dev.mkao.weaver.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mkao.weaver.data.remote.NewsApi
import dev.mkao.weaver.data.remote.NewsApi.Companion.BASE_URL
import dev.mkao.weaver.data.repository.RepositoryImpl
import dev.mkao.weaver.domain.repository.Repository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	
	@Provides
	@Singleton
	fun provideNewsApi(): NewsApi {
		val retrofit = Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
		return retrofit.create(NewsApi::class.java)
	}
	@Provides
	@Singleton
	fun providesNewsRepository(newsApi: NewsApi): Repository {
		return RepositoryImpl(newsApi)
	}
}




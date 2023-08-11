package dev.mkao.weaver.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mkao.weaver.data.remote.NewsApi
import dev.mkao.weaver.data.remote.NewsApi.Companion.BASE_URL
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
}




package dev.mkao.weaver.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mkao.weaver.data.remote.CountryDatabase
import dev.mkao.weaver.data.remote.CountryDao
import dev.mkao.weaver.data.remote.NewsApi
import dev.mkao.weaver.data.remote.NewsApi.Companion.BASE_URL
import dev.mkao.weaver.data.remote.NewsDao
import dev.mkao.weaver.data.remote.NewsDatabase
import dev.mkao.weaver.data.remote.NewsTypeConvertor
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
	fun providesNewsRepository(
		newsDao: NewsDao,
		countryDao: CountryDao,
		newsApi: NewsApi): Repository {
		return RepositoryImpl(newsApi, newsDao,countryDao)
	}

	@Provides
	@Singleton
	fun providesNewsDatabase(
		application: Application
	):NewsDatabase{
		return Room.databaseBuilder(
			context = application,
			klass = NewsDatabase::class.java,
			name = "NewsDB"
		).addTypeConverter(NewsTypeConvertor())
			.fallbackToDestructiveMigration()
			.build()
	}
	@Provides
	@Singleton
	fun providesDao(
		newsDatabase: NewsDatabase
	):NewsDao = newsDatabase.articleDao()

	@Provides
	fun provideNewsDao(database: CountryDatabase): CountryDao {
		return database.newsDao()
	}
	@Provides
	@Singleton
	fun providesNewsCountryDatabase(
		application: Application
	): CountryDatabase {
		return Room.databaseBuilder(
			context = application,
			klass = CountryDatabase::class.java,
			name = "Country"
		).fallbackToDestructiveMigration()
			.build()
	}
}




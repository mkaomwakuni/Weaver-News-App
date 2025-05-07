package dev.mkao.weaver.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.mkao.weaver.data.preferences.AppPreferencesManager
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
        newsApi: NewsApi
    ): Repository {
        return RepositoryImpl(
            newsApi = newsApi,
            newsDao = newsDao
        )
    }

    @Provides
    @Singleton
    fun providesNewsDatabase(
        application: Application
    ): NewsDatabase {
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
    ): NewsDao = newsDatabase.articleDao()

    @Provides
    @Singleton
    fun provideUserPreferencesManager(
        @ApplicationContext context: Context
    ): AppPreferencesManager {
        return AppPreferencesManager(context)
    }
}
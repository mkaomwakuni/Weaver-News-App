package dev.mkao.weaver.domain.model

import dev.mkao.weaver.data.remote.NewsDao

class UppsertCase (private val newsDao: NewsDao){

    suspend operator fun invoke(article: Article){
        newsDao.upsert(article = article)
    }
}
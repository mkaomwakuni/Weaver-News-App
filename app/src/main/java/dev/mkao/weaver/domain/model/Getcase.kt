package dev.mkao.weaver.domain.model

import dev.mkao.weaver.data.remote.NewsDao
import dev.mkao.weaver.util.Assets

class GetCase (private val newsDao: NewsDao) {
  operator fun invoke():Assets<List<Article>>{
      return newsDao.getArticles()
  }
}
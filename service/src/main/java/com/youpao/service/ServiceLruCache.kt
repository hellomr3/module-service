package com.youpao.service

import androidx.collection.LruCache

/**
 * @author guoqingshan
 * @date 2022/7/11/011
 * @description
 */
class ServiceLruCache(private val maxSize: Int) : LruCache<String, IService>(maxSize)
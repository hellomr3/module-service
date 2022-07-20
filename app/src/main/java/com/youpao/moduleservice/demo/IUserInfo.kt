package com.youpao.moduleservice.demo

import com.youpao.service.IService

/**
 * @author guoqingshan
 * @date 2022/7/20/020
 * @description
 */
interface IUserInfo : IService {
    fun getUserName(): String

    fun getUserId(): String
}
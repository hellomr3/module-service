package com.youpao.moduleservice.demo

import com.youpao.compiler.ModuleService

/**
 * @author guoqingshan
 * @date 2022/7/19/019
 * @description 测试1
 */
@ModuleService
class Test1 : IUserInfo {

    override fun getUserName(): String {
        return "张三"
    }

    override fun getUserId(): String {
        return "1"
    }
}
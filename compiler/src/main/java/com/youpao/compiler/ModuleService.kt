package com.youpao.compiler

/**
 * @author guoqingshan
 * @date 2022/7/11/011
 * @description 标记当前是一个跨模块的服务
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class ModuleService

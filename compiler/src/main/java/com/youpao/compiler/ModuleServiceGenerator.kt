package com.youpao.compiler

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview

/**
 * @author guoqingshan
 * @date 2022/7/11/011
 * @description
 */
class ModuleServiceGenerator {

    @OptIn(KotlinPoetKspPreview::class)
    fun generate(
        codeGenerator: CodeGenerator,
        logger: KSPLogger,
        lis: List<KSPropertyDeclaration>
    ) {
        lis.forEach {
            println("it:$it")
        }
    }
}
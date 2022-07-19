package com.youpao.compiler

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.validate
import com.youpao.annotation.ModuleService

/**
 * @author guoqingshan
 * @date 2022/7/11/011
 * @description
 */
class ModuleServiceProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(ModuleService::class.qualifiedName!!)
        val ret = symbols.filter { !it.validate() }.toList()
        //生成代码
        symbols.filter {
            it is KSPropertyDeclaration && it.validate()
        }
            .mapNotNull { it as? KSPropertyDeclaration }.toList()
            .also {
                ModuleServiceGenerator().generate(codeGenerator, logger, it)
            }

        return ret
    }
}
package com.youpao.compiler

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.writeTo

/**
 * @author guoqingshan
 * @date 2022/7/11/011
 * @description
 */
class ModuleServiceProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private var isload = false

    override fun process(resolver: Resolver): List<KSAnnotated> {

        if (isload) return emptyList()

        val symbols = resolver.getSymbolsWithAnnotation("com.youpao.annotation.ModuleService")

        val ret = symbols.filter { !it.validate() }.toList()
        //生成代码
        symbols.filter {
            it is KSPropertyDeclaration && it.validate()
        }
            .mapNotNull { it as? KSPropertyDeclaration }
            .onEach {
                logger.info("122222")
                genarate(it)
            }
            .forEach { it.accept(PrintVisitor(), Unit) }

        return ret
    }

    @OptIn(KotlinPoetKspPreview::class)
    private fun genarate(ksp: KSPropertyDeclaration) {
        val pkgName = ksp.packageName.asString()
        val fileSpecBuilder = FileSpec.builder(
            packageName = pkgName,
            fileName = "${pkgName}XXX"
        )
        fileSpecBuilder
            .addFunction(FunSpec.builder("test1").build())
            .build()
            .writeTo(codeGenerator = codeGenerator, aggregating = false)
    }

    inner class PrintVisitor : KSVisitorVoid() {
        @OptIn(KotlinPoetKspPreview::class)
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            logger.logging("PrintProcessor#visitClassDeclaration", classDeclaration)
            val packageName = classDeclaration.containingFile!!.packageName.asString()
            val className = "${classDeclaration.simpleName.asString()}Print"
            val fileKotlinPoet = FileSpec.builder(packageName, className)

            val functionBuilder = FunSpec.builder("printAllProperties")
            classDeclaration.getAllProperties().forEach {
                functionBuilder.addStatement(
                    "println(%S)",
                    "${it.simpleName.asString()}: ${it.type.resolve()}"
                ).build()
            }

            fileKotlinPoet.addType(
                TypeSpec.classBuilder(className)
                    .addFunction(
                        functionBuilder.build()
                    ).build()
            ).build()
            fileKotlinPoet.build().writeTo(codeGenerator = codeGenerator, dependencies =  Dependencies(true, classDeclaration.containingFile!!))
        }
    }
}
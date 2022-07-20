package com.youpao.compiler

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

/**
 * @author guoqingshan
 * @date 2022/7/11/011
 * @description
 */
class ModuleServiceProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
) : SymbolProcessor {


    private val logs: MutableMap<String, Any> = mutableMapOf()

    private val i2imp: MutableMap<String, String> = mutableMapOf()

    private var processed: Boolean = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (processed) return emptyList()
        for ((k, v) in options) {
            logs[k] = v
        }
        //生成代码
        val symbols = resolver.getSymbolsWithAnnotation(ModuleService::class.qualifiedName!!)
        val invalidSymbols = symbols.filter { !it.validate() }.toList()

        logs["service"] =ModuleService::class.qualifiedName!!
        logs["symbolsSize"] = symbols.toList().size
        //生成代码
        symbols.filter { //注解在方法上并且是有效的
            it is KSClassDeclaration && it.validate()
        }
            .mapNotNull { it as? KSClassDeclaration }
            .toList()
            .also {
                generate(it)
            }

        //log
        logA()
        return invalidSymbols.also {
            processed = true
        }
    }

    @OptIn(KotlinPoetKspPreview::class)
    private fun generate(lst: List<KSClassDeclaration>) {
        lst.forEach {
            scan(it)
        }
        val init = TypeSpec.objectBuilder("ServiceInit")
            .apply {
                i2imp.keys.forEach { key ->
                    val value = i2imp[key]
                    this.addInitializerBlock(CodeBlock.of("ServiceManager.register(\"$key\",\"$value\")\n"))
                }
            }
            .build()
        FileSpec.builder("com.yupao.service", "ServiceInit")
            .addImport("com.youpao.service", "ServiceManager")
            .addType(init)
            .build()
            .writeTo(codeGenerator = codeGenerator, dependencies = Dependencies(true))
    }

    @OptIn(KotlinPoetKspPreview::class)
    private fun scan(ksc: KSClassDeclaration) {
        val pkgName = ksc.packageName.asString()
        val impName = ksc.qualifiedName?.asString()
        val interfaceName = ksc.getAllSuperTypes().firstOrNull()?.toTypeName()
        logs["impl"] = impName ?: ""
        logs["interface"] = interfaceName ?: ""
        if (impName != null && interfaceName != null) {
            i2imp[interfaceName.toString()] = impName
        }
        ksc.getAllSuperTypes().forEach {
            val key = "superType:${it.toTypeName()}"
            logs[key] = it
        }
        ksc.getAllFunctions()
            .forEach {
                val key = "fun:${it.simpleName.asString()}"
                logs[key] = it
            }
    }


    @OptIn(KotlinPoetKspPreview::class)
    private fun logA() {
        FileSpec.builder(
            packageName = "log",
            fileName = "log"
        )
            .apply {
                logs.keys.forEach { s ->
                    this.addComment("key:$s,value:${logs.get(key = s)}\n")
                }
            }
            .build()
            .writeTo(codeGenerator = codeGenerator, aggregating = true)
    }
}


import com.google.auto.service.AutoService
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.youpao.compiler.ModuleServiceProcessor

/**
 * @author guoqingshan
 * @date 2022/7/11/011
 * @description 跨模块服务注解处理类
 */
@AutoService(SymbolProcessorProvider::class)
class ModuleServiceProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return ModuleServiceProcessor(environment.codeGenerator, environment.logger, emptyMap())
    }
}
@file:Suppress("UNCHECKED_CAST")

package com.youpao.service

object ServiceManager {
    private val serviceLruCache = ServiceLruCache(20)
    private val i2ImplMap = HashMap<String, String>()

    /**
     * 通过接口
     * @param iClass Class<T>
     * @return T?
     */
    fun <T : IService> getServiceByInterface(iClass: Class<T>): T? {
        val iName = iClass.simpleName
        val implName = i2ImplMap.getOrDefault(iName, null) ?: return null
        if (serviceLruCache.get(implName) != null) {
            return serviceLruCache.get(implName) as? T
        }
        return kotlin.runCatching {
            val impl = Class.forName(implName)
            val instance = impl.declaredConstructors.firstOrNull()?.apply { isAccessible = true }
                ?.newInstance()
                ?.also {
                    (it as? IService)?.let {
                        serviceLruCache.put(implName, it)
                    }
                }
            instance
        }.getOrNull() as? T
    }

    /**
     * 向服务注册映射表
     * @param interfaceName String
     * @param implName String
     */
    fun register(interfaceName: String, implName: String) {
        i2ImplMap[interfaceName] = implName
    }
}
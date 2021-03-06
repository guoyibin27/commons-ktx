package commonx.core.content


/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/7/10
 * @description
 **/

/**
 * 创建一个新的数据，指定数组元素类型及大小
 * @param size 大小 ，默认20
 */
inline fun <reified E> arrayOfType(size: Int = 20): Array<E> {
    return java.lang.reflect.Array.newInstance(E::class.java, size) as Array<E>
}

/**
 * 获取数组元素类型
 */
fun Array<*>.getComponentType(): Class<*> {
    return this.javaClass.componentType
}


/**
 * 如果给定数组为空，则返回默认数组。否则返回当前数组
 */
fun <T> Array<T>?.defaultIfEmpty(default: Array<T>): Array<T> {
    if (this == null || this.isEmpty()) {
        return default
    }
    return this
}

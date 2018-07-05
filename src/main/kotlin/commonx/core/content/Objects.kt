package commonx.core.content

import org.dozer.DozerBeanMapper
import kotlin.streams.toList

/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/6/25
 * @description 对象赋值
 **/

var dozer = DozerBeanMapper()

/**
 * 属性复制，使用方法
 */
inline fun <reified T> Any.transferTo(): T {
    return dozer.map(this, T::class.java)
}

/**
 * 集合内元素的属性复制
 */
inline fun <reified T, E> Collection<E>.transferTo(): Collection<T> {
    return this.parallelStream().map { dozer.map<T>(it, T::class.java) }.toList()
}

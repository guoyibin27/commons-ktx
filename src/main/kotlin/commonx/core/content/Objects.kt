package commonx.core.content

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.bean.copier.CopyOptions

/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/6/25
 * @description 对象赋值
 **/

/**
 * 属性复制，使用方法
 */
inline fun <reified T> Any.transfer(ignoreNull: Boolean = true): T {
    val destination = T::class.java.newInstance()
    BeanUtil.copyProperties(this, destination, CopyOptions.create().setIgnoreNullValue(ignoreNull))
    return destination
}

/**
 * 集合内元素的属性复制
 */
inline fun <reified T> List<*>.transferEntries(ignoreNull: Boolean = true): Collection<T> {
    return map {
        it!!.transfer<T>(ignoreNull)
    }
}

fun Any.transfer(destination: Any, ignoreNull: Boolean = true) {
    BeanUtil.copyProperties(this, destination, CopyOptions.create().setIgnoreNullValue(ignoreNull))
}

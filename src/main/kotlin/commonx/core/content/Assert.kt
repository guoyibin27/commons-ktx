package commonx.core.content

/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/6/13
 * @description
 **/

/**
 * 字符串null或者空断言
 */
fun String?.assertNull(message: String) {
    assert(this.isNotNullOrEmpty()) { message }
}

/**
 * 对象null断言
 */
fun Any?.assertNull(message: Any) {
    assert(this != null) { message }
}
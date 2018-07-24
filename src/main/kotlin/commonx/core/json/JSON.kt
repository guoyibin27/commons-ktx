package commonx.core.json

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONPath
import commonx.core.content.assertNull

/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/6/13
 * @description JSON 相关转换方法
 **/

/**
 * String 转 Json
 * @return
 */
inline fun <reified T> String.toJSONObject(): T = JSON.parseObject(this, T::class.java)

/**
 * Object 转 Json string
 */
fun Any.toJSONString(): String = JSON.toJSONString(this)

/**
 * jsonPath，获取json中的值, path规则详见</br>
 * <a href="https://github.com/alibaba/fastjson/wiki/JSONPath"/>
 */
inline fun <reified T> String.jsonPath(path: String): T {
    assertNull("json must not be empty")
    path.assertNull("path must not be empty")
    return JSONPath.eval(toJSONObject(), path) as T
}

/**
 * jsonPath，获取json中的值, path规则详见</br>
 * <a href="https://github.com/alibaba/fastjson/wiki/JSONPath"/>
 */
inline fun <reified T> String.jsonPath(path: String, block: (T) -> Unit) {
    assertNull("json must not be empty")
    path.assertNull("path must not be empty")
    block(JSONPath.eval(toJSONObject(), path) as T)
}

/**
 * 判断jsonPath的value是否存在
 */
fun <T> String.containsJsonValue(path: String, value: T): Boolean {
    assertNull("json must not be empty")
    path.assertNull("path must not be empty")
    return JSONPath.containsValue(toJSONObject(), path, value)
}

/**
 * jsonPath是否存在
 */
fun String.containsJsonPath(path: String): Boolean {
    assertNull("json must not be empty")
    path.assertNull("path must not be empty")
    return JSONPath.contains(toJSONObject(), path)
}

/**
 * jsonPath，获取json中的值, path规则详见</br>
 * <a href="https://github.com/alibaba/fastjson/wiki/JSONPath"/>
 */
inline fun <reified T> Any.jsonPath(path: String): T {
    return JSONPath.eval(this, path) as T
}

/**
 * jsonPath，获取json中的值, path规则详见</br>
 * <a href="https://github.com/alibaba/fastjson/wiki/JSONPath"/>
 */
inline fun <reified T> Any.jsonPath(path: String, block: (T) -> Unit) {
    assertNull("json must not be empty")
    path.assertNull("path must not be empty")
    block(JSONPath.eval(this, path) as T)
}


/**
 * 判断jsonPath的value是否存在
 */
fun <T> Any.containsJsonValue(path: String, value: T): Boolean {
    assertNull("json must not be empty")
    path.assertNull("path must not be empty")
    return JSONPath.containsValue(this, path, value)
}

/**
 * jsonPath是否存在
 */
fun Any.containsJsonPath(path: String): Boolean {
    assertNull("json must not be empty")
    path.assertNull("path must not be empty")
    return JSONPath.contains(this, path)
}


fun main(args: Array<String>) {
    val test = Test("test1", 20)

    val testjson = test.toJSONString()
    val parseObject = JSON.parseObject(testjson, Test::class.java)

    println(test.jsonPath<Int>("$.age"))
    println(test.jsonPath<String>("$.name"))

    println(testjson.jsonPath<Int>("$.age"))
    println(testjson.jsonPath<String>("$.name"))
}

class Test(var name: String? = null, var age: Int = 0)
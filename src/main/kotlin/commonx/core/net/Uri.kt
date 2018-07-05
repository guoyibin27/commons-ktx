package commonx.core.net

import java.net.URI

/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/6/13
 * @description
 **/


/**
 * 字符串转url
 */
fun String.toUri(): URI = URI.create(this)

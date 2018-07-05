package commonx.core.date

import java.text.SimpleDateFormat
import java.util.*

/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/6/13
 * @description 跟日期转化相关的扩展方法
 **/

/**
 * 长整型转日期类型
 * @param pattern 日期格式 default: yyyy-MM-dd HH:mm:ss
 * @return Date 日期
 */
fun Long.dateFormat(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    val dateFormat = SimpleDateFormat()
    dateFormat.applyPattern(pattern)
    return dateFormat.format(this)
}

/**
 * 字符串转日期类型
 * @param pattern 日期格式 default: yyyy-MM-dd HH:mm:ss
 * @return Date 日期
 */
fun String.toDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Date {
    val dateFormat = SimpleDateFormat()
    dateFormat.applyPattern(pattern)
    return dateFormat.parse(this)
}

/**
 * 日期转字符串
 */
fun Date.parseToString(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    val dateFormat = SimpleDateFormat()
    dateFormat.applyPattern(pattern)
    return dateFormat.format(this)
}
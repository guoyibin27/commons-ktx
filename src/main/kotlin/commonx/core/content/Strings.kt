package commonx.core.content

import com.sun.org.apache.xpath.internal.operations.Bool
import commonx.core.date.calculateAge
import commonx.core.date.dateString
import commonx.core.date.lastWeek
import commonx.core.date.toDate
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/6/13
 * @description
 **/

/**
 * 是否是数字
 * @return Boolean
 */
fun CharSequence.isNumeric(): Boolean = this.matches(Regex("\\d*"))


/**
 * 判断字符串不为null活着空(不包括空格)
 */
fun CharSequence?.isNotNullOrEmpty(): Boolean = !this.isNullOrEmpty()

/**
 * 判断字符串不为null活着空(包括空格)
 */
fun CharSequence?.isNotNullOrBlank(): Boolean = !this.isNullOrBlank()

/**
 * md5加密
 */
fun String.MD5() = encrypt(this, "MD5")


/**
 * SHA-1加密
 */
fun String.sha1() = encrypt(this, "SHA-1")

/**
 * Base64转码
 */
fun String.encodeBase64(charset: Charset = Charsets.UTF_8) = Base64.getEncoder().encodeToString(this.toByteArray(charset))

/**
 * Base64解码
 */
fun String.decodeBase64(charset: Charset = Charsets.UTF_8) = String(Base64.getDecoder().decode(this), charset)


/**
 * 是否是电话号码
 */
fun String.isPhone(): Boolean {
    val p = "^1([34578])\\d{9}\$".toRegex()
    return matches(p)
}

/**
 * 是否是email
 */
fun String.isEmail(): Boolean {
    val p = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)\$".toRegex()
    return matches(p)
}


/**
 * 加密
 */
private fun encrypt(string: String?, type: String): String {
    if (string.isNullOrEmpty()) {
        return ""
    }
    val md5: MessageDigest
    return try {
        md5 = MessageDigest.getInstance(type)
        val bytes = md5.digest(string!!.toByteArray())
        bytes.toHex()
    } catch (e: NoSuchAlgorithmException) {
        ""
    }
}

/**
 * 字节数组转16进制字符串
 */
fun ByteArray.toHex(): String {
    var des = ""
    var tmp: String
    for (i in this.indices) {
        tmp = Integer.toHexString(this[i].toInt() and 0xFF)
        if (tmp.length == 1) {
            des += "0"
        }
        des += tmp
    }
    return des
}

/**
 * 字符串转16进制字符串
 */
fun String.toHex(): String {
    return this.toByteArray().toHex()
}

/**
 * 字符串转16进制字符串
 */
fun String.toHex(charset: Charset): String {
    return this.toByteArray(charset).toHex()
}

/**
 * 转驼峰命名法
 */
fun String.toCamelCase(separator: Char = '_', firstCapital: Boolean = true): String {
    val builder = StringBuilder()
    var capitalFlag = firstCapital
    for (c in this) {
        when (c) {
            separator -> capitalFlag = true
            else -> {
                builder.append(if (capitalFlag) Character.toUpperCase(c) else Character.toLowerCase(c))
                capitalFlag = false
            }
        }
    }
    return builder.toString()
}

/**
 * 字符串转StringBuilder
 */
fun String.stringBuilder(): StringBuilder {
    return StringBuilder(this)
}

/**
 * 字符串转StringBuffer
 */
fun String.stringBuffer(): StringBuffer {
    return StringBuffer(this)
}

/**
 * 默认字符串，如果string为null时，返回给定的默认字符串
 *
 * @param defaultStr  默认字符串，默认为""
 * @return
 */
fun String?.defaultString(defaultStr: String = ""): String {
    if (this.isNullOrEmpty())
        return defaultStr
    else
        return this!!
}

fun main(args: Array<String>) {
    val s: String? = null
    val ss = "haha"
    println(s.defaultString("aaa"))
    println(ss.defaultString("aaabb"))
}
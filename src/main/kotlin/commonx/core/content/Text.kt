package commonx.core.content

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
        bytes2Hex(bytes)
    } catch (e: NoSuchAlgorithmException) {
        ""
    }
}

/**
 * Extension method to convert byteArray to String.
 */
private fun bytes2Hex(bts: ByteArray): String {
    var des = ""
    var tmp: String
    for (i in bts.indices) {
        tmp = Integer.toHexString(bts[i].toInt() and 0xFF)
        if (tmp.length == 1) {
            des += "0"
        }
        des += tmp
    }
    return des
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

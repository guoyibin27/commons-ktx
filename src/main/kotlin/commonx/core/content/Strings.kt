package commonx.core.content

import cn.hutool.core.text.StrBuilder
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
    val builder = this.stringBuffer(false)
    var capitalFlag = firstCapital
    for (c in this) {
        capitalFlag = if (c == separator) {
            true
        } else {
            builder.append(if (capitalFlag) Character.toUpperCase(c) else Character.toLowerCase(c))
            false
        }
    }
    return builder.toString()
}

/**
 * 将驼峰式命名的字符串转换为使用符号连接方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br></br>
 *
 * @param separator 连接符 默认为 "_"
 * @return 转换后符号连接方式命名的字符串
 */
fun String.toUnderlineCase(separator: Char = '_'): String {
    val sb = this.stringBuilder(false)
    var c: Char
    for (i in 0 until length) {
        c = this[i]
        val preChar = if (i > 0) this[i - 1] else null
        if (Character.isUpperCase(c)) {
            //遇到大写字母处理
            val nextChar = if (i < length - 1) this[i + 1] else null
            if (null != preChar && Character.isUpperCase(preChar)) {
                //前一个字符为大写，则按照一个词对待
                sb.append(c)
            } else if (null != nextChar && Character.isUpperCase(nextChar)) {
                //后一个为大写字母，按照一个词对待
                if (null != preChar && separator != preChar) {
                    //前一个是非大写时按照新词对待，加连接符
                    sb.append(separator)
                }
                sb.append(c)
            } else {
                //前后都为非大写按照新词对待
                if (null != preChar && separator != preChar) {
                    //前一个非连接符，补充连接符
                    sb.append(separator)
                }
                sb.append(Character.toLowerCase(c))
            }
        } else {
            if (sb.length > 0 && Character.isUpperCase(sb[sb.length - 1]) && separator != c) {
                //当结果中前一个字母为大写，当前为小写，说明此字符为新词开始（连接符也表示新词）
                sb.append(separator)
            }
            //小写或符号
            sb.append(c)
        }
    }
    return sb.toString()
}

/**
 * 字符串转StringBuilder
 */
fun String.stringBuilder(includeSelf: Boolean = true): StringBuilder {
    return if (includeSelf) StringBuilder(this) else StringBuilder()
}

/**
 * 字符串转StringBuffer
 */
fun String.stringBuffer(includeSelf: Boolean): StringBuffer {
    return if (includeSelf) StringBuffer(this) else StringBuffer()
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

/**
 * 指定长度截取字符串，并追加占位符
 * @param maxLength 最大长度
 * @param placeholder 占位符
 * @return 如果最大长度大于字符串长度本身，则返回字符串,否则返回截取的字符串+placeholder
 */
fun String.substringAfter(maxLength: Int, placeholder: String = "..."): String {
    return if (length <= maxLength) {
        this
    } else {
        this.substring(0, maxLength) + placeholder
    }
}

/**
 * 自动填充字符串头
 * @param placeholder 填充占位符
 * @param totalLength 填充后的字符串总长度
 * @return 填充后的字符串
 */
fun String.placeholderPre(placeholder: String = "", totalLength: Int): String {
    if (length >= totalLength) {
        return this
    }
    return placeholder.repeat(totalLength - length).plus(this)
}

/**
 * 自动填充字符串尾
 * @param placeholder 填充占位符
 * @param totalLength 填充后的字符串总长度
 * @return 填充后的字符串
 */
fun String.placeholderEnd(placeholder: String = "", totalLength: Int): String {
    if (length >= totalLength) {
        return this
    }
    return this.plus(placeholder.repeat(totalLength - length))
}

/**
 * 统计指定内容中包含指定字符串的数量<br>
 *
 * @param countItem 被查找的字符串
 * @return 查找到的个数 参数为 `null` 或者 ""时, 返回 `0`.
 */
fun String?.containsCount(countItem: String?): Int {
    if (this.isNullOrEmpty()) {
        return 0
    }

    if (countItem.isNullOrEmpty()) {
        return 0
    }

    var count = 0
    var idx = 0
    while (true) {
        idx = this!!.indexOf(countItem!!, idx)
        if (idx > -1) {
            count++
            idx += countItem.length
        } else {
            break
        }
    }
    return count
}

/**
 * 将字符串切分为N等份
 *
 * @param partLen 每等份的长度
 * @return 切分后的数组
 */
fun String?.cut(partLen: Int): Array<String> {
    if (this.isNullOrEmpty()) {
        return arrayOf()
    }

    if (this!!.length < partLen) {
        return arrayOf()
    }

    val totalParts = this.length.calculatePages(partLen)
    val result = Array<String>(totalParts) { "" }
    for (i in 0 until totalParts) {
        result[i] = this.substring(i * partLen, if (i == totalParts.minus(1)) length else partLen.plus(i.times(partLen)))
    }
    return result
}

/**
 * 重写String的substring，做了一些判断。
 * 正数从字符串头开始计算
 * 负数从字符串末尾开始计算
 */
fun String.substring(start: Int, end: Int): String {
    if (this.isEmpty()) {
        return this
    }

    var fromIndex = start
    var endIndex = end
    if (fromIndex < 0) {
        fromIndex = length.plus(fromIndex)
        if (fromIndex < 0) {
            fromIndex = 0
        }
    } else if (fromIndex > length) {
        fromIndex = length
    }

    if (endIndex < 0) {
        endIndex = length.plus(endIndex)
        if (endIndex < 0) {
            endIndex = length
        }
    } else if (endIndex > length) {
        endIndex = length
    }

    if (endIndex < fromIndex) {
        val tmp = fromIndex
        fromIndex = endIndex
        endIndex = tmp
    }

    if (fromIndex == endIndex) {
        return ""
    }

    return (this as java.lang.String).substring(fromIndex, endIndex)
}
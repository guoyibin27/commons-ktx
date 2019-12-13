package commonx.core.content

import cn.hutool.core.util.CharsetUtil
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException


/**
 * 转换为Charset对象
 * @return Charset
 * @throws UnsupportedCharsetException 编码不支持
 */
@Throws(UnsupportedCharsetException::class)
fun String?.toCharset(): Charset {
    return if (this.isNullOrEmpty()) {
        Charset.defaultCharset()
    } else {
        Charset.forName(this)
    }
}


/**
 * 转换字符串的字符集编码<br>
 * 当以错误的编码读取为字符串时，打印字符串将出现乱码。<br>
 * 此方法用于纠正因读取使用编码错误导致的乱码问题。<br>
 * 例如，在Servlet请求中客户端用GBK编码了请求参数，我们使用UTF-8读取到的是乱码，此时，使用此方法即可还原原编码的内容
 * <pre>
 * 客户端 -》 GBK编码 -》 Servlet容器 -》 UTF-8解码 -》 乱码
 * 乱码 -》 UTF-8编码 -》 GBK解码 -》 正确内容
 * </pre>
 *
 * @param srcCharset 源字符集，默认ISO-8859-1
 * @param destCharset 目标字符集，默认UTF-8
 * @return 转换后的字符集
 */
fun String?.convert(destCharset: Charset = Charsets.UTF_8, srcCharset: Charset = Charsets.ISO_8859_1): String? {
    if (isNullOrEmpty()) return this
    return CharsetUtil.convert(this, srcCharset, destCharset)
}

/**
 * 转换字符串的字符集编码<br>
 * 当以错误的编码读取为字符串时，打印字符串将出现乱码。<br>
 * 此方法用于纠正因读取使用编码错误导致的乱码问题。<br>
 * 例如，在Servlet请求中客户端用GBK编码了请求参数，我们使用UTF-8读取到的是乱码，此时，使用此方法即可还原原编码的内容
 * <pre>
 * 客户端 -》 GBK编码 -》 Servlet容器 -》 UTF-8解码 -》 乱码
 * 乱码 -》 UTF-8编码 -》 GBK解码 -》 正确内容
 * </pre>
 *
 * @param srcCharset 源字符集，默认ISO-8859-1
 * @param destCharset 目标字符集，默认UTF-8
 * @return 转换后的字符集
 */
fun String?.convert(destCharset: String = "UTF-8", srcCharset: String = "ISO-8859-1"): String? {
    return convert(Charset.forName(destCharset), Charset.forName(srcCharset))
}
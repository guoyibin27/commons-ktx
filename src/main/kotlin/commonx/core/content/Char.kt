package commonx.core.content

import cn.hutool.core.util.CharUtil

/**
 * 是否为ASCII字符，ASCII字符位于0~127之间
 *
 * <pre>
 *   CharUtil.isAscii('a')  = true
 *   CharUtil.isAscii('A')  = true
 *   CharUtil.isAscii('3')  = true
 *   CharUtil.isAscii('-')  = true
 *   CharUtil.isAscii('\n') = true
 *   CharUtil.isAscii('&copy;') = false
 * </pre>
 *
 * @return true表示为ASCII字符，ASCII字符位于0~127之间
 */
fun Char.isAscii(): Boolean {
    return CharUtil.isAscii(this)
}

/**
 * 是否为可见ASCII字符，可见字符位于32~126之间
 *
 * <pre>
 *   CharUtil.isAsciiPrintable('a')  = true
 *   CharUtil.isAsciiPrintable('A')  = true
 *   CharUtil.isAsciiPrintable('3')  = true
 *   CharUtil.isAsciiPrintable('-')  = true
 *   CharUtil.isAsciiPrintable('\n') = false
 *   CharUtil.isAsciiPrintable('&copy;') = false
 * </pre>
 *
 * @return true表示为ASCII可见字符，可见字符位于32~126之间
 */
fun Char.isAsciiPrintable(): Boolean {
    return CharUtil.isAsciiPrintable(this)
}

/**
 * 是否为ASCII控制符（不可见字符），控制符位于0~31和127
 *
 * <pre>
 *   CharUtil.isAsciiControl('a')  = false
 *   CharUtil.isAsciiControl('A')  = false
 *   CharUtil.isAsciiControl('3')  = false
 *   CharUtil.isAsciiControl('-')  = false
 *   CharUtil.isAsciiControl('\n') = true
 *   CharUtil.isAsciiControl('&copy;') = false
 * </pre>
 *
 * @return true表示为控制符，控制符位于0~31和127
 */
fun Char.isAsciiControl(): Boolean {
    return CharUtil.isAsciiControl(this)
}

/**
 * 判断是否为字母（包括大写字母和小写字母）<br>
 * 字母包括A~Z和a~z
 *
 * <pre>
 *   CharUtil.isLetter('a')  = true
 *   CharUtil.isLetter('A')  = true
 *   CharUtil.isLetter('3')  = false
 *   CharUtil.isLetter('-')  = false
 *   CharUtil.isLetter('\n') = false
 *   CharUtil.isLetter('&copy;') = false
 * </pre>
 *
 * @return true表示为字母（包括大写字母和小写字母）字母包括A~Z和a~z
 */
fun Char.isLetter(): Boolean {
    return CharUtil.isLetter(this)
}

/**
 * <p>
 * 判断是否为大写字母，大写字母包括A~Z
 * </p>
 *
 * <pre>
 *   CharUtil.isLetterUpper('a')  = false
 *   CharUtil.isLetterUpper('A')  = true
 *   CharUtil.isLetterUpper('3')  = false
 *   CharUtil.isLetterUpper('-')  = false
 *   CharUtil.isLetterUpper('\n') = false
 *   CharUtil.isLetterUpper('&copy;') = false
 * </pre>
 *
 * @return true表示为大写字母，大写字母包括A~Z
 */
fun Char.isLetterUpperCase(): Boolean {
    return CharUtil.isLetterUpper(this)
}

/**
 * <p>
 * 检查字符是否为小写字母，小写字母指a~z
 * </p>
 *
 * <pre>
 *   CharUtil.isLetterLower('a')  = true
 *   CharUtil.isLetterLower('A')  = false
 *   CharUtil.isLetterLower('3')  = false
 *   CharUtil.isLetterLower('-')  = false
 *   CharUtil.isLetterLower('\n') = false
 *   CharUtil.isLetterLower('&copy;') = false
 * </pre>
 *
 * @return true表示为小写字母，小写字母指a~z
 */
fun Char.isLetterLowerCase(): Boolean {
    return CharUtil.isLetterLower(this)
}

/**
 * <p>
 * 检查是否为数字字符，数字字符指0~9
 * </p>
 *
 * <pre>
 *   CharUtil.isNumber('a')  = false
 *   CharUtil.isNumber('A')  = false
 *   CharUtil.isNumber('3')  = true
 *   CharUtil.isNumber('-')  = false
 *   CharUtil.isNumber('\n') = false
 *   CharUtil.isNumber('&copy;') = false
 * </pre>
 *
 * @return true表示为数字字符，数字字符指0~9
 */
fun Char.isNumber(): Boolean {
    return CharUtil.isNumber(this)
}

/**
 * 是否为16进制规范的字符，判断是否为如下字符
 * <pre>
 * 1. 0~9
 * 2. a~f
 * 4. A~F
 * </pre>
 *
 * @return 是否为16进制规范的字符
 * @since 4.1.5
 */
fun Char.isHexChar(): Boolean {
    return CharUtil.isHexChar(this)
}


/**
 * 是否为字符或数字，包括A~Z、a~z、0~9
 *
 * <pre>
 *   CharUtil.isLetterOrNumber('a')  = true
 *   CharUtil.isLetterOrNumber('A')  = true
 *   CharUtil.isLetterOrNumber('3')  = true
 *   CharUtil.isLetterOrNumber('-')  = false
 *   CharUtil.isLetterOrNumber('\n') = false
 *   CharUtil.isLetterOrNumber('&copy;') = false
 * </pre>
 *
 * @return true表示为字符或数字，包括A~Z、a~z、0~9
 */
fun Char.isLetterOrNumber(): Boolean {
    return CharUtil.isLetterOrNumber(this)
}

/**
 * 给定对象，判断是否是字符类
 */
fun Any.isChar(): Boolean {
    return CharUtil.isChar(this)
}

/**
 * 是否空白符<br>
 * 空白符包括空格、制表符、全角空格和不间断空格<br>
 *
 * @return 是否空白符
 * @see Character#isWhitespace(int)
 * @see Character#isSpaceChar(int)
 */
fun Char.isBlank(): Boolean {
    return CharUtil.isBlankChar(this)
}

/**
 * 是否空白符<br>
 * 空白符包括空格、制表符、全角空格和不间断空格<br>
 *
 * @return 是否空白符
 * @see Character#isWhitespace(int)
 * @see Character#isSpaceChar(int)
 */
fun Int.isBlank(): Boolean {
    return CharUtil.isBlankChar(this)
}

/**
 * 判断是否为emoji表情符<br>
 *
 * @return 是否为emoji
 */
fun Char.isEmoji(): Boolean {
    return CharUtil.isEmoji(this)
}

/**
 * 是否为Windows或者Linux（Unix）文件分隔符<br></br>
 * Windows平台下分隔符为\，Linux（Unix）为/
 *
 * @return 是否为Windows或者Linux（Unix）文件分隔符
 */
fun Char.isFileSeparator(): Boolean {
    return CharUtil.isFileSeparator(this)
}
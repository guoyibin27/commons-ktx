package commonx.core.content

import java.math.BigDecimal

/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/7/11
 * @description
 **/

/**
 * 汉语中数字大写
 */
private val CN_UPPER_NUMBER = arrayOf("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖")


/**
 * 汉语中货币单位大写，这样的设计类似于占位符
 */
private val CN_UPPER_MONETARY_UNIT = arrayOf("分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟")

/**
 * 特殊字符：整
 */
private const val CN_FULL = "整";

/**
 * 特殊字符：负
 */
private const val CN_NEGATIVE = "负";

/**
 * 金额的精度，默认值为2
 */
private const val MONEY_PRECISION = 2;

/**
 * 特殊字符：零元整
 */
private const val CN_ZERO_FULL = "零元$CN_FULL";

/**
 * 数字转大写
 */
fun BigDecimal.toChinese(): String {
    val sb = StringBuffer();
    // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
    // positive.
    val signum = this.signum();
    // 零元整的情况
    if (signum == 0) {
        return CN_ZERO_FULL;
    }
    //这里会进行金额的四舍五入
    var number = this.movePointRight(MONEY_PRECISION)
            .setScale(0, 4).abs().toLong();
    // 得到小数点后两位值
    val scale = number % 100;
    var numUnit = 0;
    var numIndex = 0;
    var getZero = false;
    // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
    if (!(scale > 0)) {
        numIndex = 2;
        number /= 100;
        getZero = true;
    }
    if ((scale > 0) && (!(scale % 10 > 0))) {
        numIndex = 1;
        number /= 10;
        getZero = true;
    }
    var zeroSize = 0;
    while (true) {
        if (number <= 0) {
            break;
        }
        // 每次获取到最后一个数
        numUnit = (number % 10).toInt();
        if (numUnit > 0) {
            if ((numIndex == 9) && (zeroSize >= 3)) {
                sb.insert(0, CN_UPPER_MONETARY_UNIT[6]);
            }
            if ((numIndex == 13) && (zeroSize >= 3)) {
                sb.insert(0, CN_UPPER_MONETARY_UNIT[10]);
            }
            sb.insert(0, CN_UPPER_MONETARY_UNIT[numIndex]);
            sb.insert(0, CN_UPPER_NUMBER[numUnit]);
            getZero = false;
            zeroSize = 0;
        } else {
            ++zeroSize;
            if (!(getZero)) {
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
            }
            if (numIndex == 2) {
                if (number > 0) {
                    sb.insert(0, CN_UPPER_MONETARY_UNIT[numIndex]);
                }
            } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                sb.insert(0, CN_UPPER_MONETARY_UNIT[numIndex]);
            }
            getZero = true;
        }
        // 让number每次都去掉最后一个数
        number /= 10;
        ++numIndex;
    }
    // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
    if (signum == -1) {
        sb.insert(0, CN_NEGATIVE);
    }
    // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
    if (!(scale > 0)) {
        sb.append(CN_FULL);
    }
    return sb.toString();
}

/**
 * 数字转大写
 */
fun Int.toChinese(): String {
    return BigDecimal(this).toChinese()
}

fun Int.calculatePages(pageSize: Int): Int {
    return this.minus(1).div(pageSize).plus(1)
}

/**
 * 是否是质数<br></br>
 * 质数表的质数又称素数。指整数在一个大于1的自然数中,除了1和此整数自身外,没法被其他自然数整除的数。
 *
 * @return 是否是质数
 */
fun Int.isPrimes(): Boolean {
    var i = 2
    while (i <= Math.sqrt(this.toDouble())) {
        if (this % i == 0) {
            return false
        }
        i++
    }
    return true
}

/**
 * 最大公约数
 *
 * @param m 第一个值
 * @return 最大公约数
 */
fun Int.divisor(m: Int): Int {
    var self = this
    while (m % self != 0) {
        val temp = m % self
        self = temp
    }
    return self
}

/**
 * 最小公倍数
 *
 * @param m 第一个值
 * @return 最小公倍数
 */
fun Int.multiple(m: Int): Int {
    return m * this / this.divisor(m)
}

fun main(args: Array<String>) {
    val a = 1234564435
    println(a.toChinese())
}
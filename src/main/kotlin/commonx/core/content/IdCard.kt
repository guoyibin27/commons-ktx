package commonx.core.content

import java.util.HashMap
import cn.hutool.core.util.StrUtil
import com.sun.org.apache.xalan.internal.lib.ExsltMath.power
import cn.hutool.core.date.DateUtil.dayOfMonth
import cn.hutool.core.date.DateUtil.month
import cn.hutool.core.date.DateUtil
import cn.hutool.core.date.DateTime
import commonx.core.date.isBirthday
import commonx.core.date.toDate


/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/7/10
 * @description 身份证验证
 **/

/** 中国公民身份证号码最小长度。  */
private const val CHINA_ID_MIN_LENGTH = 15
/** 中国公民身份证号码最大长度。  */
private const val CHINA_ID_MAX_LENGTH = 18
/** 每位加权因子  */
private val power = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)
/** 省市代码表  */
private val cityCodes = hashMapOf<String, String>(
        "11" to "北京",
        "12" to "天津",
        "13" to "河北",
        "14" to "山西",
        "15" to "内蒙古",
        "21" to "辽宁",
        "22" to "吉林",
        "23" to "黑龙江",
        "31" to "上海",
        "32" to "江苏",
        "33" to "浙江",
        "34" to "安徽",
        "35" to "福建",
        "36" to "江西",
        "37" to "山东",
        "41" to "河南",
        "42" to "湖北",
        "43" to "湖南",
        "44" to "广东",
        "45" to "广西",
        "46" to "海南",
        "50" to "重庆",
        "51" to "四川",
        "52" to "贵州",
        "53" to "云南",
        "54" to "西藏",
        "61" to "陕西",
        "62" to "甘肃",
        "63" to "青海",
        "64" to "宁夏",
        "65" to "新疆",
        "71" to "台湾",
        "81" to "香港",
        "82" to "澳门",
        "91" to "国外"
)
/** 台湾身份首字母对应数字  */
private val twFirstCode = HashMap<String, Int>()
/** 香港身份首字母对应数字  */
private val hkFirstCode = HashMap<String, Int>()

/**
 *
 * <p>
 * 判断18位身份证的合法性
 * </p>
 * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。<br>
 * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
 * <p>
 * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
 * </p>
 * <ol>
 * <li>第1、2位数字表示：所在省份的代码</li>
 * <li>第3、4位数字表示：所在城市的代码</li>
 * <li>第5、6位数字表示：所在区县的代码</li>
 * <li>第7~14位数字表示：出生年、月、日</li>
 * <li>第15、16位数字表示：所在地的派出所的代码</li>
 * <li>第17位数字表示性别：奇数表示男性，偶数表示女性</li>
 * <li>第18位数字是校检码，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示</li>
 * </ol>
 * <p>
 * 第十八位数字(校验码)的计算方法为：
 * <ol>
 * <li>将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2</li>
 * <li>将这17位数字和系数相乘的结果相加</li>
 * <li>用加出来和除以11，看余数是多少</li>
 * <li>余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2</li>
 * <li>通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2</li>
 * </ol>
 *
 * @return 是否有效的18位身份证
 */
fun String.isIdCard18(): Boolean {
    if (CHINA_ID_MAX_LENGTH != this.length) {
        return false
    }

    // 前17位
    val code17 = this.substring(0, 17)
    // 第18位
    val code18 = Character.toLowerCase(this.last())
    if (code17.isNumeric()) {
        // 获取校验位
        val `val` = getCheckCode18(code17)
        if (`val` == code18) {
            return true
        }
    }
    return false
}


/**
 * 验证15位身份编码是否合法
 *
 * @return 是否合法
 */
fun String.isIdCard15(): Boolean {
    if (CHINA_ID_MIN_LENGTH != this.length) {
        return false
    }
    if (this.isNumeric()) {
        // 省份
        val proCode = this.substring(0, 2)
        if (null == cityCodes[proCode]) {
            return false
        }

        // 生日
        val birthDate = this.substring(6, 12).toDate("yyMMdd")
        if (!birthDate.isBirthday()) {
            return false
        }
    } else {
        return false
    }
    return true
}

private fun getCheckCode18(code17: String): Char {
    val sum = getPowerSum(code17.toCharArray())
    return getCheckCode18(sum)
}

/**
 * 将power和值与11取模获得余数进行校验码判断
 *
 * @param iSum
 * @return 校验位
 */
private fun getCheckCode18(iSum: Int): Char {
    return when (iSum % 11) {
        10 -> '2'
        9 -> '3'
        8 -> '4'
        7 -> '5'
        6 -> '6'
        5 -> '7'
        4 -> '8'
        3 -> '9'
        2 -> 'x'
        1 -> '0'
        0 -> '1'
        else -> StrUtil.C_SPACE
    }
}

/**
 * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
 *
 * @param iArr
 * @return 身份证编码。
 */
private fun getPowerSum(iArr: CharArray): Int {
    var iSum = 0
    if (power.size == iArr.size) {
        for (i in iArr.indices) {
            iSum += Integer.valueOf(iArr[i].toString()) * power[i]
        }
    }
    return iSum
}
package commonx.core.date

import commonx.core.content.toChinese
import java.util.*

/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/7/11
 * @description
 **/
enum class Month(val value: Int) {
    /** 一月  */
    JANUARY(Calendar.JANUARY),
    /** 二月  */
    FEBRUARY(Calendar.FEBRUARY),
    /** 三月  */
    MARCH(Calendar.MARCH),
    /** 四月  */
    APRIL(Calendar.APRIL),
    /** 五月  */
    MAY(Calendar.MAY),
    /** 六月  */
    JUNE(Calendar.JUNE),
    /** 七月  */
    JULY(Calendar.JULY),
    /** 八月  */
    AUGUST(Calendar.AUGUST),
    /** 九月  */
    SEPTEMBER(Calendar.SEPTEMBER),
    /** 十月  */
    OCTOBER(Calendar.OCTOBER),
    /** 十一月  */
    NOVEMBER(Calendar.NOVEMBER),
    /** 十二月  */
    DECEMBER(Calendar.DECEMBER),
    /** 十三月，仅用于农历  */
    UNDECIMBER(Calendar.UNDECIMBER);

    /**
     * 转中文，默认"月"结尾
     */
    fun toChinese(suffix: String = "月"): String {
        return "${this.value.toChinese()}月"
    }

    companion object {
        /**
         * 将 [Calendar]月份相关值转换为Month枚举对象<br></br>
         *
         * @see Calendar.JANUARY
         *
         * @see Calendar.FEBRUARY
         *
         * @see Calendar.MARCH
         *
         * @see Calendar.APRIL
         *
         * @see Calendar.MAY
         *
         * @see Calendar.JUNE
         *
         * @see Calendar.JULY
         *
         * @see Calendar.AUGUST
         *
         * @see Calendar.SEPTEMBER
         *
         * @see Calendar.OCTOBER
         *
         * @see Calendar.NOVEMBER
         *
         * @see Calendar.DECEMBER
         *
         * @see Calendar.UNDECIMBER
         *
         *
         * @param calendarMonthIntValue Calendar中关于Month的int值
         * @return [Month]
         */
        fun of(calendarMonthIntValue: Int): Month? {
            when (calendarMonthIntValue) {
                Calendar.JANUARY -> return JANUARY
                Calendar.FEBRUARY -> return FEBRUARY
                Calendar.MARCH -> return MARCH
                Calendar.APRIL -> return APRIL
                Calendar.MAY -> return MAY
                Calendar.JUNE -> return JUNE
                Calendar.JULY -> return JULY
                Calendar.AUGUST -> return AUGUST
                Calendar.SEPTEMBER -> return SEPTEMBER
                Calendar.OCTOBER -> return OCTOBER
                Calendar.NOVEMBER -> return NOVEMBER
                Calendar.DECEMBER -> return DECEMBER
                Calendar.UNDECIMBER -> return UNDECIMBER
                else -> return null
            }
        }
    }
}
package commonx.core.date

import java.util.*

/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/7/11
 * @description
 **/
enum class Week(val value: Int) {
    /** 周日  */
    SUNDAY(Calendar.SUNDAY),
    /** 周一  */
    MONDAY(Calendar.MONDAY),
    /** 周二  */
    TUESDAY(Calendar.TUESDAY),
    /** 周三  */
    WEDNESDAY(Calendar.WEDNESDAY),
    /** 周四  */
    THURSDAY(Calendar.THURSDAY),
    /** 周五  */
    FRIDAY(Calendar.FRIDAY),
    /** 周六  */
    SATURDAY(Calendar.SATURDAY);
    
    /**
     * 转换为中文名
     *
     * @param weekNamePrefix 表示星期的前缀，例如前缀为“星期”，则返回结果为“星期一”；前缀为”周“，结果为“周一”
     * @return 星期的中文名
     * @since 4.0.11
     */
    fun toChinese(weekNamePrefix: String = "星期"): String? {
        return when (this) {
            SUNDAY -> "${weekNamePrefix}日"
            MONDAY -> "${weekNamePrefix}一"
            TUESDAY -> "${weekNamePrefix}二"
            WEDNESDAY -> "${weekNamePrefix}三"
            THURSDAY -> "${weekNamePrefix}四"
            FRIDAY -> "${weekNamePrefix}五"
            SATURDAY -> "${weekNamePrefix}六"
        }
    }

    companion object {
        /**
         * 将 [Calendar]星期相关值转换为Week枚举对象<br></br>
         *
         * @see .SUNDAY
         *
         * @see .MONDAY
         *
         * @see .TUESDAY
         *
         * @see .WEDNESDAY
         *
         * @see .THURSDAY
         *
         * @see .FRIDAY
         *
         * @see .SATURDAY
         *
         *
         * @param calendarWeekIntValue Calendar中关于Week的int值
         * @return [Week]
         */
        fun of(calendarWeekIntValue: Int): Week? {
            return when (calendarWeekIntValue) {
                Calendar.SUNDAY -> SUNDAY
                Calendar.MONDAY -> MONDAY
                Calendar.TUESDAY -> TUESDAY
                Calendar.WEDNESDAY -> WEDNESDAY
                Calendar.THURSDAY -> THURSDAY
                Calendar.FRIDAY -> FRIDAY
                Calendar.SATURDAY -> SATURDAY
                else -> null
            }
        }
    }

}
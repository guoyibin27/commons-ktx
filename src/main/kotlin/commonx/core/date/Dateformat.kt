package commonx.core.date

import cn.hutool.core.date.DateField
import java.text.ParsePosition
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
 * 字符串转日期类型
 * @param pattern {@link DateFormatPattern}
 * @return Date 日期
 */
fun String.toDate(pattern: DateFormatPattern): Date {
    return toDate(pattern.pattern)
}

/**
 * 字符串转日期类型,自动判断传入的日期类型,如果类型无法解析，则返回null
 * @return Date 日期或者null
 */
fun String.toDateIf(): Date? {
    val pattern = getDateFormatPattern() ?: return null
    return toDate(pattern)
}

/**
 * 日期转字符串
 */
fun Date.dateString(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    val dateFormat = SimpleDateFormat()
    dateFormat.applyPattern(pattern)
    return dateFormat.format(this)
}

/**
 * 获取日期字符串的日期风格。失敗返回null。
 *
 * @param date 日期字符串
 * @return 日期风格
 */
fun String.getDateFormatPattern(): DateFormatPattern? {
    val map = HashMap<Long, DateFormatPattern>()
    val timestamps = ArrayList<Long>()
    for (p in DateFormatPattern.values()) {
        var dateTmp: Date? = null
        try {
            val pos = ParsePosition(0)
            val dateFormat = SimpleDateFormat(p.pattern, Locale.US)
            dateFormat.isLenient = false
            dateTmp = dateFormat.parse(this, pos)
            if (pos.index != length) {
                dateTmp = null
            }
        } catch (e: Exception) {
        }
        if (dateTmp != null) {
            timestamps.add(dateTmp.time)
            map[dateTmp.time] = p
        }
    }
    val accurateDate = getAccurateDate(timestamps)
    if (accurateDate != null) {
        return map[accurateDate.time]
    }
    return null
}

/**
 * 获取精确的日期
 *
 * @param timestamps 时间long集合
 * @return 日期
 */
private fun getAccurateDate(timestamps: List<Long>?): Date? {
    var date: Date? = null
    var timestamp: Long = 0
    val map = HashMap<Long, LongArray>()
    val absoluteValues = ArrayList<Long>()

    if (timestamps != null && timestamps.size > 0) {
        if (timestamps.size > 1) {
            for (i in timestamps.indices) {
                for (j in i + 1 until timestamps.size) {
                    val absoluteValue = Math.abs(timestamps[i] - timestamps[j])
                    absoluteValues.add(absoluteValue)
                    val timestampTmp = longArrayOf(timestamps[i], timestamps[j])
                    map[absoluteValue] = timestampTmp
                }
            }

            // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的。此时minAbsoluteValue为0
            // 因此不能将minAbsoluteValue取默认值0
            var minAbsoluteValue: Long = -1
            if (!absoluteValues.isEmpty()) {
                minAbsoluteValue = absoluteValues[0]
                for (i in 1 until absoluteValues.size) {
                    if (minAbsoluteValue > absoluteValues[i]) {
                        minAbsoluteValue = absoluteValues[i]
                    }
                }
            }

            if (minAbsoluteValue != -1L) {
                val timestampsLastTmp = map[minAbsoluteValue]

                val dateOne = timestampsLastTmp!![0]
                val dateTwo = timestampsLastTmp[1]
                if (absoluteValues.size > 1) {
                    timestamp = if (Math.abs(dateOne) > Math.abs(dateTwo))
                        dateOne
                    else
                        dateTwo
                }
            }
        } else {
            timestamp = timestamps[0]
        }
    }

    if (timestamp != 0L) {
        date = Date(timestamp)
    }
    return date
}

/**
 * 验证是否为生日<br>
 *
 * @return 是否为生日
 */
fun Date.isBirthday(): Boolean {
    val cal = Calendar.getInstance()
    // 验证年
    val thisYear = cal.get(DateField.YEAR.value)
    if (year() < 1930 || year() > thisYear) {
        return false;
    }

    // 验证月,month()从0开始，0-11 标示1-12月
    if (month() < 0 || month() > 11) {
        return false;
    }

    // 验证日
    if (dayOfMonth() < 1 || dayOfMonth() > 31) {
        return false;
    }
    if ((month() == 4 || month() == 6 || month() == 9 || month() == 11) && dayOfMonth() == 31) {
        return false;
    }
    if (month() == 2) {
        if (dayOfMonth() > 29 || (dayOfMonth() == 29 && !isLeapYear())) {
            return false;
        }
    }
    return true;
}

fun String.isBirthday(): Boolean {
    return this.toDateIf()?.isBirthday() ?: false
}

/**
 * 是否是闰年
 */
fun Date.isLeapYear(): Boolean {
    return GregorianCalendar().isLeapYear(year());
}

/**
 * date 转 calendar
 *
 * @param isMondayAsFirstDayOfWeek 是否周一为每周的开始
 */
fun Date.calendar(isMondayAsFirstDayOfWeek: Boolean = true): Calendar {
    val cal = Calendar.getInstance()
    if (isMondayAsFirstDayOfWeek) {
        cal.firstDayOfWeek = Calendar.MONDAY
    }
    cal.time = this
    return cal
}

/**
 * Long类型转calendar
 *
 * @param isMondayAsFirstDayOfWeek 是否周一为每周的开始
 */
fun Long.calendar(isMondayAsFirstDayOfWeek: Boolean = true): Calendar {
    val cal = Calendar.getInstance()
    if (isMondayAsFirstDayOfWeek) {
        cal.firstDayOfWeek = Calendar.MONDAY
    }
    cal.timeInMillis = this
    return cal
}

/**
 * 获得月份，从0开始计数
 */
fun Long.month(): Int {
    return this.calendar().get(Calendar.MONTH)
}

/**
 * 获得月份
 *
 * @return {@link Month}
 */
fun Long.monthEnum(): Month? {
    return Month.of(month())
}

/**
 * 获得指定日期是星期几，1表示周日，2表示周一
 *
 * @return 天
 */
fun Long.dayOfWeek(): Int {
    return this.calendar().get(Calendar.DAY_OF_WEEK)
}

/**
 * 获得指定日期是星期几
 *
 * @return {@link Week}
 */
fun Long.dayOfWeekEnum(): Week? {
    return Week.of(dayOfWeek())
}

/**
 * 获得指定日期是这个日期所在月份的第几天<br>
 *
 * @return 天
 */
fun Long.dayOfMonth(): Int {
    return this.calendar().get(Calendar.DAY_OF_MONTH)
}

/**
 * 获得指定日期是这个日期所在年份的第几天<br>
 *
 * @return 天
 */
fun Long.dayOfYear(): Int {
    return calendar().get(Calendar.DAY_OF_YEAR)
}

/**
 * 获得当前日期所属季度，从1开始计数<br>
 *
 * @return 第几个季度
 */
fun Long.quarter(): Int {
    return month() / 3 + 1
}

/**
 * 获得当前日期所属季度<br>
 *
 * @return 第几个季度 {@link Quarter}
 */
fun Long.quarterEnum(): Quarter? {
    return Quarter.of(quarter())
}

/**
 * 获取年份
 */
fun Long.year(): Int {
    return calendar().get(Calendar.YEAR)
}

/**
 * 获取小时
 *
 * @param 是否是24小时格式时间
 * @return 小时
 */
fun Long.hour(is24Clock: Boolean): Int {
    return if (is24Clock) calendar().get(Calendar.HOUR_OF_DAY) else calendar().get(Calendar.HOUR)
}

/**
 * 获得指定日期是星期几，1表示周日，2表示周一
 *
 * @return 天
 */
fun Date.dayOfWeek(isMondayAsFirstDayOfWeek: Boolean = true): Int {
    val calendar = this.calendar(isMondayAsFirstDayOfWeek)
    var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    if (isMondayAsFirstDayOfWeek) {
        dayOfWeek = dayOfWeek.minus(1)
        if (dayOfWeek == 0) {
            dayOfWeek = 7
        }
    }
    return dayOfWeek
}

/**
 * 获得指定日期是星期几
 *
 * @return {@link Week}
 */
fun Date.dayOfWeekEnum(): Week? {
    return Week.of(dayOfWeek())
}

/**
 * 获得指定日期是这个日期所在月份的第几天<br>
 *
 * @return 天
 */
fun Date.dayOfMonth(): Int {
    return this.calendar().get(Calendar.DAY_OF_MONTH)
}

/**
 * 获得指定日期是这个日期所在年份的第几天<br>
 *
 * @return 天
 */
fun Date.dayOfYear(): Int {
    return calendar().get(Calendar.DAY_OF_YEAR)
}

/**
 * 获得月份
 *
 * @return {@link Month}
 */
fun Date.monthEnum(): Month? {
    return Month.of(month())
}

/**
 * 获得当前日期所属季度，从1开始计数<br>
 *
 * @return 第几个季度
 */
fun Date.quarter(): Int {
    return month() / 3 + 1
}

/**
 * 获得当前日期所属季度<br>
 *
 * @return 第几个季度 {@link Quarter}
 */
fun Date.quarterEnum(): Quarter? {
    return Quarter.of(quarter())
}

/**
 * 获得月份，从0开始计数
 *
 * @return 月份
 */
fun Date.month(): Int {
    return calendar().get(Calendar.MONTH)
}

/**
 * 获取年份
 */
fun Date.year(): Int {
    return calendar().get(Calendar.YEAR)
}

/**
 * 获取小时
 *
 * @param 是否是24小时格式时间
 * @return 小时
 */
fun Date.hour(is24Clock: Boolean): Int {
    return if (is24Clock) calendar().get(Calendar.HOUR_OF_DAY) else calendar().get(Calendar.HOUR)
}

/**
 * 获取本周第一天的日期
 * @param isMondayAsFirstDayOfWeek 第一天是否是周一，默认第一天为周一
 */
fun Date.beginOfWeek(isMondayAsFirstDayOfWeek: Boolean = true): Date {
    val calendar = this.calendar(isMondayAsFirstDayOfWeek)
    if (isMondayAsFirstDayOfWeek) {
        // 设置周一为一周开始
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    } else {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    }
    return calendar.time
}

/**
 * 获取周的最后一天的日期
 *
 * @param isMondayAsFirstDayOfWeek 第一天是否是周一，默认为周一为一周的开始
 * @return Date 周最后一天的日期
 */
fun Date.endOfWeek(isMondayAsFirstDayOfWeek: Boolean = true): Date {
    val cal = calendar(isMondayAsFirstDayOfWeek)
    if (isMondayAsFirstDayOfWeek) {
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    } else {
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
    }
    return cal.time
}

fun Date.beginOfMonth(): Date {
    return calendar().apply { set(Calendar.DAY_OF_MONTH, 1) }.time
}

/**
 * 获取某月的最后一天日期
 *
 */
fun Date.endOfMonth(): Date {
    val cal = calendar()
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
    return cal.time
}

/**
 * 判断时间是否过期
 *
 * @param 日期
 * @return 是否过期
 *
 */
fun Date.isExpired(`when`: Date): Boolean {
    return after(`when`)
}

/**
 * 获取当前日期之前的某个日期
 */
fun Date.before(field: Int, value: Int): Date {
    val cal = calendar(false)
    cal.add(field, -value)
    return cal.time
}

/**
 * 获取当前时间之后的某个日期
 */
fun Date.after(field: Int, value: Int): Date {
    val cal = calendar(false)
    cal.add(field, value)
    return cal.time
}

/**
 * 获取某天的开始时间
 */
fun Date.beginTimeOfDay(): Date {
    val cal = calendar()
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.time
}

/**
 * 获取某天的最后时间
 */
fun Date.endTimeOfDay(): Date {
    val cal = calendar()
    cal.set(Calendar.HOUR_OF_DAY, 23)
    cal.set(Calendar.MINUTE, 59)
    cal.set(Calendar.SECOND, 59)
    cal.set(Calendar.MILLISECOND, 999)
    return cal.time
}

/**
 * 昨天
 */
fun Date.yesterday(): Date {
    return before(Calendar.DAY_OF_YEAR, 1).beginTimeOfDay()
}

/**
 * 今天
 */
fun Date.today(): Date {
    return now().beginTimeOfDay()
}

/**
 * 现在时间
 */
fun Date.now(): Date {
    return Date()
}

/**
 * 明天
 */
fun Date.tomorrow(): Date {
    return after(Calendar.DAY_OF_YEAR, 1).beginTimeOfDay()
}

/**
 * 上周
 */
fun Date.lastWeek(): Date {
    return before(Calendar.WEEK_OF_YEAR, 1).beginTimeOfDay()
}

/**
 * 下周
 */
fun Date.nextWeek(): Date {
    return after(Calendar.WEEK_OF_YEAR, 1).beginTimeOfDay()
}

/**
 * 下个月
 */
fun Date.lastMonth(): Date {
    return before(Calendar.MONTH, 1).beginTimeOfDay()
}

/**
 * 上个月
 */
fun Date.nextMonth(): Date {
    return after(Calendar.MONTH, 1).beginTimeOfDay()
}

/**
 * 根据出生年月日计算年龄
 *
 * @param 日期 默认为今天
 * @return 年龄
 */
fun Date.calculateAge(date: Date = now()): Int {
    //如果给定的日期 在 出生年月日 之前，则返回 0
    if (date.before(this)) {
        return 0
    }

    var age = date.year() - this.year()

    //如果出生月和给定日期的月份相同,则判断日期
    if (date.month() == this.month()) {
        if (date.dayOfMonth() < dayOfMonth()) {
            age--
        }
    } else if (date.month() < this.month()) {
        age--
    }
    return age
}

/**
 * 根据出生年月日计算年龄
 *
 * @param 日期 默认为今天
 * @return 年龄
 */
fun String.calculateAge(date: Date = Date()): Int {
    return this.toDateIf()?.calculateAge(date) ?: 0
}

/**
 * 根据出生年月日计算年龄
 *
 * @param 日期 默认为今天
 * @return 年龄
 */
fun String.calculateAge(date: String): Int {
    return this.toDateIf()?.calculateAge(date.toDateIf() ?: Date()) ?: 0
}

/**
 * 获取某季度的开始时间
 */
fun String.beginOfQuarter(): Date {
    return this.toInt().beginOfQuarter()
}

/**
 * 获取某季度的结束时间
 */
fun String.endOfQuarter(): Date {
    return this.toInt().endOfQuarter()
}

/**
 * 获取某季度的开始时间
 */
fun Int.beginOfQuarter(): Date {
    val cal = Calendar.getInstance()
    cal.firstDayOfWeek = Calendar.MONDAY
    cal.set(Calendar.MONTH, this.toInt() * 3 - 3)
    cal.set(Calendar.DAY_OF_MONTH, 1)
    return cal.time.beginTimeOfDay()
}

/**
 * 获取某季度的结束时间
 */
fun Int.endOfQuarter(): Date {
    val cal = Calendar.getInstance()
    cal.firstDayOfWeek = Calendar.MONDAY
    cal.set(Calendar.MONTH, this.toInt() * 3 - 1)
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
    return cal.time.beginTimeOfDay()
}


fun main(args: Array<String>) {
//    val date = "1039-11-11"
//    println(date.toDateIf()!!.calculateAge())
//    println(date.calculateAge())
//    println(date.calculateAge("1900-01-01"))
//    println(date.calculateAge("1900-01-01".toDateIf()!!))
//    println(date.)
    val t = "1988-01-01"
    println(t.isBirthday())

}
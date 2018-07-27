package commonx.core.date

/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/7/27
 * @description
 **/
enum class DateFormatPattern(val pattern: String) {

    YYYY_MM("yyyy-MM"),
    YYYY_MM_DD("yyyy-MM-dd"),
    YYYYMMDD("yyyyMMdd"),
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
    YYYY_MM_EN("yyyy/MM"),
    YYYY_MM_DD_EN("yyyy/MM/dd"),
    YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm"),
    YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss"),
    MM_DD_YYYY_HH_MM_SS_EN("MM/dd/yyyy HH:mm:ss"),
    MM_DD_YYYY_HH_MM_EN("MM/dd/yyyy HH:mm"),
    MM_DD_YYYY_HH_EN("MM/dd/yyyy HH"),
    MM_DD_YYYY_EN("MM/dd/yyyy"),
    MM_YYYY_EN("MM/yyyy"),

    YYYYMMDDHHMMSS("yyyyMMddhhmmss"),

    YYYY_MM_CN("yyyy年MM月"),
    YYYY_MM_DD_CN("yyyy年MM月dd日"),
    YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm"),
    YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss"),

    HH_MM("HH:mm"),
    HH_MM_SS("HH:mm:ss"),

    MM_DD("MM-dd"),
    MM_DD_HH_MM("MM-dd HH:mm"),
    MM_DD_HH_MM_SS("MM-dd HH:mm:ss"),

    MM_DD_EN("MM/dd"),
    MM_DD_HH_MM_EN("MM/dd HH:mm"),
    MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss"),

    MM_DD_CN("MM月dd日"),
    MM_DD_HH_MM_CN("MM月dd日 HH:mm"),
    MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss"),

    E_MMM_DD_KK_MM_SS_Z_Y("E MMM dd HH:mm:ss Z yyyy");

    companion object {
        fun enumOf(pattern: String): DateFormatPattern {
            val found = DateFormatPattern.values().find { pattern == it.pattern }
            return found ?: throw IllegalArgumentException("not found DateFormatPattern with $pattern")
        }
    }
}
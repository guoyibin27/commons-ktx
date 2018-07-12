package commonx.core.content

/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/7/11
 * @description
 **/
fun Int.toChinese(): String {
    return ""
}

fun Int.calculatePages(pageSize: Int): Int {
    return this.minus(1).div(pageSize).plus(1)
}
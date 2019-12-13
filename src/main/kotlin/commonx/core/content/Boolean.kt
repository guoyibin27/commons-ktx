package commonx.core.content

fun Boolean.toInt(): Int {
    return if (this) 1 else 0
}

fun Boolean.toChar(): Char {
    return toInt().toChar()
}

fun Boolean.toByte(): Byte {
    return toInt().toByte()
}

fun Boolean.toLong(): Long {
    return toInt().toLong()
}

fun Boolean.toShort(): Short {
    return toInt().toShort()
}

fun Boolean.toFloat(): Float {
    return toInt().toFloat()
}

fun Boolean.toDouble(): Double {
    return toInt().toDouble()
}

fun Boolean.toOnOrOff(): String {
    return if (this) "on" else "off"
}

fun Boolean.toYesOrNo(): String {
    return if (this) "yes" else "no"
}

val TRUE_STR_LIST = listOf("true", "yes", "y", "t", "ok", "on", "1", "是", "对", "真")

fun String.toBoolean(): Boolean {
    return if (this.isNotNullOrEmpty()) {
        TRUE_STR_LIST.contains(this.toLowerCase())
    } else {
        false
    }
}


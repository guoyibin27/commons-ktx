package commonx.core.date

/**
 * @version 1.0.0
 * @author: Gyb
 * @date 2018/7/11
 * @description
 **/
enum class Quarter(val value: Int) {

    /** 第一季度  */
    Q1(1),
    /** 第二季度  */
    Q2(2),
    /** 第三季度  */
    Q3(3),
    /** 第四季度  */
    Q4(4);


    companion object {

        /**
         * 将 季度int转换为Season枚举对象</br>
         *
         * @see .Q1
         * @see .Q2
         * @see .Q3
         * @see .Q4
         *
         * @param intValue 季度int表示
         * @return [Quarter]
         */
        fun of(intValue: Int): Quarter? {
            return when (intValue) {
                1 -> Q1
                2 -> Q2
                3 -> Q3
                4 -> Q4
                else -> null
            }
        }
    }
}

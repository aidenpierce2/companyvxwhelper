package com.xwq.companyvxwhelper

import org.junit.Assert.*
import org.junit.Test
import java.text.DecimalFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test01 () {
        var test : String = "fdsdfsfs"
        var decimalFormat : DecimalFormat = DecimalFormat("#.00000")

//        return decimalFormat.format(test)
    }
}
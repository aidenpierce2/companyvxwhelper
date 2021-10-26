package com.xwq.companyvxwhelper.base

/**
 * create by xwq in 2021 6 03
 */

class BaseNetworkResponse<T> : BaseEntity() {
    var isError: Boolean = false
        get() = 0 != code
    var data: T? = null
    val code: Int = 0
    var msg: String? = null

}

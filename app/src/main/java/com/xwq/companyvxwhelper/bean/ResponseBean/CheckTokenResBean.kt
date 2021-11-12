package com.xwq.companyvxwhelper.bean.ResponseBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class CheckTokenResBean(var isValid : Boolean) : BaseEntity(){

    constructor() : this(false)
}
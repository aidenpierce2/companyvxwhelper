package com.xwq.companyvxwhelper.bean.dataBindingBean

import com.xwq.companyvxwhelper.base.BaseEntity

class LoadingDataBindBean(imgaeResourceId : Int) :BaseEntity(){

    var imgaeResourceId : Int? = 0

    init {
        this@LoadingDataBindBean.imgaeResourceId = imgaeResourceId
    }
}
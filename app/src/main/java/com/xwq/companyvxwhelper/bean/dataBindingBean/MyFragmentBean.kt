package com.xwq.companyvxwhelper.bean.dataBindingBean

import com.xwq.companyvxwhelper.base.BaseEntity

class MyFragmentBean(var headImgUrl: String, var nickName: String, var userPhone: String, var functionBean: Array<FunctionItemBean>) : BaseEntity(){


    // 第二部分 下面的recyclerview
    data class FunctionBean(var functionArray : Array<FuntionItemBean>) : BaseEntity() {

    }

    data class FuntionItemBean(var backgroundColor : Int, var title : String, var textSize : Int) :BaseEntity() {

    }
}
package com.xwq.companyvxwhelper.bean.dataBindingBean

import android.content.Context
import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.base.BaseEntity

 class DialogMainGuideBean : BaseEntity {

    var contentStr : String = ""
    var leftChooseStr : String = ""
    var rightChooseStr : String = ""

     constructor() : this("", "" ,""){

     }

    constructor(contentStrId : Int, leftChooseStrId : Int, rightChooseStrId : Int) {
        var app : Context = MyApplication.app
        this.contentStr = app.getString(contentStrId)
        this.leftChooseStr = app.getString(leftChooseStrId)
        this.rightChooseStr = app.getString(rightChooseStrId)
    }

    constructor(contentStr : String, leftChooseStr : String, rightChooseStr : String) {
        this.contentStr = contentStr
        this.leftChooseStr = leftChooseStr
        this.rightChooseStr = rightChooseStr
    }

    constructor(contentStr : String, leftChooseStrId : Int, rightChooseStrId : Int) {
        var app : Context = MyApplication.app
        this.contentStr = contentStr
        this.leftChooseStr = app.getString(leftChooseStrId)
        this.rightChooseStr = app.getString(rightChooseStrId)
    }


}
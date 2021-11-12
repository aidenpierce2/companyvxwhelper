package com.xwq.companyvxwhelper.bean.dataBindingBean

import android.graphics.drawable.Drawable
import com.xwq.companyvxwhelper.base.BaseEntity

data class RadioButtonBean(var items : List<RadioButtonItem>?, var mark : Int) : BaseEntity(){

    data class RadioButtonItem(var title : String, var tag : String, var image : Drawable, var redDotImage : String) : BaseEntity() {

    }
}
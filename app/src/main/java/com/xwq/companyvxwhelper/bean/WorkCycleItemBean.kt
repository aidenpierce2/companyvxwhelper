package com.xwq.companyvxwhelper.bean

import android.graphics.drawable.Drawable
import android.view.View
import com.xwq.companyvxwhelper.base.BaseEntity

data class WorkCycleItemBean(var headerImgRes : Drawable?, var needSwitchButton : Boolean, var switchChecked : Boolean, var contextStr : String) : BaseEntity() {


}
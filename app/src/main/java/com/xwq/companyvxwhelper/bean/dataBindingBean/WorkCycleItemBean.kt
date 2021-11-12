package com.xwq.companyvxwhelper.bean.dataBindingBean

import android.graphics.drawable.Drawable
import com.xwq.companyvxwhelper.base.BaseEntity

data class WorkCycleItemBean(var headerImgRes : Drawable?, var needSwitchButton : Boolean, var switchChecked : Boolean, var contextStr : String) : BaseEntity() {


}
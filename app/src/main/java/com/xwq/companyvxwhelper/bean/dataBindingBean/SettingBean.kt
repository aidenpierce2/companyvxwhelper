package com.xwq.companyvxwhelper.bean.dataBindingBean

import android.graphics.drawable.Drawable
import com.xwq.companyvxwhelper.base.BaseEntity

data class SettingDBBean   (var headerStr : String, var contentStr : String, var contentDrawableUrl: String, var showStr : Boolean, var rightDrawable: Drawable) : BaseEntity() {
}
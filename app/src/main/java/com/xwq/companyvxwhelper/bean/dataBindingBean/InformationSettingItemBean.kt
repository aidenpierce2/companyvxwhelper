package com.xwq.companyvxwhelper.bean.dataBindingBean

import android.graphics.drawable.Drawable
import com.xwq.companyvxwhelper.base.BaseEntity

class InformationSettingItemBean(var imageSrc : Drawable?, var contentSequence : CharSequence?) :
    BaseEntity() {

        constructor() : this(null, "") {

        }

}
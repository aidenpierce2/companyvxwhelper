package com.xwq.companyvxwhelper.bean

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.xwq.companyvxwhelper.base.BaseEntity

data class SearchDialogItemBean(var posIcon : Drawable?, var latitude : Double, var lontitude : Double, var nickName : String, var detailAddress : String?, var locIcon : Drawable?,var facilityType : String,
                                var province : String, var city : String, var county : String, var distance : String) : BaseEntity() {

}
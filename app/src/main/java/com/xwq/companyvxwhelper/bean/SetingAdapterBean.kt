package com.xwq.companyvxwhelper.bean

import com.xwq.companyvxwhelper.base.BaseEntity
import com.xwq.companyvxwhelper.bean.Enum.InputInformationTypeEnum
import com.xwq.companyvxwhelper.bean.Enum.MultiAdapterEnum

class SettingAdapterBean : BaseEntity {

    var multiAdapterEnum : MultiAdapterEnum = MultiAdapterEnum.SEPARATOR
    var titleStr : String = ""
    var contentStr : String = ""
    var showExamineIcon : Boolean = false
    var inputInformationSettingItemBean : InputInformationTypeEnum = InputInformationTypeEnum.OTHER
    var tag : String = ""

    constructor(multiAdapterEnum : MultiAdapterEnum) : this(multiAdapterEnum, ""){

    }

    constructor(multiAdapterEnum : MultiAdapterEnum, titleStr : String) : this(multiAdapterEnum, titleStr, "") {

    }

    constructor(multiAdapterEnum : MultiAdapterEnum, titleStr : String, contentStr : String) : this(multiAdapterEnum, titleStr, contentStr, false) {

    }

    constructor(multiAdapterEnum : MultiAdapterEnum, titleStr : String, contentStr : String, showExamineIcon : Boolean) : this(multiAdapterEnum, titleStr, contentStr, showExamineIcon, InputInformationTypeEnum.OTHER) {

    }

    constructor(multiAdapterEnum : MultiAdapterEnum, titleStr : String, contentStr : String, showExamineIcon : Boolean, inputInformationSettingItemBean : InputInformationTypeEnum ) : this(multiAdapterEnum, titleStr, contentStr, showExamineIcon, inputInformationSettingItemBean, "") {

    }

    constructor(multiAdapterEnum : MultiAdapterEnum, titleStr : String, contentStr : String, showExamineIcon : Boolean, inputInformationSettingItemBean : InputInformationTypeEnum, tag : String) {
        this.multiAdapterEnum = multiAdapterEnum
        this.titleStr = titleStr
        this.contentStr = contentStr
        this.showExamineIcon = showExamineIcon
        this.inputInformationSettingItemBean = inputInformationSettingItemBean
        this.tag = tag
    }
}
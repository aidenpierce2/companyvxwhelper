package com.xwq.companyvxwhelper.bean.dataBindingBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class LocationFragmentBean(var searchText : String?, var locationNickName : String?) : BaseEntity() {

    constructor() : this("", "") {}
}
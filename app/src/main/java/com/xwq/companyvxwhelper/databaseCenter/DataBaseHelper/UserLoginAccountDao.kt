package com.xwq.companyvxwhelper.databaseCenter.DataBaseHelper

import android.content.Context
import android.text.TextUtils
import com.xwq.companyvxwhelper.base.BaseDataBaseHelper
import com.xwq.companyvxwhelper.base.BaseEntity
import com.xwq.companyvxwhelper.bean.DataBaseBean.UserLoginAccountDBBean
import com.xwq.companyvxwhelper.bean.DataBaseBean.UserLoginAccountDBBeanDao
import org.greenrobot.greendao.AbstractDao

class UserLoginAccountDao(context : Context) : BaseDataBaseHelper(context) {

    override fun getSelfDao(): AbstractDao<BaseEntity, Long> {
        return mDaoSession!!.userLoginAccountDBBeanDao as AbstractDao<BaseEntity, Long>
    }

    override fun addOrReplace(T: BaseEntity) {
        var isCompanyBean : Boolean = T is UserLoginAccountDBBean;
        if (isCompanyBean) {
            mSelfDao!!.insertOrReplace(T)
        }
    }

    override fun delete(T: BaseEntity) {
        var isCompanyBean : Boolean = T is UserLoginAccountDBBean;
        if (isCompanyBean) {
            mSelfDao!!.delete(T)
        }
    }

    override fun selectAll(): List<BaseEntity> {
        return mSelfDao!!.queryBuilder().list()
    }

    fun getUserLoginAccountByUUid(uuidStr : String) : UserLoginAccountDBBean? {
        if (uuidStr.isNullOrEmpty()) {
            return null
        }
        return mSelfDao!!.queryBuilder().where(UserLoginAccountDBBeanDao.Properties.UserUUid.eq(uuidStr)).build().unique() as UserLoginAccountDBBean
    }

}
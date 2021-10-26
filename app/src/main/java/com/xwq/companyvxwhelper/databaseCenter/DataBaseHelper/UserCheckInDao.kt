package com.xwq.companyvxwhelper.databaseCenter.DataBaseHelper

import android.content.Context
import com.xwq.companyvxwhelper.base.BaseDataBaseHelper
import com.xwq.companyvxwhelper.base.BaseEntity
import com.xwq.companyvxwhelper.bean.DataBaseBean.UserCheckInDBBean
import com.xwq.companyvxwhelper.bean.DataBaseBean.UserCheckInDBBeanDao
import org.greenrobot.greendao.AbstractDao

class UserCheckInDao(mContext : Context) : BaseDataBaseHelper(mContext) {

    override fun getSelfDao(): AbstractDao<BaseEntity, Long> {
        return mDaoSession!!.userCheckInDBBeanDao as AbstractDao<BaseEntity, Long>
    }

    override fun addOrReplace(T: BaseEntity) {
        var isUserCheckInDBBean : Boolean = T is UserCheckInDBBean
        if (!isUserCheckInDBBean) {
            return
        }

        mSelfDao?.insertOrReplace(T)
    }

    override fun delete(T: BaseEntity) {
        var isUserCheckInDBBean : Boolean = T is UserCheckInDBBean
        if (isUserCheckInDBBean) {
            return
        }

        mSelfDao?.delete(T)
    }

    override fun selectAll(): List<BaseEntity> {
        return mSelfDao?.queryBuilder()!!.list()
    }

    fun findDataByUserId(userId : Int) : UserCheckInDBBean? {
        if (userId < 0)
            return null

        var consistentDataList : ArrayList<UserCheckInDBBean> = mSelfDao!!.queryBuilder().where(UserCheckInDBBeanDao.Properties.UserUUid.eq(userId)).build().unique() as ArrayList<UserCheckInDBBean>
        if (consistentDataList.isEmpty()) {
            return null
        } else {
            return consistentDataList.get(0)
        }
    }
}
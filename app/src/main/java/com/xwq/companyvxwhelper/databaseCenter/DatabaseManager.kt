package com.xwq.companyvxwhelper.databaseCenter

import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.databaseCenter.DataBaseHelper.CompanyDao
import com.xwq.companyvxwhelper.databaseCenter.DataBaseHelper.UserCheckInDao
import com.xwq.companyvxwhelper.databaseCenter.DataBaseHelper.UserInfoDao
import com.xwq.companyvxwhelper.databaseCenter.DataBaseHelper.UserLoginAccountDao

class DatabaseManager {

    companion object {
        private lateinit var companyDao : CompanyDao
        private lateinit var userCheckInDao : UserCheckInDao
        private lateinit var userInfoDao: UserInfoDao
        private lateinit var userLoginAccountDao: UserLoginAccountDao

        fun getCompanyInstance() : CompanyDao {
            if (!this::companyDao.isInitialized || this::companyDao == null) {
                companyDao = CompanyDao(MyApplication.app)
            }
            return companyDao
        }

        fun getUserCheckInInstance() : UserCheckInDao {
            if (!this::companyDao.isLateinit || this::userCheckInDao == null) {
                userCheckInDao = UserCheckInDao(MyApplication.app)
            }
            return userCheckInDao
        }

        fun getUserInfoInstance() : UserInfoDao {
            if (!this::userInfoDao.isInitialized || this::userInfoDao == null) {
                userInfoDao = UserInfoDao(MyApplication.app)
            }
            return userInfoDao
        }

        fun getUserLoginAccountInstance() : UserLoginAccountDao {
            if (!this::userLoginAccountDao.isInitialized || this::userLoginAccountDao == null) {
                userLoginAccountDao = UserLoginAccountDao(MyApplication.app)
            }
            return userLoginAccountDao
        }
    }
}
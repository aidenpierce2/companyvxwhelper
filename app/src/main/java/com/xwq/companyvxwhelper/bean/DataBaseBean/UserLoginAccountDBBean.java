package com.xwq.companyvxwhelper.bean.DataBaseBean;

import com.xwq.companyvxwhelper.base.BaseEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserLoginAccountDBBean extends BaseEntity {

    @Id(autoincrement = true)
    Long id;

    @Index(unique = true)
    String userUUid;

    String userPhoneNum;

    String userWxAccount;

    String userWxPassword;

    boolean usePhoneAsAccount;

    boolean hasCurValid;

    @Generated(hash = 5337253)
    public UserLoginAccountDBBean(Long id, String userUUid, String userPhoneNum,
            String userWxAccount, String userWxPassword, boolean usePhoneAsAccount,
            boolean hasCurValid) {
        this.id = id;
        this.userUUid = userUUid;
        this.userPhoneNum = userPhoneNum;
        this.userWxAccount = userWxAccount;
        this.userWxPassword = userWxPassword;
        this.usePhoneAsAccount = usePhoneAsAccount;
        this.hasCurValid = hasCurValid;
    }

    @Generated(hash = 1097348114)
    public UserLoginAccountDBBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserUUid() {
        return this.userUUid;
    }

    public void setUserUUid(String userUUid) {
        this.userUUid = userUUid;
    }

    public String getUserPhoneNum() {
        return this.userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public String getUserWxAccount() {
        return this.userWxAccount;
    }

    public void setUserWxAccount(String userWxAccount) {
        this.userWxAccount = userWxAccount;
    }

    public String getUserWxPassword() {
        return this.userWxPassword;
    }

    public void setUserWxPassword(String userWxPassword) {
        this.userWxPassword = userWxPassword;
    }

    public boolean getUsePhoneAsAccount() {
        return this.usePhoneAsAccount;
    }

    public void setUsePhoneAsAccount(boolean usePhoneAsAccount) {
        this.usePhoneAsAccount = usePhoneAsAccount;
    }

    public boolean getHasCurValid() {
        return this.hasCurValid;
    }

    public void setHasCurValid(boolean hasCurValid) {
        this.hasCurValid = hasCurValid;
    }
}

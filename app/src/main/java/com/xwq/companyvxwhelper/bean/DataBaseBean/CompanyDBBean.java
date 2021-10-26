package com.xwq.companyvxwhelper.bean.DataBaseBean;

import androidx.annotation.Nullable;

import com.xwq.companyvxwhelper.base.BaseEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

@Entity
public class CompanyDBBean extends BaseEntity {

    @Id(autoincrement = true)
    Long id = 0L;

    @Index(unique = true)
    String userUuidStr = null;

    String companyName = "";

    int companyIcon = 0;

    String companyLontitude = "";

    String companyLatitude = "";

    int tickRange = 0;

    @Generated(hash = 573143460)
    public CompanyDBBean(Long id, String userUuidStr, String companyName, int companyIcon,
            String companyLontitude, String companyLatitude, int tickRange) {
        this.id = id;
        this.userUuidStr = userUuidStr;
        this.companyName = companyName;
        this.companyIcon = companyIcon;
        this.companyLontitude = companyLontitude;
        this.companyLatitude = companyLatitude;
        this.tickRange = tickRange;
    }

    @Generated(hash = 86265571)
    public CompanyDBBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserUuidStr() {
        return this.userUuidStr;
    }

    public void setUserUuidStr(String userUuidStr) {
        this.userUuidStr = userUuidStr;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyIcon() {
        return this.companyIcon;
    }

    public void setCompanyIcon(int companyIcon) {
        this.companyIcon = companyIcon;
    }

    public String getCompanyLontitude() {
        return this.companyLontitude;
    }

    public void setCompanyLontitude(String companyLontitude) {
        this.companyLontitude = companyLontitude;
    }

    public String getCompanyLatitude() {
        return this.companyLatitude;
    }

    public void setCompanyLatitude(String companyLatitude) {
        this.companyLatitude = companyLatitude;
    }

    public int getTickRange() {
        return this.tickRange;
    }

    public void setTickRange(int tickRange) {
        this.tickRange = tickRange;
    }

}

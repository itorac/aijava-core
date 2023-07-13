package com.aijava.core.security.common;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName UserEnums.java
 * @Description 角色类型，用来判定登陆身份。
 * @createTime 2021 年12 月20 日 15:13
 */
public enum UserEnums {
    MERCHANTS("商户"),
    MEMBER("会员"),
    MANAGER("管理员"),
    OPERATION("运营人员"),
    SYSTEM("系统用户");

    private final String roleName;

    UserEnums(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}

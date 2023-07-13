package com.aijava.core.common.utils.alipay.enums;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName AliPayMiniAppOptTypeEnum.java
 * @Description 支付宝小程序操作类别枚举对象
 * @createTime 2022 年04 月12 日 15:50
 */
public enum AliPayMiniAppOptCategoryEnum {

    AUDIT_APPLY("auditApply", "小程序提交审核"),
    AUDIT_CANCEL("auditCancel", "小程序撤销审核"),
    AUDITED_CANCEL("auditedCancel", "小程序退回开发"),
    GRAY_ON_LINE("grayOnline", "小程序灰度上架"),
    GRAY_CANCEL("grayCancel", "小程序结束灰度"),
    VERSION_UP_LOAD("versionUpLoad", "小程序基于模板上传版本"),
    VERSION_ON_LINE("versionOnLine", "小程序上架"),
    VERSION_OFF_LINE("versionOffLine", "小程序下架"),
    VERSION_ROLLBACK("versionRollback", "小程序回滚"),
    VERSION_DELETE("versionDelete", "小程序删除版本"),
    EXPERIENCE_CREATE("experienceCreate", "小程序生成体验版"),
    EXPERIENCE_CANCEL("experienceCancel", "小程序取消体验版"),
    BASE_INFO_MODIFY("baseInfoModify", "修改基础信息"),
    MEMBERS_QUERY("membersQuery", "查询成员列表"),
    MEMBERS_CREATE("membersCreate", "应用添加成员"),
    MEMBERS_DELETE("membersDelete", "应用删除成员"),
    GR_CODE_BIND("grCodeBind", "关联普通二维码"),
    GR_CODE_UNBIND("grCodeUnbind", "删除已关联普通二维码"),
    SERVICE_CONFIG_MODIFY("serviceConfigModify", "小程序设置客服方式"),
    SAFE_DOMAIN_CREATE("safeDomainCreate", "添加域白名单"),
    SAFE_DOMAIN_DELETE("safeDomainDelete", "删除域白名单"),
    INDIVIDUAL_CERTIFY("individualCertify", "个人账户升级个体工商户"),
    CATEGORY_REQUIRE_QUERY("categoryRequireQuery", "查询类目所需资质信息");

    /**
     * 操作类别ID
     */
    private String categoryId;
    /**
     * 操作类别名称
     */
    private String categoryName;

    AliPayMiniAppOptCategoryEnum(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

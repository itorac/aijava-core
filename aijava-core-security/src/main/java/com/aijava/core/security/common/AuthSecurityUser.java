package com.aijava.core.security.common;

import lombok.*;

import java.io.Serializable;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName AuthSecUser.java
 * @Description Security认证的用户基本信息对象
 * @createTime 2021 年12 月20 日 15:11
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthSecurityUser implements Serializable {

    /**  
	 * @Fields serialVersionUID : TODO(描述)
	 * @author xiegr
	 * @date 2021-12-30 03:50:45 
	 */  
	private static final long serialVersionUID = 925286892940946227L;
	/**
     * id
     */
    private String id;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 长期有效（用于手机app登录场景或者信任场景等）
     */
    private Boolean longTerm = false;

    /**
     * @see UserEnums
     * 角色
     */
    private UserEnums role;

    /**
     * 如果角色是商家，则存在此店铺id字段
     * storeId
     */
    private String storeId;

    /**
     * 如果角色是商家，则存在此店铺名称字段
     * storeName
     */
    private String storeName;

    /**
     * 是否是超级管理员
     */
    private Boolean isSuper = false;
    
    

    /**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the longTerm
	 */
	public Boolean getLongTerm() {
		return longTerm;
	}

	/**
	 * @param longTerm the longTerm to set
	 */
	public void setLongTerm(Boolean longTerm) {
		this.longTerm = longTerm;
	}

	/**
	 * @return the role
	 */
	public UserEnums getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(UserEnums role) {
		this.role = role;
	}

	/**
	 * @return the storeId
	 */
	public String getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	/**
	 * @return the storeName
	 */
	public String getStoreName() {
		return storeName;
	}

	/**
	 * @param storeName the storeName to set
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	/**
	 * @return the isSuper
	 */
	public Boolean getIsSuper() {
		return isSuper;
	}

	/**
	 * @param isSuper the isSuper to set
	 */
	public void setIsSuper(Boolean isSuper) {
		this.isSuper = isSuper;
	}

	public AuthSecurityUser(String userName, String id, String nickName, UserEnums role) {
        this.userName = userName;
        this.id = id;
        this.role = role;
        this.nickName = nickName;
    }

    public AuthSecurityUser(String userName, String id, UserEnums manager, String nickName, Boolean isSuper) {
        this.userName = userName;
        this.id = id;
        this.role = manager;
        this.isSuper = isSuper;
        this.nickName = nickName;
    }
}

package com.aijava.core.basemodel.pojo;

import java.io.Serializable;

/**
 * 
 * @ClassName: BasePageModel
 * @Description:分页查询需要继承此继承抽象实体
 * @author xiegr
 * @date 2021-11-29 04:58:39
 */
public class BasePageModel implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author xiegr
	 * @date 2021-11-29 04:59:15
	 */
	private static final long serialVersionUID = -2106317193153077534L;

	/**
	 * 每页 条数
	 */
	private int pageSize = 10;

	/**
	 * 页码,当前多少页
	 */
	private int pageNo = 0;

	/**
	 * 页码与每页条数的乘积
	 */
	private int offset = 0;

	public BasePageModel() {

	}

	public BasePageModel(int pageSize, int pageNo) {
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getOffset() {
		if (pageNo <= 1) {
			return offset;
		} else {
			return (pageNo - 1) * pageSize;
		}
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}

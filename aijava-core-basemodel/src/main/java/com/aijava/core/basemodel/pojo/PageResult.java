package com.aijava.core.basemodel.pojo;

import java.util.List;

import com.google.common.collect.Lists;


/**
 * @ClassName: PageResult
 * @Description: 分页封装 返回结果
 * @author xiegr
 * @date 2021-11-29 05:34:13
 */
public class PageResult extends BasePageModel {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -1155958318169758357L;

	/**
	 * 泛型列表
	 */
	private List<?> records=Lists.newArrayList();

	/**
	 * 总条数
	 */
	private int totalCount;
	
	/**
	 * 总页数
	 */
	private int totalPage;
	

	public PageResult() {

	}
	
	public PageResult(List<?> records, int totalCount,int pageSize,int pageNo) {
		super(pageSize,pageNo);
		this.records = records;
		this.totalCount = totalCount;
	}

	public List<?> getRecords() {
		return records;
	}

	public void setRecords(List<?> records) {
		this.records = records;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		int totalPage = totalCount / getPageSize();
		if (totalPage == 0 || totalCount % getPageSize() != 0) {
			totalPage++;
		}
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	@Override
	public String toString() {
		return "PageResult [records=" + records + ", totalCount=" + totalCount + ", totalPage=" + totalPage + "]";
	}

}

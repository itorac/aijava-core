/**  
 * @Title: BaseService.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-06 09:20:07 
 */
package com.aijava.core.repository.base.service;

import java.util.List;

import com.aijava.core.basemodel.pojo.BasePageModel;
import com.aijava.core.basemodel.pojo.PageResult;

/**
 * @ClassName: BaseService
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-06 09:20:07
 */
public interface BaseService<ID, T> {

	/**
	 * 插入
	 * 
	 * @param t
	 */
	public int save(T t);

	/**
	 * 批量插入
	 * 
	 * @param list
	 * @return
	 */
	public int saveBatch(List<T> list);

	/**
	 * 更新
	 * 
	 * @param t
	 */
	public int update(T t);

	/**
	 * 
	 * @Title: updateBatch
	 * @Description:批量更新
	 * @param list
	 * @return
	 * @author xiegr
	 * @date 2021-12-29 11:33:51
	 */
	public int updateBatch(List<T> list);

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 */
	public int deleteById(ID id);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteByIds(List<ID> ids);

	/**
	 * 
	 * @Title: queryList
	 * @Description: 查询对象列表
	 * @return
	 * @author xiegr
	 * @date 2021-12-20 02:46:06
	 */
	public List<T> queryList();

	/**
	 * 
	 * @Title: queryList
	 * @Description: 根据对象查询List
	 * @param t
	 * @return
	 * @author xiegr
	 * @date 2021-12-20 02:54:47
	 */
	public List<T> queryList(T t);

	/**
	 * 根据id找到对象
	 * 
	 * @param id
	 * @return
	 */
	public T queryById(ID id);

	/**
	 * 
	 * @Title: selectByIds
	 * @Description: 根据 ID列表查询
	 * @param @param  ids
	 * @param @return 设定文件
	 * @return List<T> 返回类型
	 *
	 */
	public List<T> queryByIds(List<ID> ids);

	/**
	 * 
	 * @Title: queryPageCount
	 * @Description:查询统计
	 * @param <Q>
	 * @param queryParams
	 * @return
	 * @author xiegr
	 * @date 2021-12-06 09:23:39
	 */
	public <Q extends BasePageModel> int queryPageCount(Q queryParams);

	/**
	 * 
	 * @Title: queryPage
	 * @Description: 返回分页数据及统计记录
	 * @param <Q>
	 * @param q
	 * @return
	 * @author xiegr
	 * @date 2021-12-06 09:23:29
	 */
	public <Q extends BasePageModel> PageResult queryPage(Q q);
}

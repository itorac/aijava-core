/**
 * @Title: BaseMapper.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-06 04:01:34
 */
package com.aijava.core.repository.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aijava.core.basemodel.pojo.BasePageModel;

/**
 * @ClassName: BaseMapper
 * @Description: 基础Mapper, 实现基础功能, 如果要求性能可以不继承此类
 * @author xiegr
 * @date 2021-12-06 04:01:34
 */
public interface BaseMapper<ID, T> {

	/**
	 *
	 * @Title: insert
	 * @Description: 保存
	 * @param t
	 * @return
	 * @author xiegr
	 * @date 2021-11-29 08:14:15
	 */
	public int insert(T t);

	/**
	 * 批量插入
	 *
	 * @param list
	 * @return
	 */
	public int insertBatch(List<T> list);

	/**
	 *
	 * @Title: update
	 * @Description: 更新
	 * @param t
	 * @return
	 * @author xiegr
	 * @date 2021-11-29 08:13:56
	 */
	int update(T t);

	/**
	 * 
	 * @Title: updateBatch
	 * @Description: 批量更新
	 * @param list
	 * @return
	 * @author xiegr
	 * @date 2021-12-29 11:31:24
	 */
	int updateBatch(List<T> list);

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	int deleteByIds(@Param("ids") List<ID> ids);

	/**
	 *
	 * @Title: deleteById
	 * @Description: 根据id删除
	 * @param id
	 * @return
	 * @author xiegr
	 * @date 2021-11-29 08:13:46
	 */
	int deleteById(@Param("id") ID id);

	/**
	 *
	 * @Title: selectById
	 * @Description: 根据id查询
	 * @param id
	 * @return
	 * @author xiegr
	 * @date 2021-11-29 08:22:29
	 */
	T selectById(@Param("id") ID id);

	/**
	 *
	 * @Title: selectList
	 * @Description:根据对象查询list
	 * @param object
	 * @return
	 * @author xiegr
	 * @date 2021-12-20 02:53:21
	 */
	List<T> selectList(Object object);

	/**
	 *
	 * @Title: selectByIds 根据 ID列表查询
	 * @param @param  ids
	 * @param @return 设定文件
	 * @return List<T> 返回类型
	 *
	 */
	public List<T> selectByIds(@Param("ids") List<ID> ids);

	/**
	 *
	 * @Title: selectPage
	 * @Description: 分页查询
	 * @param q
	 * @return
	 * @author xiegr
	 * @date 2021-11-29 05:50:41
	 */
	public <Q extends BasePageModel> List<T> selectPage(Q q);

	/**
	 *
	 * @Title: selectPageCount
	 * @Description: 查询统计
	 * @param q
	 * @return
	 * @author xiegr
	 * @date 2021-11-29 05:51:00
	 */
	public <Q extends BasePageModel> int selectPageCount(Q q);

}

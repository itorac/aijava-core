/**  
 * @Title: BaseServiceImpl.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-06 09:24:38 
 */
package com.aijava.core.repository.base.service.impl;

import java.util.List;

import com.aijava.core.repository.base.mapper.BaseMapper;
import com.aijava.core.repository.base.page.PageUtils;
import com.aijava.core.repository.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import com.aijava.core.basemodel.pojo.BasePageModel;
import com.aijava.core.basemodel.pojo.PageResult;

/**
 * @ClassName: BaseServiceImpl
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-06 09:24:38
 */
public abstract class BaseServiceImpl<ID, T> implements BaseService<ID, T> {

	@Autowired
    BaseMapper<ID, T> baseMapper;

	/**
	 * @Title: save
	 * @Description: TODO(描述)
	 * @param t
	 * @return
	 * @see com.cig.core.dal.base.service.BaseService#save(java.lang.Object)
	 * @author xiegr
	 * @date 2021-12-06 09:25:20
	 */
	@Override
	public int save(T t) {
		return baseMapper.insert(t);
	}

	/**
	 * @Title: saveBatch
	 * @Description: TODO(描述)
	 * @param list
	 * @return
	 * @see com.cig.core.dal.base.service.BaseService#saveBatch(java.util.List)
	 * @author xiegr
	 * @date 2021-12-06 09:25:20
	 */
	@Override
	public int saveBatch(List<T> list) {
		return baseMapper.insertBatch(list);
	}

	/**
	 * @Title: update
	 * @Description: TODO(描述)
	 * @param t
	 * @return
	 * @see com.cig.core.dal.base.service.BaseService#update(java.lang.Object)
	 * @author xiegr
	 * @date 2021-12-06 09:25:20
	 */
	@Override
	public int update(T t) {
		return baseMapper.update(t);
	}

	/**
	 * @Title: updateBatch
	 * @Description: 批量更新
	 * @param list
	 * @return
	 * @see BaseService#updateBatch(java.util.List)
	 * @author xiegr
	 * @date 2021-12-29 11:34:43
	 */
	@Override
	public int updateBatch(List<T> list) {
		return this.baseMapper.updateBatch(list);
	}

	/**
	 * @Title: deleteById
	 * @Description: TODO(描述)
	 * @param id
	 * @return
	 * @see com.cig.core.dal.base.service.BaseService#deleteById(java.lang.Object)
	 * @author xiegr
	 * @date 2021-12-06 09:25:20
	 */
	@Override
	public int deleteById(ID id) {
		return baseMapper.deleteById(id);
	}

	/**
	 * @Title: deleteByIds
	 * @Description: TODO(描述)
	 * @param ids
	 * @return
	 * @see com.cig.core.dal.base.service.BaseService#deleteByIds(java.util.List)
	 * @author xiegr
	 * @date 2021-12-06 09:25:20
	 */
	@Override
	public int deleteByIds(List<ID> ids) {
		return baseMapper.deleteByIds(ids);
	}

	/**
	 * @Title: queryList
	 * @Description: TODO(描述)
	 * @return
	 * @see com.cig.core.dal.base.service.BaseService#queryList()
	 * @author xiegr
	 * @date 2021-12-20 02:46:56
	 */
	@Override
	public List<T> queryList() {
		return baseMapper.selectList(null);
	}

	/**
	 * @Title: queryList
	 * @Description: TODO(描述)
	 * @param t
	 * @return
	 * @see com.cig.core.dal.base.service.BaseService#queryList(java.lang.Object)
	 * @author xiegr
	 * @date 2021-12-20 02:55:37
	 */
	@Override
	public List<T> queryList(T t) {
		return baseMapper.selectList(t);
	}

	/**
	 * @Title: queryById
	 * @Description: TODO(描述)
	 * @param id
	 * @return
	 * @see com.cig.core.dal.base.service.BaseService#queryById(java.lang.Object)
	 * @author xiegr
	 * @date 2021-12-06 09:25:20
	 */
	@Override
	public T queryById(ID id) {
		return baseMapper.selectById(id);
	}

	/**
	 * @Title: queryByIds
	 * @Description: TODO(描述)
	 * @param ids
	 * @return
	 * @see com.cig.core.dal.base.service.BaseService#queryByIds(java.util.List)
	 * @author xiegr
	 * @date 2021-12-06 09:25:20
	 */
	@Override
	public List<T> queryByIds(List<ID> ids) {
		return baseMapper.selectByIds(ids);
	}

	/**
	 * @Title: queryPageCount
	 * @Description: TODO(描述)
	 * @param q
	 * @return
	 * @see com.cig.core.dal.base.service.BaseService#queryPageCount(java.lang.Object)
	 * @author xiegr
	 * @date 2021-12-06 09:25:20
	 */
	@Override
	public <Q extends BasePageModel> int queryPageCount(Q q) {
		return baseMapper.selectPageCount(q);
	}

	/**
	 * @Title: queryPage
	 * @Description: TODO(描述)
	 * @param q
	 * @return
	 * @see com.cig.core.dal.base.service.BaseService#queryPage(java.lang.Object)
	 * @author xiegr
	 * @date 2021-12-06 09:25:20
	 */
	@Override
	public <Q extends BasePageModel> PageResult queryPage(Q q) {
		return PageUtils.queryPage(baseMapper, q);
	}

}

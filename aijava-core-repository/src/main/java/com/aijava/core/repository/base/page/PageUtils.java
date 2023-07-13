package com.aijava.core.repository.base.page;

import java.util.List;

import com.aijava.core.basemodel.pojo.BasePageModel;
import com.aijava.core.basemodel.pojo.PageResult;
import com.aijava.core.repository.base.mapper.BaseMapper;
import com.google.common.collect.Lists;

/**
 * 
 * @ClassName: PageUtils
 * @Description:分页工具类
 * @author xiegr
 * @date 2021-11-29 05:07:23
 */
public class PageUtils {

	/**
	 * 
	 * @Title: pageQuery
	 * @Description: 分页
	 * @param @param  baseMapper
	 * @param @param  queryParams
	 * @param @return 设定文件
	 * @return QueryResult<List<T>> 返回类型
	 *
	 */
	public static <ID, T, Q extends BasePageModel> PageResult queryPage(BaseMapper<ID, T> baseMapper, Q queryParams) {
		// 数量
		int totalCount = baseMapper.selectPageCount(queryParams);

		if (totalCount > 0) {
			// 数据列表
			List<T> list = baseMapper.selectPage(queryParams);

			return newResult(list, totalCount, queryParams.getPageSize(), queryParams.getPageNo());
		}

		return newResult(Lists.newArrayList(), totalCount, queryParams.getPageSize(), queryParams.getPageNo());
	}

	/**
	 * 
	 * @Title: pageQuery
	 * @Description: 自定义分页
	 * @param @param  list
	 * @param @param  totalCount
	 * @param @return 设定文件
	 * @return QueryResult<List<T>> 返回类型
	 *
	 */
	public static PageResult queryPageResult(List<?> list, int totalCount, int pageSize, int pageNo) {
		return newResult(list, totalCount, pageSize, pageNo);
	}

	/**
	 * 
	 * @Title: newResult
	 * @Description: 返回结果
	 * @param <T>
	 * @param records
	 * @param totalCount
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * @author xiegr
	 * @date 2021-11-29 05:03:51
	 */
	private static PageResult newResult(List<?> records, int totalCount, int pageSize, int pageNo) {
		return new PageResult(records, totalCount, pageSize, pageNo);
	}

}

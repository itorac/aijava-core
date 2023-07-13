package com.aijava.core.dal.datasource.cobar;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.aijava.core.dal.datasource.router.MultiDataSourceRouter;

import java.util.*;

/**
 */
public class CobarHelper {

	private static final Map<String, Boolean> daoToIsCobarMap = new HashMap<>();

	private static final Set<String> cobarSchemaSet = new HashSet<>();

	private static final String IS_COBAR_KEY_IN_YML = "cobar";

	private static final String COBAR_CLUSTER_NAME_KEY_IN_YML = "clusterName";

	/**
	 * <p>
	 * 根据dao判断所属的schema是不是cobar。
	 * </p>
	 * daoToIsCobarMap中的dao包含配置文件中配置的dao，不包含未配置的dao，比如配置文件中第一个schema正常不会配置dao
	 * 
	 * @param daoStr
	 * @return
	 */
	public static boolean isCobarByDaoStr(String daoStr) {
		Boolean isCobar = daoToIsCobarMap.get(daoStr);
		if (isCobar != null) {
			return isCobar;
		} else {
			return isCobarSchema(MultiDataSourceRouter.getDefaultDBSchema());
		}
	}

	/**
	 * 设置dao所属的schema是不是cobar
	 *
	 * @param daos
	 * @param isCobar
	 */
	public static void setDaosToIsCobar(List<String> daos, boolean isCobar) {
		if (daos != null) {
			for (String dao : daos) {
				daoToIsCobarMap.put(dao, isCobar);
			}
		}

	}

	public static void addCobarSchema(String schema) {
		cobarSchemaSet.add(StringUtils.trim(schema));
	}

	public static boolean isCobarSchema(String schema) {
		return cobarSchemaSet.contains(schema);
	}

	public static boolean hasCobarSchema() {
		return CollectionUtils.isNotEmpty(cobarSchemaSet);
	}

	public static boolean isCobarFromYmlMap(Map<String, Object> dbinfo) {
		if (dbinfo.get(IS_COBAR_KEY_IN_YML) != null && (Boolean) dbinfo.get(IS_COBAR_KEY_IN_YML)) {
			return true;
		}
		return false;
	}

	public static void storeCobarClusterName(Map<String, Object> dbinfo) {
		String cobarClusterName = getCobarClusterName(dbinfo);
		System.out.println("cobarClusterName:" + cobarClusterName);
		// CobarWatcher.addCobarCluserName(cobarClusterName);
	}

	public static String getCobarClusterName(Map<String, Object> dbinfo) {
		if (dbinfo == null) {
			// return CobarWatcher.DEFAUL_COBAR_CLUSTER_NAME;
		}
		String cobarClusterName = (String) dbinfo.get(COBAR_CLUSTER_NAME_KEY_IN_YML);
		cobarClusterName = cobarClusterName == null ? "cobar" : cobarClusterName;
		return cobarClusterName.trim();
	}
}

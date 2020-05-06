package com.kq.sharding.jdbc.api;

/**
 * 清理缓存
 * @author kq
 * @since 2020-04-26
 */
public interface CacheClear {

    /**
     * 清理机构缓存
     * @param orgId
     */
    public void clearOrgCache(String orgId);


    /**
     * 清理租户缓存  保存该租户下所有的机构缓存
     * @param tenantId
     */
    public void clearTenantCache(String tenantId);

}

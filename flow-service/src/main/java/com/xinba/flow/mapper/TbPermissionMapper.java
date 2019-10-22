package com.xinba.flow.mapper;


import com.xinba.common.domain.admin.TbPermission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

/**
 * 权限表(TbPermission)表数据库访问层
 *
 * @author makejava
 * @since 2019-04-22 17:04:29
 */
public interface TbPermissionMapper extends MyMapper<TbPermission> {

    /**
     * 通过ID查询用户对应的所有权限
     *
     * @param id 主键
     * @return List<TbPermission> 集合对象
     */
    List<TbPermission> selectByUserId(Long id);

    List<TbPermission> selectPermissionByRoleId(@Param("firstUrl") String firstUrl);

}
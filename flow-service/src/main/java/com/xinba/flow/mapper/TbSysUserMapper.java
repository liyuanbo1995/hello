package com.xinba.flow.mapper;

import com.xinba.common.domain.admin.TbSysUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

/**
 * 用户表(TbSysUser)表数据库访问层
 *
 * @author makejava
 * @since 2019-04-22 16:18:15
 */
public interface TbSysUserMapper extends MyMapper<TbSysUser> {

    TbSysUser selectByUserId(@Param("id") String id);

    TbSysUser selectByUserName(@Param("name") String name);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TbSysUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbSysUser 实例对象
     * @return 对象列表
     */
    List<TbSysUser> queryAll(TbSysUser tbSysUser);

    /**
     * 新增数据
     *
     * @param tbSysUser 实例对象
     * @return 影响行数
     */
    int insert(TbSysUser tbSysUser);

    /**
     * 修改数据
     *
     * @param tbSysUser 实例对象
     * @return 影响行数
     */
    int update(TbSysUser tbSysUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
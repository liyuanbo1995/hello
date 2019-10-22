package com.xinba.flow.mapper;


import com.xinba.flow.entity.SysMenu;
import java.util.List;

/**
 * 菜单权限表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2019-07-15 09:35:12
 */
public interface SysMenuMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    SysMenu queryById(String menuId);

    /**
     * 查询所有子菜单
     *
     * @return 对象列表
     */
    List<SysMenu> queryAllche();

    /**
     * 查询所有父节点
     *
     * @return 对象列表
     */
    List<SysMenu> queryAllRoot();

    /**
     * 通过父级菜单ID作为筛选条件查询
     *
     * @param pid 父级菜单id
     * @return 对象列表
     */
    List<SysMenu> queryAllByPid(String pid);

    /**
     * 新增数据
     *
     * @param sysMenu 实例对象
     * @return 影响行数
     */
    int insert(SysMenu sysMenu);

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 影响行数
     */
    int update(SysMenu sysMenu);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    int deleteById(String menuId);

}
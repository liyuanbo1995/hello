package com.xinba.flow.service;

import com.xinba.flow.entity.SysMenu;

import java.util.List;
import java.util.Map;

/**
 * 菜单权限表(SysMenu)表服务接口
 *
 * @author makejava
 * @since 2019-07-15 09:32:02
 */
public interface SysMenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    SysMenu queryById(String menuId);

    /**
     * 根据条件查询多个菜单
     *
     * @return List<SysMenu>
     */
    List<SysMenu> queryAllMenus();

    /**
     * 查询父级菜单下的所有子菜单
     *
     * @param pid 父级
     * @return List<SysMenu>
     */
    List<SysMenu> queryMenusByPid(String pid);

    /**
     * 新增数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    SysMenu insert(SysMenu sysMenu);

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    SysMenu update(SysMenu sysMenu);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 是否成功
     */
    boolean deleteById(String menuId);

}
package com.xinba.flow.service.impl;

import com.xinba.flow.entity.SysMenu;
import com.xinba.flow.mapper.SysMenuMapper;
import com.xinba.flow.service.SysMenuService;
import com.xinba.flow.util.TreeToolUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单权限表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2019-07-15 09:32:02
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    @Override
    public SysMenu queryById(String menuId) {
        return this.sysMenuMapper.queryById(menuId);
    }

    /**
     * 查询所有菜单
     *
     * @return List<SysMenu>
     */
    @Override
    public List<SysMenu> queryAllMenus() {
        List<SysMenu> result =null;
        List<SysMenu> rootList = sysMenuMapper.queryAllRoot();
        if(rootList!=null){
            List<SysMenu> bodyList = sysMenuMapper.queryAllche();
            TreeToolUtils utils =  TreeToolUtils.getTreeToolUtils(rootList,bodyList);
            result =  utils.getTree();
        }

        return result;
    }

    /**
     * 查询父级菜单下的所有子菜单
     *
     * @param pid 父级
     * @return List<SysMenu>
     */
    @Override
    public List<SysMenu> queryMenusByPid(String pid) {
        return sysMenuMapper.queryAllByPid(pid);
    }

    /**
     * 新增数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    @Override
    public SysMenu insert(SysMenu sysMenu) {
        this.sysMenuMapper.insert(sysMenu);
        return sysMenu;
    }

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    @Override
    public SysMenu update(SysMenu sysMenu) {
        this.sysMenuMapper.update(sysMenu);
        return this.queryById(sysMenu.getMenuId());
    }

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String menuId) {
        return this.sysMenuMapper.deleteById(menuId) > 0;
    }
}
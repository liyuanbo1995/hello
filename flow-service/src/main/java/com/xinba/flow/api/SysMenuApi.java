package com.xinba.flow.api;

import com.xinba.flow.entity.SysMenu;
import com.xinba.flow.service.SysMenuService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单权限表(SysMenu)表控制层
 *
 * @author makejava
 * @since 2019-07-15 09:32:02
 */
@RestController
@RequestMapping("menu")
public class SysMenuApi {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 查询所有Tree菜单
     *
     * @return List<SysMenu> Json
     */
    @ApiOperation(value="获取系统菜单栏返回TreeJson", notes="查询5000以上的菜单")
    @GetMapping(value = "/all")
    public List<SysMenu> all() {
        return this.sysMenuService.queryAllMenus();
    }


    /**
     * 查询所有Tree菜单
     *
     * @return List<SysMenu> Json
     */
    @ApiOperation(value="获取系统菜单栏返回TreeJson", notes="查询5000以上的菜单")
    @GetMapping(value = "/test")
    public List<SysMenu> test() {
        return this.sysMenuService.queryAllMenus();
    }


 /*   *//**
     * 查询所有Tree菜单
     *
     * @return List<SysMenu> Json
     *//*
    @ApiOperation(value="获取系统菜单栏返回TreeJson", notes="查询5000以上的菜单")
    @GetMapping(value = "/findtc")
    public Wrapper<SysMenu> findtc(@PathParam("userid") String userid) {
        List<SysMenu> sysMenus= sysMenuService.queryAllMenus();
        SysMenu s =sysMenus.get(1);

        return  new Wrapper<>(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,s );
    }*/
}
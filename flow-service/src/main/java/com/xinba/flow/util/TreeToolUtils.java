package com.xinba.flow.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xinba.flow.entity.SysMenu;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/26.
 */
public class TreeToolUtils {

    private List<SysMenu> rootList; //根节点对象存放到这里

    private List<SysMenu> bodyList; //其他节点存放到这里，可以包含根节点

    public TreeToolUtils(List<SysMenu> rootList, List<SysMenu> bodyList) {
        this.rootList = rootList;
        this.bodyList = bodyList;
    }

    public static TreeToolUtils  getTreeToolUtils(List<SysMenu> rootList, List<SysMenu> bodyList) {
        return new TreeToolUtils(rootList,bodyList);
    }


    public List<SysMenu> getTree(){   //调用的方法入口
        if(bodyList != null && !bodyList.isEmpty()){
            //声明一个map，用来过滤已操作过的数据
            Map<String,String> map = Maps.newHashMapWithExpectedSize(bodyList.size());
            rootList.forEach(beanTree -> getChild(beanTree,map));
            return rootList;
        }
        return null;
    }

    public void getChild(SysMenu beanTree,Map<String,String> map){
        List<SysMenu> childList = Lists.newArrayList();
        bodyList.stream()
                .filter(c -> !map.containsKey(c.getMenuId())&&c.getParentId().equals(beanTree.getMenuId()))//不包含则通过
                /*.filter(c -> c.getParentId().equals(beanTree.getMenuId()))*/
                .forEach(c ->{
                    map.put(c.getMenuId(),c.getParentId());
                    getChild(c,map);
                    childList.add(c);
                });
        beanTree.setChildren(childList);
    }

/*    public static void main(String[] args){
        SysMenu beanTree1 = new SysMenu();
        beanTree1.setCode("540000");
        beanTree1.setLabel("西藏省");
        beanTree1.setPid("100000"); //最高节点
        SysMenu beanTree2 = new SysMenu();
        beanTree2.setCode("540100");
        beanTree2.setLabel("拉萨市");
        beanTree2.setPid("540000");
        SysMenu beanTree3 = new SysMenu();
        beanTree3.setCode("540300");
        beanTree3.setLabel("昌都市");
        beanTree3.setPid("540000");
        SysMenu beanTree4 = new SysMenu();
        beanTree4.setCode("540121");
        beanTree4.setLabel("林周县");
        beanTree4.setPid("540100");
        SysMenu beanTree5 = new SysMenu();
        beanTree5.setCode("540121206");
        beanTree5.setLabel("阿朗乡");
        beanTree5.setPid("540121");
        SysMenu beanTree6 = new SysMenu();
        beanTree6.setCode("540121206001");
        beanTree6.setLabel("堂堂池");
        beanTree6.setPid("540121206");
        List<SysMenu> rootList = new ArrayList<>();
        rootList.add(beanTree1);
        List<SysMenu> bodyList = new ArrayList<>();
        bodyList.add(beanTree1);
        bodyList.add(beanTree2);
        bodyList.add(beanTree3);
        bodyList.add(beanTree4);
        bodyList.add(beanTree5);
        bodyList.add(beanTree6);
        TreeToolUtils utils =  new TreeToolUtils(rootList,bodyList);
        List<SysMenu> result =  utils.getTree();
        String tjson = JSON.toJSONString(result);
        result.get(0);
        System.out.println(tjson);

    }*/
}

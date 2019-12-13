package com.xinba.active.util;


import com.xinba.active.entity.CxActivityGoods;

import java.util.List;

public class ActiveAlgorithm {

    /**
     * 根据Math.random()产生一个double型的随机数，判断每个奖品出现的概率
     * @param
     * @return random：奖品列表ActivityGoods中的序列（ActivityGoods中的第random个就是抽中的奖品）
     */
    public static CxActivityGoods getActivityGoodsIndex(List<CxActivityGoods> activityGoodsList) {
        CxActivityGoods activityGoods=null ;
        try{
            //计算总权重
            double sumWeight = 0;
            for(CxActivityGoods goods : activityGoodsList){
                sumWeight += goods.getGoodsWeight();
            }

            //产生随机数
            double randomNumber;
            randomNumber = Math.random();

            //根据随机数在所有奖品分布的区域并确定所抽奖品
            double d1 = 0;
            double d2 = 0;
            for(int i=0;i<activityGoodsList.size();i++){
                d2 += Double.parseDouble(String.valueOf(activityGoodsList.get(i).getGoodsWeight()))/sumWeight;
                if(i==0){
                    d1 = 0;
                }else{
                    d1 +=Double.parseDouble(String.valueOf(activityGoodsList.get(i-1).getGoodsWeight()))/sumWeight;
                }
                if(randomNumber >= d1 && randomNumber <= d2){
                    activityGoods = activityGoodsList.get(i);
                    break;
                }
            }
        }catch(Exception e){
            System.out.println("生成抽奖随机数出错，出错原因：" +e.getMessage());
        }
        return activityGoods;
    }


//    public static void main(String[] args) {
//        ActivityGoods p1 =new ActivityGoods();
//        ActivityGoods p2 =new ActivityGoods();
//        ActivityGoods p3 =new ActivityGoods();
//        ActivityGoods p4 =new ActivityGoods();
//        ActivityGoods p5 =new ActivityGoods();
//        ActivityGoods p6 =new ActivityGoods();
//        ActivityGoods p7 =new ActivityGoods();
//        ActivityGoods p8 =new ActivityGoods();
//        ActivityGoods p9 =new ActivityGoods();
//        ActivityGoods p10 =new ActivityGoods();
//        p1.setId(1);
//        p2.setId(2);
//        p3.setId(3);
//        p4.setId(4);
//        p5.setId(5);
//        p6.setId(6);
//        p7.setId(7);
//        p8.setId(8);
//        p9.setId(9);
//        p10.setId(10);
//
//        p1.setActivity_amount(1);
//        p2.setActivity_amount(2);
//        p3.setActivity_amount(3);
//        p4.setActivity_amount(4);
//        p5.setActivity_amount(5);
//        p6.setActivity_amount(6);
//        p7.setActivity_amount(7);
//        p8.setActivity_amount(8);
//        p9.setActivity_amount(9);
//        p10.setActivity_amount(10);
//
//        p1.setActivity_name("name_1");
//        p2.setActivity_name("name_2");
//        p3.setActivity_name("name_3");
//        p4.setActivity_name("name_4");
//        p5.setActivity_name("name_5");
//        p6.setActivity_name("name_6");
//        p7.setActivity_name("name_7");
//        p8.setActivity_name("name_8");
//        p9.setActivity_name("name_9");
//        p10.setActivity_name("name_10");
//
//        p1.setActivity_weight(1);
//        p2.setActivity_weight(20);
//        p3.setActivity_weight(30);
//        p4.setActivity_weight(40);
//        p5.setActivity_weight(50);
//        p6.setActivity_weight(60);
//        p7.setActivity_weight(70);
//        p8.setActivity_weight(80);
//        p9.setActivity_weight(90);
//        p10.setActivity_weight(10000);
//
//
//        List<ActivityGoods> p =  new ArrayList<ActivityGoods>();
//        p.add(p1);
//        p.add(p2);
//        p.add(p3);
//        p.add(p4);
//        p.add(p5);
//        p.add(p6);
//        p.add(p7);
//        p.add(p8);
//        p.add(p9);
//        p.add(p10);
//        int pid;
//        int is1 = 0;
//        for(int i = 0; i<=10000; i++){
//            pid = getActivityGoodsIndex(p);
//            System.out.println(pid);
//            if(pid==1){
//                is1+=1;
//            }
//        }
//        System.out.println("is1="+is1);
//
//    }
}

package com.xinba.flow.algorithm;

import com.xinba.flow.entity.OperationFlow;
import java.util.*;

public class Algorithm {
    int N,V;
    //用于存储每个物体的重量，下标从1开始
    private int[] weight;
    //存储每个物体的收益，下标从1开始
    private int[] value;
    //二维数组，用来保存每种状态下的最大收益
    private int[][] F;

    //构造参数
    public void Algorithm(int N,int V,int[] weight,int[] value){
    this.N=N;
    this.V=V;
    this.weight=weight;
    this.value=value;
    }

    /**
     * 使用非递归方式，求解F[0 .. N][0 .. V]，即for循环从下至上求解
     */
    public void ZeroOnePackNonRecursive() {
        //对二维数组F进行初始化
        for(int j = 0; j <= V; j++) {
            F[0][j] = 0;
        }

        //注意边界问题，i是从1开始的，j是从0开始的
        //因为F[i - 1][j]中i要减1
        for(int i = 1; i <= N; i++) {
            for(int j = 0; j <= V; j++) {
                //如果容量为j的背包放得下第i个物体
                if(j >= weight[i]) {
                    F[i][j] = Math.min(F[i - 1][j - weight[i]] + value[i], F[i - 1][j]);
                }else {
                    //放不下，只能选择不放第i个物体
                    F[i][j] = F[i - 1][j];
                }
            }
        }
    }
    /**
     * 求解F[n][m]这个最优值具体选取哪几样物品能获得最大价值，但只会输出一种情况
     * @param n     表示前n个物体，n <= N
     * @param v     表示背包的容量，v <= V
     */
    public boolean[] printResult(int n, int v) {
        boolean[] isAdd = new boolean[n + 1];

        for(int i = n; i >= 1; i--) {
            if(F[i][v] == F[i-1][v])
                isAdd[i] = false;
            else {
                isAdd[i] = true;
                v -= weight[i];
            }
        }
        return isAdd;
    }
//贪心算法选取包
    public static int[] countPackage(List<OperationFlow> list, int total){
        int i;
        int[] item=new int[list.size()];
        Collections.sort(list, new Comparator<OperationFlow>() {
            @Override
            public int compare(OperationFlow o1, OperationFlow o2) {
                if(o1.getStandard()<o2.getStandard()){
                    return 1;
                }
                if(o1.getStandard()==o2.getStandard()){
                    return 0;
                }
                return -1;
            }
        });//list按照stardard属性从大到小排列
            for(i=0;i<list.size();i++){
                if(total>=list.get(i).getStandard()){
                    item[i]=total/list.get(i).getStandard();
                    total=total%list.get(i).getStandard();
                }
            }
            if(total>0){
                item[i-1]++;
            }
        return item;
    }

    //拆分字符串
    public  static int cutStrRatePlan(String str){
        String[]  strArry=str.split("_");
        String  strRatePlan="";
        for(int i=0;i<strArry[2].length();i++){
            if(strArry[2].charAt(i)>='0'&&strArry[2].charAt(i)<='9'){
                strRatePlan+=strArry[2].charAt(i);
            }
            else{
                break;
            }
        }
        int result=Integer.valueOf(strRatePlan);
        return result;
    }
}

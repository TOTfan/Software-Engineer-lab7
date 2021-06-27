package com.cells.util;

public class Config {
  public static int LEFT_INTERVAL = 10;   //与左侧边框的距离
  public static int UP_INTERVAL = 40;     //与上侧边框的距离
  public static int GAMEMAP_LENGTH = 800; //地图的长
  public static int GAMEMAP_WIDTH = 800;  //地图的宽
  public static long DURATION = 500;     //刷新间隔时间
  public static int LENGTH = 100 + 2;     //实际的地图长度为50，多出的 2 仅仅作为判断最外圈状态的条件
  public static int WIDTH = 100 + 2;      //实际的地图宽度为50
  public static int DEAD = 0;             //死亡即0
  public static int ALIVE = 1;            //存活即1
  public static int DEAD_LOW = 1;         //即活细胞周围小于等于1个存活细胞时，死亡
  public static int DEAD_HIGH = 4;        //即活细胞周围大于等于4个存活细胞时，死亡
  public static int ALIVE_BEGIN = 2;      //即活细胞周围2到3个活细胞可以维持存活状态
  public static int ALIVE_END = 3;
  public static int REPRODUCTION = 3;     //即死细胞周围有等于3个活细胞可以被繁殖存活

}

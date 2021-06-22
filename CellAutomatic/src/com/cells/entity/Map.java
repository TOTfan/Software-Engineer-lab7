package com.cells.entity;

import com.cells.util.Config;
import java.util.Random;
import static java.lang.System.exit;

public class Map {
  //gameMap为游戏的地图，可以对地图进行编辑
  //最外侧的元胞不允许判断其存活状态，一律为死亡
  private Cells[][] gameMap;

  public Cells[][] getCells() {
    return gameMap;
  }

  public Map(Cells[][] gameMap) {
    this.gameMap = gameMap;
  }

  public Map() {
    //  L E N G T H
    // * W
    // * I
    // * D
    // * T
    // * H
    // *
    gameMap = new Cells[Config.WIDTH][Config.LENGTH];
    for (int i = 0; i < Config.WIDTH; ++i) {
      for (int j = 0; j < Config.LENGTH; ++j) {
        this.gameMap[i][j] = new Cells(i, j);
      }
    }
  }
  //查询一个元胞的周围有几个存活的元胞
  // row属于(0, WIDTH - 1)开区间
  //col属于(0, LENGTH - 1)开区间

  private void findLivedNum(int row, int col) {
    int nums = 0;
    if (row == 0 || row == Config.WIDTH - 1 || col == 0 || col == Config.LENGTH - 1) {
      exit(0);      //表示最外侧不能进行判断。
    }
    if (gameMap[row][col - 1].getStatus() == Config.ALIVE) {  //左侧
      nums++;
    }
    if (gameMap[row - 1][col - 1].getStatus() == Config.ALIVE) {   //左上侧
      nums++;
    }
    if (gameMap[row + 1][col - 1].getStatus() == Config.ALIVE) {   //左下侧
      nums++;
    }
    if (gameMap[row - 1][col].getStatus() == Config.ALIVE) {   //上侧
      nums++;
    }
    if (gameMap[row + 1][col].getStatus() == Config.ALIVE) {   //下侧
      nums++;
    }
    if (gameMap[row - 1][col + 1].getStatus() == Config.ALIVE) {   //右上侧
      nums++;
    }
    if (gameMap[row][col + 1].getStatus() == Config.ALIVE) {   //右侧
      nums++;
    }
    if (gameMap[row + 1][col + 1].getStatus() == Config.ALIVE) {   //右下侧
      nums++;
    }
    this.gameMap[row][col].setLiveNums(nums);
  }

  //更新细胞的状态
  private void refreshStatus(int row, int col) {
    int alive = gameMap[row][col].getStatus();      //获得当前细胞的生存状态
    int nums = gameMap[row][col].getLiveNums();     //获得周围存活细胞的数量
    if (alive == Config.ALIVE) {      //细胞为存活状态
      if (nums <= Config.DEAD_LOW || nums >= Config.DEAD_HIGH) {     //过于孤独或过度竞争死亡
        gameMap[row][col].setStatus(Config.DEAD);
      } else if (nums >= Config.ALIVE_BEGIN && nums <= Config.ALIVE_END) {  //适宜生存存活
        gameMap[row][col].setStatus(Config.ALIVE);
      }
    } else {       //细胞为死亡状态
      if (nums == Config.REPRODUCTION) {     //死细胞繁殖
        gameMap[row][col].setStatus(Config.ALIVE);
      }
    }
  }

  //更新地图状态
  public void refreshMap() {
    for (int i = 1; i < Config.WIDTH - 1; ++i) {
      for (int j = 1; j < Config.LENGTH - 1; ++j) {
        this.findLivedNum(i, j);
      }
    }
    for (int i = 1; i < Config.WIDTH - 1; ++i) {
      for (int j = 1; j < Config.LENGTH - 1; ++j) {
        this.refreshStatus(i, j);       //更新节点状态
      }
    }
  }

  //随机生成元胞地图进行填充
  //level为随机存活的的概率，即生成0-10的随机数仅在0-level之间存活，故level越高（10），存活概率越高，越低存活概率越低（0）
  public void initMapRandom(int level) {
    Random random = new Random(System.currentTimeMillis());     //指定随机数种子
    for (int i = 1; i < Config.WIDTH - 1; ++i) {
      for (int j = 1; j < Config.LENGTH - 1; ++j) {
        int temp = random.nextInt(10);       //生成0-10的随机数
        if (temp < level) {       //表示存活,将默认值死亡改为存活
          this.gameMap[i][j].setStatus(Config.ALIVE);
        } else {
          this.gameMap[i][j].setStatus(Config.DEAD);
        }
      }
    }
  }

  public String toString() {
    StringBuilder display = new StringBuilder();
    for (int i = 1; i < Config.WIDTH - 1; ++i) {
      for (int j = 1; j < Config.LENGTH - 1; ++j) {
        if (gameMap[i][j].getStatus() == Config.ALIVE) {
          display.append("■");
        } else {
          display.append("□");
        }
      }
      display.append("\n");
    }
    return display.toString();
  }

  //status状态只可以使用config中的配置文件
  //row属于(0, WIDTH - 1)开区间
  //col属于(0, LENGTH - 1)开区间
  public void setGameMap(int row, int col, int status) {
    this.gameMap[row][col].setStatus(status);
  }
}

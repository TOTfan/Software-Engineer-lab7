package com.cells.entity;

import com.cells.util.Config;

public class Cells {
  private int status;
  private int row;    //行数
  private int col;    //列数
  private int liveNums;

  public Cells(int row, int col) {
    status = Config.DEAD;
    liveNums = 0;
    this.row = row;
    this.col = col;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) { //只能设置为DEAD or ALIVE
    if (status != Config.DEAD && status != Config.ALIVE) {
      status = Config.DEAD;
    }
    this.status = status;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    if  (row <= 0 || row > Config.WIDTH - 2) {
      row = 0;
    }
    this.row = row;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int col) {
    if  (col <= 0 || col > Config.WIDTH - 2) {
      col = 0;
    }
    this.col = col;
  }

  public int getLiveNums() {
    return liveNums;
  }

  public void setLiveNums(int liveNums) {
    this.liveNums = liveNums;
  }
}

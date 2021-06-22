package com.cells.ui;

import com.cells.entity.Map;
import com.cells.util.Config;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisplayForm extends JFrame {
  private JLabel levelLable;
  private Map maps;
  private JFrame gameFrame = new JFrame("Game of Life");         //游戏框架
  private JPanel controlPanel = new JPanel();                        //游戏按钮控制界面
  private JButton gameStartButton = new JButton();                   //游戏开始按钮
  private JButton gameStepButton = new JButton();                   //游戏暂停按钮
  private JButton initgameMapButton = new JButton();                   //游戏暂停按钮
  private JComboBox<String> levelcbBox = new JComboBox<>();
  private boolean isStart = false;

  public DisplayForm() {
    this.maps = new Map();
    maps.initMapRandom(levelcbBox.getSelectedIndex());
    gameFrame.setSize(840, 890);        //设置窗体大小为1000*1040
    gameFrame.add(controlPanel);
    gameFrame.add(gamePanel);                       //将游戏显示和控制界面加入窗体
    initControlPanel(controlPanel);
    initGamePanel(gamePanel);                       //初始化游戏界面和控件
    gameFrame.setVisible(true);
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        //设置窗体关闭响应
    gamePanel.setVisible(true);
    controlPanel.setVisible(true);

  }

  //绘制矩形表示细胞
  //方块的长宽
  private final int width = Config.GAMEMAP_LENGTH / (Config.LENGTH - 2);
  private final int length = Config.GAMEMAP_WIDTH / (Config.WIDTH - 2);
  private final int max = Math.min(width, length);

  private class GamePanel extends JPanel {
    public void paint(Graphics graphics) {
      super.paint((graphics));
      //循环从而在1000 * 1000的GamePanel内绘制矩形
      for (int i = 0; i < Config.WIDTH - 2; i++) {
        for (int j = 0; j < Config.LENGTH - 2; j++) {
          //设置矩形内部颜色为白色并填充
          graphics.setColor(Color.WHITE);
          graphics.fillRect(Config.LEFT_INTERVAL + j * max, Config.UP_INTERVAL + i * max, max, max);
          //设置矩形框体颜色为黑色并绘制
          graphics.setColor(Color.BLACK);
          graphics.drawRect(Config.LEFT_INTERVAL + j * max, Config.UP_INTERVAL + i * max, max, max);
        }
      }
      //通过已知细胞分布信息数组绘制对应的细胞，黑色代表活细胞，白色代表死细胞
      for (int i = 0; i < Config.WIDTH - 2; i++) {
        for (int j = 0; j < Config.LENGTH - 2; j++) {
          if (maps.getCells()[i + 1][j + 1].getStatus() == Config.ALIVE) {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(Config.LEFT_INTERVAL + j * max,
                    Config.UP_INTERVAL + i * max, max, max);
          }
        }
      }
    }
  }

  private GamePanel gamePanel = new GamePanel();

  //初始化游戏界面
  private void initGamePanel(JPanel gamePanel) {
    gamePanel.setBounds(0, Config.UP_INTERVAL,
        Config.GAMEMAP_LENGTH, Config.GAMEMAP_WIDTH);             //初始化游戏界面窗体及大小
    gamePanel.setLayout(null);                //设置布局为null
    gamePanel.addMouseListener(new MouseListener() {
        @Override//左上 10，40 右下810，840
            public void mouseClicked(MouseEvent e) {
            Point cur = e.getPoint();   //获得当前的指向的点
            //System.out.println(cur.x + ", " +cur.y);
            if (cur.x >= Config.LEFT_INTERVAL && cur.x <= Config.LEFT_INTERVAL + max * Config.LENGTH
                        && cur.y >= Config.UP_INTERVAL
                    && cur.y <= Config.UP_INTERVAL + max * Config.WIDTH) {
              int row;
              int col;       //点击的行与列
              col = (cur.x - Config.LEFT_INTERVAL) / max + 1;
              row = (cur.y - Config.UP_INTERVAL) / max + 1;
              //System.out.println(row + ", " +col);
              int status = maps.getCells()[row][col].getStatus()
                      == Config.ALIVE ? Config.DEAD : Config.ALIVE;
              maps.setGameMap(row, col, status);
              gamePanel.repaint();
              controlPanel.repaint();
            }
        }

        @Override
            public void mousePressed(MouseEvent e) {
        }

        @Override
            public void mouseReleased(MouseEvent e) {
        }

        @Override
            public void mouseEntered(MouseEvent e) {
        }

        @Override
            public void mouseExited(MouseEvent e) {
        }
        });
  }

  //初始化控制界面
  private void initControlPanel(JPanel controlPanel) {
    controlPanel.setLayout(new FlowLayout());                          //设置控制界面布局为FlowLayout
    controlPanel.setBounds(0, 0, Config.GAMEMAP_LENGTH + Config.UP_INTERVAL,
            Config.UP_INTERVAL);            //设置控制界面位置及大小
    //添加按钮控件
    levelLable = new JLabel();
    levelLable.setText("存活率：");
    String[] strArray =
          { "0%", "10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%" };
    for (String item : strArray) {
      levelcbBox.addItem(item);
    }
    initgameMapButton.setText("创建地图");
    gameStartButton.setText("开始游戏");
    gameStepButton.setText("单步运行");
    controlPanel.add(initgameMapButton);
    controlPanel.add(levelLable);
    controlPanel.add(levelcbBox);
    controlPanel.add(gameStartButton);
    controlPanel.add(gameStepButton);
    initgameMapButton.addActionListener(new InitListener());
    gameStepButton.addActionListener(new StepListener());
    gameStartButton.addActionListener(new StartListener());
  }

  private class StepListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      maps.refreshMap();
      gamePanel.repaint();
      controlPanel.repaint();
    }
  }

  private class InitListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      maps.initMapRandom(levelcbBox.getSelectedIndex());
      gamePanel.repaint();
      controlPanel.repaint();
    }
  }

  private class StartThread extends Thread {
    private Thread time;

    StartThread() {
      System.out.println("开始游戏线程被成功创建！");
    }

    public void run() {
      while (isStart) {
        try {
          maps.refreshMap();
          gamePanel.repaint();
          controlPanel.repaint();
          Thread.sleep(Config.DURATION);
        } catch (InterruptedException e) {
          e.printStackTrace();
          return;
        }
      }
    }

    public void start() {
      if (time == null) {
        time = new Thread(this);
        time.start();
        System.out.println("开始游戏！");
      }
    }
  }

  private StartThread tempThread;

  private class StartListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      isStart = !isStart;
      if (isStart) {        //游戏开始
        tempThread = new StartThread();
        gameStartButton.setText("暂停游戏");
        gameStepButton.setEnabled(false);
        initgameMapButton.setEnabled(false);
        tempThread.start();
      } else {
        gameStartButton.setText("开始游戏");
        gameStepButton.setEnabled(true);
        initgameMapButton.setEnabled(true);
        tempThread.interrupt();
      }
    }
  }
}
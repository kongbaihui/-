import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;


public class GameJFrame extends JFrame implements MouseListener {

    //棋盘位置、宽高
    int qx = 20, qy = 80, qw = 490, qh = 490;

    //按钮宽高、位置
    int bw = 150, bh = 50, bx = 570, by = 150;

    //保存棋子坐标
    int x = 0, y = 0;

    //保存每个棋子的位置
    int[][] SaveGame = new int[15][15];

    //记录白棋=2，黑棋=1
    int chess = 1;

    //判断棋子是否重复
    int repeat = 0;

    //判断游戏进行的步数
    int step=0;

    //判断游戏是否开始和结束
    boolean canplay = true;
    //游戏信息
    String go = "黑子先行";

    ChessUI chessui = new ChessUI();

    String result=null;


    /*
    构造函数
    */
    public GameJFrame() {

        //初始化界面
        initJFrame();

        //初始化菜单
        initJMenuBar();

        // 初始化棋子位置数组
        // 假设最大步数为225
        chessui.ps = new Position[225];
        for (int i = 0; i < chessui.ps.length; i++) {
            chessui.ps[i] = new Position(0, 0);
        }

        this.setLayout(null);

        this.pack();

        //然界面显示出来
        this.setVisible(true);


    }

    /*
    初始化菜单栏
    */
    private void initJMenuBar() {

        //设置菜单栏
        JMenuBar menuBar = new JMenuBar();

        //设置菜单栏里的选项
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于");

        //功能中的选项
        JMenuItem closeItem = new JMenuItem("退出游戏");
        JMenuItem aboutItem =new JMenuItem("关于");

        //退出游戏的选项
        closeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //关于的选项
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutJFrame();
                /* System.out.println("xxx"); */
            }
        });

        // 读取游戏结果的选项
        JMenuItem readResultsItem = new JMenuItem("读取游戏结果");
        readResultsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readGameResults();
            }
        });

        //将功能中的选项条目添加进功能选项中
        functionJMenu.add(closeItem);
        functionJMenu.add(readResultsItem);
        aboutJMenu.add(aboutItem);

        //将选项添加进菜单栏中
        menuBar.add(functionJMenu);
        menuBar.add(aboutJMenu);



        //给整个界面设置菜单
        this.setJMenuBar(menuBar);

    }

    //读取游戏结果
    private void readGameResults() {
        StringBuilder results = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("game_results.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                results.append(line).append("\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            results.append("读取游戏结果时发生错误！");
        }

        // 显示结果
        JOptionPane.showMessageDialog(this, results.toString(), "游戏结果", JOptionPane.INFORMATION_MESSAGE);
    }

    //初始化JFrame
    private void initJFrame() {

        //设置界面大小
        this.setPreferredSize(new Dimension(800, 600));

        //窗口不可以改变大小
        this.setResizable(false);

        //设这界面标题
        this.setTitle("五子棋单机版 v1.0");

        //将界面置顶
        //让界面居中
        this.setLocationRelativeTo(null);

        //设置界面关闭
        this.setDefaultCloseOperation(3);

        //获取屏幕宽度
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;

        //获取屏幕高度
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        //设置窗口默认位置以屏幕居中
        this.setLocation((width - 800) / 2, (height - 600) / 2);

        this.addMouseListener(this);


    }

    /*
    初始化棋盘
     */
    public void Initialize() {

        //遍历并初始化数组
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                SaveGame[i][j] = 0;
            }
        }

        //黑子先行
        chess = 1;
        go = "轮到黑子";
    }


    @Override
    public void paint(Graphics g) {
    super.paint(g);

    //双缓冲技术防止屏幕闪烁
    BufferedImage bi = new BufferedImage(800, 550, BufferedImage.TYPE_INT_ARGB);
    Graphics g2 = bi.createGraphics();

    //获取图片路径
    BufferedImage image = null;
    try {
        image = ImageIO.read(new File("D:\\java study\\FinalExam\\6233.png_860.png"));
    } catch (IOException e) {
        e.printStackTrace();
    }

    //显示图片
    g2.drawImage(image, 60, 60, this);

    //设置画笔颜色
    g2.setColor(Color.BLACK);

    //设置字体
    g2.setFont(new Font("华文行楷", 10, 50));

    //绘制字符
    g2.drawString("五子棋", 565, 120);

    //棋盘
    // 设置画笔颜色
    g2.setColor(Color.getHSBColor(30, (float) 0.90, (float) 0.90));

    //绘制棋盘背景矩形
    g2.fillRect(qx, qy, qw, qh);

    //开始按钮
    // 设置画笔颜色
    g2.setColor(Color.WHITE);

    //绘制开始按钮
    g2.fillRect(bx, by, bw, bh);

    //设置字体
    g2.setFont(new Font("华文行楷", 10, 30));

    //设置画笔颜色
    g2.setColor(Color.black);

    //绘制字符
    g2.drawString("重新开始", 585, 185);

    //悔棋按钮
    // 设置画笔颜色
    g2.setColor(Color.LIGHT_GRAY);

    //绘制悔棋按钮
    g2.fillRect(bx, by + 60, bw, bh);

    //设置字体
    g2.setFont(new Font("华文行楷", 10, 30));

    //设置画笔颜色
    g2.setColor(Color.WHITE);

    //绘制字符
    g2.drawString("悔棋", 615, 245);

    //认输按钮
    // 设置画笔颜色
    g2.setColor(Color.GRAY);

    //绘制认输按钮
    g2.fillRect(bx, by + 120, bw, bh);

    //设置字体
    g2.setFont(new Font("华文行楷", 10, 30));

    //设置画笔颜色
    g2.setColor(Color.WHITE);

    //绘制字符
    g2.drawString("认输", 615, 305);

    //游戏信息栏
    // 设置画笔颜色
    g2.setColor(Color.getHSBColor(30, (float) 0.10, (float) 0.90));

    //绘制游戏状态区域
    g2.fillRect(530, 350, 260, 150);

    //设置画笔颜色
    g2.setColor(Color.black);

    //设置字体
    g2.setFont(new Font("黑体", 10, 20));

    //绘制字符
    g2.drawString("-----信息-----", 580, 380);

    //绘制字符
    g2.drawString(go, 610, 410);

    //绘制字符
    g2.drawString("作者姓名：xxx", 540, 440);

    //绘制字符
    g2.drawString("学号：xxx", 540, 465);

    // 绘制字符
    g2.drawString("班级：xxx", 540, 490);


    //设置画笔颜色
    g2.setColor(Color.BLACK);

    //绘制棋盘格线
    for (int x = 0; x <= qw; x += 35) {

        //绘制一条横线
        g2.drawLine(qx, x + qy, qw + qx, x + qy);

        //绘制一条竖线
        g2.drawLine(x + qx, qy, x + qx, qh + qy);
    }

    //绘制标注点
    for (int i = 3; i <= 11; i += 4) {
        for (int y = 3; y <= 11; y += 4) {

            //绘制实心圆
            g2.fillOval(35 * i + qx - 3, 35 * y + qy - 3, 6, 6);
        }
    }


    //绘制棋子
    for (int i = 0; i < 15; i++) {
        for (int j = 0; j < 15; j++) {

            //黑子
            if (SaveGame[i][j] == 1)
            {
                int sx = i * 35 + qx;
                int sy = j * 35 + qy;
                g2.setColor(Color.BLACK);

                //绘制实心圆
                g2.fillOval(sx - 13, sy - 13, 26, 26);
            }

            //白子
            if (SaveGame[i][j] == 2)
            {
                int sx = i * 35 + qx;
                int sy = j * 35 + qy;

                //绘制实心圆
                g2.setColor(Color.WHITE);
                g2.fillOval(sx - 13, sy - 13, 26, 26);

                //绘制空心圆
                g2.setColor(Color.BLACK);
                g2.drawOval(sx - 13, sy - 13, 26, 26);
            }
        }
    }
    g.drawImage(bi, 0, 0, this);
    }



    //---------------------------------------------------------------------------------------------------------------------
    @Override
    //鼠标点击
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    //鼠标按下
    public void mousePressed(MouseEvent e) {

        //获取鼠标点击位置
        x = e.getX();
        y = e.getY();



        //判断是否已开始游戏
        if (canplay == true) {

            //判断点击是否为棋盘内
            if (x > qx && x < qx + qw && y > qy && y < qy + qh) {

                //计算点击位置最近的点
                if ((x - qx) % 35 > 17) {
                    x = (x - qx) / 35 + 1;
                } else {
                    x = (x - qx) / 35;
                }
                if ((y - qy) % 35 > 17) {
                    y = (y - qy) / 35 + 1;
                } else {
                    y = (y - qy) / 35;
                }

                //判断当前位置有没有棋子
                if (SaveGame[x][y] == 0) {
                    SaveGame[x][y] = chess;
                    repeat = 0;
                } else {
                    repeat = 1;
                }

                //切换棋子
                if (repeat == 0) {
                    if (chess == 1) {
                        chess = 2;
                        go = "轮到白子";
                    } else {
                        chess = 1;
                        go = "轮到黑子";
                    }
                }

                //重新执行一次paint方法
                this.repaint();

                //弹出胜利对话框
                boolean wl = Chess.WinLose(SaveGame,x,y);
                if (wl) {
                    String result = "游戏结束，" + (SaveGame[x][y] == 1 ? "黑方赢了" : "白方赢了");

                    // 弹出提示对话框
                    JOptionPane.showMessageDialog(this, result);
                    canplay = false;

                    // 获取当前时间
                    Date date = new Date();

                    // 将结果写入文件
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("game_results.txt", true))) {
                        writer.write(date.toString() + " - " + result + System.lineSeparator());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                Position p = new Position( x, y);
                chessui.ps[step].listx=x;
                chessui.ps[step].listy=y;
                step++;


            } else {

            }
        }


        //实现开始按钮
        //判断是否点击开始按钮
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by && e.getY() < by + bh) {

            //判断游戏是否开始
            if (canplay == false) {

                //如果游戏结束，则开始游戏
                canplay = true;
                JOptionPane.showMessageDialog(this, "游戏开始");

                //初始化游戏
                Initialize();

                //重新执行一次paint方法
                this.repaint();

            } else {

                //如果游戏进行中，则重新开始
                JOptionPane.showMessageDialog(this, "重新开始");

                //初始化游戏
                Initialize();

                //重新执行一次paint方法
                this.repaint();

            }
        }


        //实现悔棋按钮
        //判断是否点击悔棋按钮
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by + 60 && e.getY() < by + 60 + bh) {

            //判断游戏是否开始
            if (canplay) {

                //遍历棋盘上是否有棋子
                int z = 0;
                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < 15; j++) {
                        if (SaveGame[i][j] != 0) {
                            z++;
                        }
                    }
                }

                //判断是否有棋子
                if (z != 0) {
                    int result = JOptionPane.showConfirmDialog(this, "确认要悔棋吗？");
                    if (result == 0) {
                        int x = chessui.ps[step - 1].listx;
                        int y = chessui.ps[step - 1].listy;

                        if (SaveGame[x][y] == 0) {
                            JOptionPane.showMessageDialog(this, "已悔过一次棋了！");
                        } else {
                            if (SaveGame[x][y] == 1) {
                                chess = 1;
                                go = "轮到黑子";
                            } else if (SaveGame[x][y] == 2) {
                                chess = 2;
                                go = "轮到白子";
                            }
                            SaveGame[x][y] = 0;
                            step--;
                            this.repaint();
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(this, "请先开始游戏");
                }
            }
        }


            //实现认输按钮
            //判断是否点击认输按钮
            if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by + 120 && e.getY() < by + 120 + bh) {

                //判断游戏是否开始
                if (canplay == true) {

                    Date date = new Date();

                    //判断是谁认输
                    if (chess == 1) {
                        JOptionPane.showMessageDialog(this, "黑方认输，白方获胜");
                        result="黑方输，白方胜";
                        canplay = false;
                    } else if (chess == 2) {
                        JOptionPane.showMessageDialog(this, "白方认输，黑方获胜");
                        result="白方输，黑方胜";
                        canplay = false;
                    }else {
                        result = "未知结果";
                    }

                    //判断是否存储异常
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("game_results.txt", true))) {
                        writer.write(date.toString() + " - " + result + System.lineSeparator());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "请先开始游戏");
                }
            }

    }

        @Override
        //鼠标抬起
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        //鼠标进入
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        //鼠标离开
        public void mouseExited(MouseEvent e) {

        }

}

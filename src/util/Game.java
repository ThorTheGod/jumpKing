package util;
import map.GameObject;
import map.Map;
import map.Obstacle;
import map.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//程序入口，启动窗口，游戏初始化
public class Game extends JFrame implements KeyListener, Runnable {
    //背景集合
    private List<Map> allMap = new ArrayList<Map>();
    private Map nowMap = null;
    private Role jumpKing = null;
    private boolean isStart = false;
    //线程：人物移动刷新地图
    private Thread t1 = new Thread(this);
    private long pressed_currentTime;
    private long released_currentTime;

    //构造函数，设置窗口参数
    public Game(){
        setTitle(ConstValue.gameTitle);
        setSize(ConstValue.gameWidth,ConstValue.gameHeight);
        //将窗口显示在屏幕中央
        setLocationRelativeTo(null);

        //设置关闭窗口时便退出程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //将所有图片添加入对应集合中
        GameImage.init();
        //将背景图片放入本类的背景图集合中
        for (int i = 1;i<=GameImage.bgImage.size();i++){
            this.allMap.add(new Map(i-1,i==2));
        }
        //启动游戏时当前地图为第一个背景
        nowMap = this.allMap.get(0);
        //初始化jumpKing
        this.jumpKing = new Role(nowMap);

        System.out.println("jumpKing born!");

        //重绘
        this.repaint();
        //绑定键盘监听
        this.addKeyListener(this);
        //开始线程
        t1.start();

        //窗口默认不可见，故设置窗口可视
        setVisible(true);

    }

    /**
     * 重写JFrame的paint方法
     * @param g
     */
    @Override
    public void paint(Graphics g){
        //在内存中将图片绘制在image的g2，完成后复制到g上，加快绘制时间
        BufferedImage image = new BufferedImage(900,600,BufferedImage.TYPE_3BYTE_BGR);
        Graphics g2 = image.getGraphics();
        if(this.isStart){
            //绘制背景
            g2.drawImage(this.nowMap.getBGImage(),0,0,this);
            //添加障碍物
            Iterator<Obstacle> iterator = this.nowMap.getAllObstacle().iterator();
            while (iterator.hasNext()) {
                Obstacle obstacle = iterator.next();
                g2.drawImage(obstacle.getShowImage(), obstacle.getX(), obstacle.getY(), this);
            }
            //添加jumpKing
            g2.drawImage(this.jumpKing.getShowImage(),this.jumpKing.getX(),this.jumpKing.getY(),this);
        }else{
            //若初次绘制则绘制第一张背景
            g2.drawImage(GameImage.firstImage,0,0,this);
            this.isStart = true;
        }
        //复制到g上
        g.drawImage(image,0,0,this);
    }

    //刷新画面
    @Override
    public void run() {
        while(true){
            //刷新地图
            this.repaint();
            try{
                Thread.sleep(1000/ConstValue.fps);
                //当jumpKing不在最后一个地图时正常执行换地图判断
                if(!this.jumpKing.isWin()){
                    //切换下一张地图
                    System.out.println("sort:"+this.nowMap.getSort()+"\tmaxSort:"+GameImage.bgImage.size());
                     if(this.jumpKing.getY()<0){
                        this.jumpKing.changePositionInMap(1);//向上飞
                        this.nowMap = this.allMap.get(this.nowMap.getSort()+1);
                    }else if(this.jumpKing.getY()>600){//切换上一张地图
                        this.jumpKing.changePositionInMap(-1);//向下掉
                        this.nowMap = this.allMap.get(this.nowMap.getSort()-1);
                    }
                }else{  //当jumpKing所在地图为最后一个地图时
                    System.out.println("last map!");
                    JOptionPane.showMessageDialog(null,"JumpKing!!!","jumppp King",JOptionPane.YES_NO_OPTION);
                    this.exitJumpKing();
                }
                this.jumpKing.setMap(this.nowMap);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.println("ketTyped:"+e.getKeyCode());

    }

    //左：37 右：39 空格（跳跃）：32
    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyCode());
        if(this.isStart){
            if(e.getKeyCode()==32){
                if(this.pressed_currentTime==0&&this.jumpKing.isOnLand()){
                    this.pressed_currentTime = System.currentTimeMillis();
                    System.out.println("pressed:"+pressed_currentTime);
                }
            }
        }
    }
    //左：37 右：39 空格（跳跃）：32
    @Override
    public void keyReleased(KeyEvent e) {
        if(this.isStart){
            if(this.jumpKing.isOnLand()){//人物站立时才能调整左右或蓄力
                if(e.getKeyCode()==32){ //空格蓄力结束
                    this.released_currentTime = System.currentTimeMillis();
                    if(this.released_currentTime-this.pressed_currentTime>350){
                        this.jumpKing.setTypeSpace(true);
                        System.out.println("released:"+released_currentTime);
                        this.jumpKing.jump(pressed_currentTime,released_currentTime);
                    }
                }else if(e.getKeyCode()==37){ //
                    this.jumpKing.setShowImage(GameImage.allJumpKingImage.get(0));
                    this.jumpKing.setStatus("left--standing");
                }else if(e.getKeyCode()==39){
                    this.jumpKing.setShowImage(GameImage.allJumpKingImage.get(1));
                    this.jumpKing.setStatus("right--standing");
                }
            }
            this.pressed_currentTime = 0;
            this.released_currentTime = 0;
        }
        }


    /**
     * 游戏退出
     */
    public void exitJumpKing(){
        System.exit(1);
    }

    public static void main(String[] args) {
        Game gameFrame = new Game();
    }

}

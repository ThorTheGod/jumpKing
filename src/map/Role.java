package map;

import util.ConstValue;
import util.Game;
import util.GameImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//jumpKing
public class Role extends GameObject implements Runnable{
    private BufferedImage showImage;
    private String status;//人物状态
    private Thread t1;
    private Map map;//jumpKing当前所在场景

    private boolean isWin = false;//游戏完成标记
    private boolean canLeft = true; //人物状态
    private boolean canRight = true;
    private boolean onLand = false;
    private boolean typeSpace = false;

    private long storageTime;   //蓄力时间
    private int upN = 0;    //跳跃次数
    private int beforeJumpx;
    private int beforeJumpy;

    private int xTime;   //x,y方向跳跃总历时
    private int yTime; //
    double startVx = 0d;    //起跳的x，y初速度
    double startVy = 0d;

    private int beforeJumpMapSort = 0; //跳跃前所在的地图编号
    private int jumpingMapSort = 0;

    //生成角色图，设置初始位置，地图，启动线程
    public Role(Map map){
        this.status = "left--standing";
        this.setShowImage(GameImage.allJumpKingImage.get(0));
        this.setPosition(140,90);
        //this.setPosition(150,120);
        beforeJumpx = this.x;
        beforeJumpy = this.y;
        this.setMap(map);
        t1 = new Thread(this);
        t1.start();
    }
    //根据蓄力时间确定起跳速度
    public void jump(long pcTime,long rcTime){
        upN++;
        this.storageTime = rcTime - pcTime;
        if(storageTime<=450) storageTime = 450;
        else if(storageTime>=2000) storageTime = 2000;
        System.out.println("totalTime："+storageTime);
        if(status.contains("left")){
            startVx =(-1)*0.006*storageTime;
            //vx = startVx;
        }else{
            startVx =0.006*storageTime;
            //vx = startVx;
        }
        startVy =(-1)*0.009*storageTime;
        //vy = startVy;
        System.out.println("startVx:"+startVx);
    }
    //弹回
    public void reBound(){
        if(status.contains("left--jumping")){
            canLeft = false;
            canRight = true;
            System.out.println("left --》 right!");
            startVx = (-1+ConstValue.loseVFactor)*startVx;
            xTime = 0;
            setStatus("right--jumping");
            beforeJumpx = this.x;
            setShowImage(GameImage.allJumpKingImage.get(1));
        }else if(status.contains("right--jumping")){
            canRight = false;
            canLeft = true;
            System.out.println("right --》 left!");
            startVx = (-1+ConstValue.loseVFactor)*startVx;
            xTime = 0;
            setStatus("left--jumping");
            beforeJumpx = this.x;
            setShowImage(GameImage.allJumpKingImage.get(0));
        }

    }


    public void changePositionInMap(int yDirection){
        if(yDirection == 1){
            jumpingMapSort++;
            System.out.println("跳到了更高一层！");
        }else if(yDirection == -1){
            jumpingMapSort--;
            System.out.println("跳到了更低一层！");
        }
    }

    @Override
    public void run() {
        while(true) {
            if (!this.isWin()) {
                //障碍物碰撞检测
                if(x <= 900 && x >= 0 && y<600 && y>0){
                for (int i = 0; i < this.map.getAllObstacle().size(); i++) {
                    Obstacle obstacle = this.map.getAllObstacle().get(i);
                    //跳跃顶到了障碍物
                    if ((obstacle.y + obstacle.getHeight() + 7 >= this.y && obstacle.y + obstacle.getHeight() - 7 <= this.y ) && ((obstacle.x + obstacle.getWidth()) >= this.x && (obstacle.x - ConstValue.jumpKingWidth) <= this.x)) {
                        startVy = (-0.5+ConstValue.loseVFactor)* startVy;
                        yTime = 0;
                        //System.out.println("向上移动判定-边界y范围："+(obstacle.y + ConstValue.obstacleHeight[i] - 5 )+"~"+(obstacle.y + ConstValue.obstacleHeight[i] + 5));
                        System.out.println("你的y位置："+this.y);
                        System.out.println("原移动位置:"+(this.y+startVy));
                        //如果反弹向下后的下一刻仍没有逃出向上反弹判定，则将其位置修改至判定下边界
                        if (this.y+startVy<=obstacle.y + obstacle.getHeight() + 7){
                            beforeJumpy = obstacle.y + obstacle.getHeight() + 8;
                            System.out.println("修改原移动位置");
                        }
                        else beforeJumpy = this.y;
                        beforeJumpMapSort = map.getSort();
                        jumpingMapSort = beforeJumpMapSort;
                        break;
                    }
                    //向右移动判断,碰撞则向左弹回
                    if ((obstacle.x-8 - ConstValue.jumpKingWidth<= this.x  && obstacle.x+8 - ConstValue.jumpKingWidth >= this.x) && ( obstacle.y + obstacle.getHeight() >= this.y && obstacle.y  <= this.y+ConstValue.jumpKingHeight)) {
                        reBound();
                        System.out.println("向右移动判定-边界x范围："+(obstacle.x-7 - ConstValue.jumpKingWidth)+"~"+(obstacle.x+7 - ConstValue.jumpKingWidth));
                        System.out.println("你的x位置："+this.x);
                        System.out.println("原移动位置:"+(this.x+startVx));
                        //如果反弹向左后的下一刻仍没有逃出向左反弹判定，则将其位置修改至判定左边界
                        if(this.x+ConstValue.jumpKingWidth+startVx>=obstacle.x -8){
                            beforeJumpx = obstacle.x -9- ConstValue.jumpKingWidth;
                        }
                        break;
                    }
                    //向左移动判断，碰撞则向右弹回
                    if ((obstacle.x + obstacle.getWidth()+8 >= this.x && obstacle.x + obstacle.getWidth() - 8 <= this.x ) && (obstacle.y + obstacle.getHeight() >= this.y && obstacle.y  <= this.y+ConstValue.jumpKingHeight)) {
                        reBound();
                        //System.out.println("向左移动判定-边界x范围："+(obstacle.x + ConstValue.obstacleWidth[i] - 7)+"~"+(obstacle.x + ConstValue.obstacleWidth[i]+5));
                        System.out.println("你的位置："+this.x);
                        System.out.println("原移动位置:"+(this.x+startVx));
                        //如果在反弹向右后的下一刻仍没有逃出向右反弹判定，则将其位置修改至判定右边界
                        if(this.x+startVx<=obstacle.x + obstacle.getWidth()+8){
                            beforeJumpx = obstacle.x + obstacle.getWidth()+9;
                        }
                        break;
                    }
                    //判断是否处于障碍物上，是则可以跳跃;否则进行运动
                    if (obstacle.y+10 - ConstValue.jumpKingHeight>= this.y  &&obstacle.y - ConstValue.jumpKingHeight-6<= this.y  && obstacle.getX() + obstacle.getWidth() >= this.x && obstacle.x - ConstValue.jumpKingWidth <= this.x) {
                        setOnLand(true);
                        if(xTime!=0 || yTime !=0){
                            typeSpace = false;
                        }
                        canLeft = true;
                        canRight = true;
                        System.out.println("stand!");
                        //设置站立
                        if(this.status.contains("left--jumping")){
                            setStatus("left--standing");
                            System.out.println("set left--standing!");
                        }else if(this.status.contains("right-jumping")){
                            setStatus("right-standing");
                            System.out.println("set right--standing!");
                        }
                    }
                }
                }else{  //处于不正常位置
                    System.out.println("跳过本次障碍物检测，以先执行位置更新");
                }

                if(typeSpace){  //跳跃状态
                    xTime++;
                    yTime++;
                    //修改为跳跃状态
                    if(this.status.contains("left--standing"))
                        setStatus("left--jumping");
                    else if(this.status.contains("right--standing"))
                        setStatus("right--jumping");

                    //修改x
                    if((status.contains("left--jumping")&&canLeft)||(status.contains("right--jumping")&&canRight)){
                        setX((int)(beforeJumpx+xTime*startVx));
                    }
                    //修改y
                    setY((int)(beforeJumpy+ConstValue.g*yTime*yTime/2+startVy*yTime+600*(jumpingMapSort-beforeJumpMapSort)));
                }else{ //落地
                    beforeJumpx = this.x;
                    beforeJumpy = this.y;
                    xTime = 0;
                    yTime = 0;
                    beforeJumpMapSort = map.getSort();
                    jumpingMapSort = beforeJumpMapSort;
                }

                if(xTime != 0 || yTime != 0)    //保持非着陆状态
                    setOnLand(false);

                System.out.println("beforeSort："+beforeJumpMapSort+"\tjumpingSort:"+jumpingMapSort);
                System.out.println("x:"+this.x+"\ty:"+this.y);

                this.isWin();

                try{
                    Thread.sleep(1000/ConstValue.fps);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }else{
                //获胜
                System.out.println("恭喜你成功了！");
                break;
            }
        }
    }

    public boolean isWin(){
        if(this.map.getSort()==3&&this.getX()+20<=235&&this.getX()>=185&&this.getY()<=90&&this.getY()>=25){
            this.isWin = true;
        }
        return this.isWin;
    }

    public Image getShowImage() {
        return showImage;
    }

    public void setShowImage(BufferedImage showImage) {
        this.showImage = showImage;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isOnLand() {
        return onLand;
    }

    public void setOnLand(boolean onLand) {
        this.onLand = onLand;
    }

    public int getUpN() {
        return upN;
    }

    public void setUpN(int upN) {
        this.upN = upN;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isTypeSpace() {
        return typeSpace;
    }

    public void setTypeSpace(boolean typeSpace) {
        this.typeSpace = typeSpace;
    }
}

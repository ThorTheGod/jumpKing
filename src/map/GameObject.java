package map;

import java.awt.*;

//游戏物体父类
public class GameObject {
    protected int x;
    protected int y;

//    public void draw(Graphics g){
//    }

    //设置坐标
    public void setPosition(int x,int y){
        this.x = x;
        this.y = y;
    }

    //偏移坐标
    public void transfer(int x,int y){
        this.x+=x;
        this.y+=y;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

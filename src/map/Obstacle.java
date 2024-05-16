package map;

import util.ConstValue;
import util.GameImage;

import java.awt.*;
import java.awt.image.BufferedImage;

//障碍物(台阶
public class Obstacle extends GameObject{
    private Map map;
    private int type;
    private int width;
    private int height;
    private BufferedImage showImage;

    public Obstacle(int x,int y,int type,Map map){
        this.x = x;
        this.y = y;
        this.map = map;
        this.type = type;
        this.width = ConstValue.obstacleWidth[type];
        this.height = ConstValue.obstacleHeight[type];
        this.showImage = GameImage.allObstacle.get(type);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getShowImage() {
        return showImage;
    }

    public void setShowImage(BufferedImage showImage) {
        this.showImage = showImage;
    }
}

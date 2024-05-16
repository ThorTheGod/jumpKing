package map;

import util.ConstValue;
import util.GameImage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

//地图背景类
public class Map {
    //当前显示背景
    private BufferedImage bgImage;
    //场景顺序
    private int sort;//0~3
    //最后地图标记
    private boolean isFinalBG;
    //本地图中全部障碍物
    private List<Obstacle> allObstacle = new ArrayList<Obstacle>();


    public Map(int sort, boolean isFinalBG){
        this.sort = sort;
        this.isFinalBG = isFinalBG;
        if(isFinalBG){
            this.bgImage = GameImage.bgImage.get(sort);
        }else{
            this.bgImage = GameImage.bgImage.get(sort);
        }
        //加载每张地图的障碍物
        switch (sort) {
            case 0: {
                this.allObstacle.add(new Obstacle(0, 550, 1, this)); //底
                this.allObstacle.add(new Obstacle(120, 120, 0, this));
                this.allObstacle.add(new Obstacle(300,400,0,this));
                this.allObstacle.add(new Obstacle(420,400,0,this));
                this.allObstacle.add(new Obstacle(650,250,0,this));
                this.allObstacle.add(new Obstacle(730,500,0,this));

                this.allObstacle.add(new Obstacle(0, 0, 2, this));//两边墙
                this.allObstacle.add(new Obstacle(850, 0, 2, this));
                break;
            }
            case 1:{
                this.allObstacle.add(new Obstacle(120, 200, 0, this));
                this.allObstacle.add(new Obstacle(240, 200, 0, this));
                this.allObstacle.add(new Obstacle(130, 480, 1, this));
                this.allObstacle.add(new Obstacle(350, 350, 0, this));
                this.allObstacle.add(new Obstacle(470, 315, 0, this));
                this.allObstacle.add(new Obstacle(470, 350, 0, this));

                this.allObstacle.add(new Obstacle(0, 0, 2, this));
                this.allObstacle.add(new Obstacle(850, 0, 2, this));
                break;
            }
            case 2:{
                this.allObstacle.add(new Obstacle(200, 200, 0, this));
                this.allObstacle.add(new Obstacle(-600, 480, 1, this));
                this.allObstacle.add(new Obstacle(600, 480, 1, this));
                this.allObstacle.add(new Obstacle(390, 380, 0, this));


                this.allObstacle.add(new Obstacle(0, 0, 2, this));
                this.allObstacle.add(new Obstacle(850, 0, 2, this));
                break;
            }
            case 3:{
                this.allObstacle.add(new Obstacle(150, 150, 0, this));
                this.allObstacle.add(new Obstacle(-450, 500, 1, this));
                this.allObstacle.add(new Obstacle(600, 400, 0, this));

                this.allObstacle.add(new Obstacle(0, 0, 2, this));
                this.allObstacle.add(new Obstacle(850, 0, 2, this));
                this.allObstacle.add(new Obstacle(0, 15, 1, this));
                break;
            }
        }
    }

    public BufferedImage getBGImage() {
        return bgImage;
    }

    public void setBGImage(BufferedImage bgImage) {
        this.bgImage = bgImage;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<Obstacle> getAllObstacle() {
        return allObstacle;
    }

    public void setAllObstacle(List<Obstacle> allObstacle) {
        this.allObstacle = allObstacle;
    }
}

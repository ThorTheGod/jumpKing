package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameImage {
    //背景图
    public static List<BufferedImage> bgImage = new ArrayList<BufferedImage>();
    public static BufferedImage firstImage;
    //障碍物
    public static List<BufferedImage> allObstacle = new ArrayList<BufferedImage>();
    //jumpKing状态图
    public static List<BufferedImage> allJumpKingImage = new ArrayList<BufferedImage>();

    public static void init(){
        //按序循环导入所有jumpKing图片
        for (int i = 1; i <= ConstValue.jumpKingImageTotal; i++) {
            try {
                allJumpKingImage.add(ImageIO.read(new File("D:\\IDEA\\IdeaProject\\jumpKing\\src\\util\\images/jumpKing" + i  + ".png")));

            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //按序循环导入背景图片
        for (int i = 1; i <= ConstValue.mapTotal; i++) {
            try {
                bgImage.add(ImageIO.read(new File("D:\\IDEA\\IdeaProject\\jumpKing\\src\\util\\images/bg" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            firstImage =bgImage.get(0);
        }

       //导入所有种类的障碍物图片
        for(int i = 1; i <= ConstValue.obstacleKindTotal; i++){
            try{
                allObstacle.add(ImageIO.read(new File("D:\\IDEA\\IdeaProject\\jumpKing\\src\\util\\images/obstacle" + i + ".png")));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}

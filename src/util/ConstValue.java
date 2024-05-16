package util;

import java.util.ArrayList;
import java.util.List;

//常数类
public class ConstValue {
    //属性声明为static后，其他类可以直接通过constValue.gameTitle使用该属性
    static final String gameTitle = "jumppp King!";
    static final int gameWidth = 900;
    static final int gameHeight = 600;

    public static final int fps=30;

    static final int mapTotal = 4;
    static final int jumpKingImageTotal = 2;
    static final int obstacleKindTotal = 3;

    public static final int jumpKingWidth = 20;
    public static final int jumpKingHeight = 30;
    public static final double g = 0.25d;
    public static final double loseVFactor = 0.2d;   //碰撞损失速度（落地情况直接静止）

    //通过obstacle的type访问其宽高
    public static final int obstacleWidth[] = new int[]{120,900,50};
    public static final int obstacleHeight[] = new int[]{50,50,600};



}

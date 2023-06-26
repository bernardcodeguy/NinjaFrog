package fx.games.ninjafrog;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Iterator;

public class Enemy extends Pane {
    ImageView enemy;
    Image image;
    int columns = 6;
    int totalNumberOfFrames = 6;

    int FrameWidth = 52;
    int FrameHeight = 34;
    int FPS = 16;

    int rows = 1;

    public  boolean alive = true;

    InfiniteSpriteAnimation anim;

    private double initialPosX;
    int deltaX = -1;

    int moveSpeed = (int) (1+Math.random()*3);
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Enemy(Image image, int x, int y) {
        this.image = image;
        enemy = new ImageView(image);
        enemy.setFitWidth(InGameController.BLOCK_SIZE);
        enemy.setFitHeight(InGameController.BLOCK_SIZE);
        setTranslateX(x);
        setTranslateY(y);

        anim = new InfiniteSpriteAnimation(enemy,  columns, rows, totalNumberOfFrames, FrameWidth, FrameHeight, FPS);
        anim.start();

        getChildren().add(enemy);
        InGameController.enemies.add(this);
        InGameController.gameRoot.getChildren().add(this);

        initialPosX = x;
    }

    public void move(){
        boolean moveRight = false;
        if(this.getBoundsInParent().intersects(InGameController.player.getBoundsInParent())){

            InGameController.player.setTranslateX(0);
            InGameController.player.setTranslateY(400);
            InGameController.gameRoot.setLayoutX(0);
            new Effect("f_hit.wav",0.5);
            InGameController.playerLives.setValue(InGameController.playerLives.getValue()-1);
        }


        this.setTranslateX(this.getTranslateX()+(moveSpeed*this.deltaX));

        if(this.getTranslateX()+150 == initialPosX){
           this.enemy.setScaleX(-1);
           this.deltaX = 1;
           moveRight = true;
        }else if(this.getTranslateX() == initialPosX){
            this.enemy.setScaleX(1);
            this.deltaX = -1;
            moveRight = false;
        }

    }



}

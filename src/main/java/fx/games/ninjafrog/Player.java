package fx.games.ninjafrog;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Iterator;

public class Player extends Pane {
    Image ninjaImg = new Image(getClass().getResourceAsStream("player.png"));
    ImageView imageView = new ImageView(ninjaImg);
    int count = 4;
    int columns = 4;
    int width = 40;
    int height = 40;

    public static SpriteAnimation animation;
    public Point2D playerVelocity = new Point2D(0,0);
    private boolean canJump = true;

    public Player(){
        imageView.setFitHeight(InGameController.MARIO_SIZE);
        imageView.setFitWidth(InGameController.MARIO_SIZE);

        int offsetX=0;
        int offsetY=0;
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        animation = new SpriteAnimation(this.imageView, Duration.millis(200),count,columns,offsetX,offsetY,width,height);
        getChildren().add(this.imageView);
    }


    public void moveX(int value){

        if(getBoundsInParent().intersects(InGameController.flag.getBoundsInParent())){
            System.out.println("Home");
        }

        boolean movingRight = value > 0;
        for(int i = 0; i<Math.abs(value); i++) {
            for (Node platform : InGameController.platforms) {
                if(this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (this.getTranslateX() + InGameController.MARIO_SIZE == platform.getTranslateX()){
                            this.setTranslateX(this.getTranslateX() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateX() == platform.getTranslateX() + InGameController.BLOCK_SIZE) {
                            this.setTranslateX(this.getTranslateX() + 1);
                            return;
                        }
                    }
                }
            }

            for (Node platform : InGameController.enemies) {
                if(this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        this.setTranslateX(0);
                        this.setTranslateY(400);
                        InGameController.gameRoot.setLayoutX(0);

                        new Effect("f_hit.wav",0.5);
                        InGameController.playerLives.setValue(InGameController.playerLives.getValue()-1);
                    } else {
                        new Effect("f_hit.wav",0.5);
                        InGameController.playerLives.setValue(InGameController.playerLives.getValue()-1);

                    }
                }
            }


            this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    public void moveY(int value){

        boolean movingDown = value >0;



        if(InGameController.fruit != null && getBoundsInParent().intersects(InGameController.fruit.getBoundsInParent())){
            new Effect("eat.wav",0.3);
            InGameController.canShoot = true;
            InGameController.gameRoot.getChildren().remove(InGameController.fruit);
            InGameController.fruit = null;
        }


        for(int i = 0; i < Math.abs(value); i++){
            for(Block platform :InGameController.platforms){
                if(getBoundsInParent().intersects(platform.getBoundsInParent())){

                    if(movingDown){

                        if(this.getTranslateY()+ InGameController.MARIO_SIZE == platform.getTranslateY()){
                            //System.out.println("Yes");
                            this.setTranslateY(this.getTranslateY()-1);
                            canJump = true;
                            return;
                        }
                    }
                    else{
                        if(this.getTranslateY() == platform.getTranslateY()+ InGameController.BLOCK_SIZE){

                            this.setTranslateY(this.getTranslateY()+1);
                            playerVelocity = new Point2D(0,10);
                            return;

                        }
                    }
                }
            }

            for(Enemy enemy :InGameController.enemies){
                if(getBoundsInParent().intersects(enemy.getBoundsInParent())){

                    if(movingDown){

                        if(this.getTranslateY()+ InGameController.MARIO_SIZE == enemy.getTranslateY()){

                            enemy.setAlive(false);
                            InGameController.enemiesleft.setValue(InGameController.enemiesleft.getValue() - 1);
                        }
                    }

                }            }

            for (Iterator<Enemy> it = InGameController.enemies.iterator(); it.hasNext(); ) {
                Enemy enemy = it.next();
                if (!enemy.isAlive()) {
                    it.remove();
                    new Effect("r_hit.wav",0.3);
                    InGameController.gameRoot.getChildren().remove(enemy);
                }
            }


            this.setTranslateY(this.getTranslateY() + (movingDown?1:-1));
            if(this.getTranslateY()>640){
                new Effect("fall.wav",0.3);
                InGameController.playerLives.setValue(InGameController.playerLives.getValue()-1);
                this.setTranslateX(0);
                this.setTranslateY(400);
                InGameController.gameRoot.setLayoutX(0);


            }

        }
    }

    public void jumpPlayer(){
        if(canJump){
            playerVelocity = playerVelocity.add(0,-30);
            canJump = false;
            new Effect("jump.wav",0.3);
        }
    }

}

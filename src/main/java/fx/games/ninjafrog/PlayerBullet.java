package fx.games.ninjafrog;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Iterator;

public class PlayerBullet extends Pane {
    Image ninjaImg = new Image(getClass().getResourceAsStream("bullet.png"));
    ImageView imageView = new ImageView(ninjaImg);


    public boolean rightDir = true;

    public boolean disappear = false;
    public PlayerBullet(){
        imageView.setFitHeight(InGameController.BULLET_SIZE);
        imageView.setFitWidth(InGameController.BULLET_SIZE);

        int offsetX=0;
        int offsetY=0;
        imageView.setViewport(new Rectangle2D(0,0,15,15));
        this.getChildren().add(imageView);

    }

    public boolean isDisappear() {
        return disappear;
    }

    public void setDisappear(boolean disappear) {
        this.disappear = disappear;
    }

    public void shootBullet(){
        if(InGameController.facingRight){
            this.rightDir = true;
        } else{
            this.rightDir = false;
        }
        InGameController.gameRoot.getChildren().add(this);
        this.setTranslateY(InGameController.player.getTranslateY()+15);
        this.setTranslateX(InGameController.player.getTranslateX()+15);
        InGameController.bullets.add(this);
    }

    public void moveBullet(){


        if(this.getTranslateX() > 1280+(InGameController.player.getTranslateX()-40)){
            this.disappear = true;
        }


        for(Block platform :InGameController.platforms){
            if(getBoundsInParent().intersects(platform.getBoundsInParent())){

                this.disappear = true;

            }

        }


        for (Iterator<PlayerBullet> it = InGameController.bullets.iterator(); it.hasNext(); ) {
            PlayerBullet b = it.next();
            if (b.disappear) {
                it.remove();
                InGameController.gameRoot.getChildren().remove(b);

            }
        }


        for(Enemy enemy :InGameController.enemies){
            if(getBoundsInParent().intersects(enemy.getBoundsInParent())){
                InGameController.enemiesleft.setValue(InGameController.enemiesleft.getValue() - 1);
                new Effect("r_hit.wav",0.3);
               enemy.setAlive(false);

            }
        }

        for (Iterator<Enemy> it = InGameController.enemies.iterator(); it.hasNext(); ) {
            Enemy enemy = it.next();
            if (!enemy.isAlive()) {
                it.remove();
                this.disappear = true;
                InGameController.gameRoot.getChildren().remove(enemy);
            }
        }

        if(this.rightDir){
            this.setTranslateX(this.getTranslateX()+(5*1));
        }
        if(!this.rightDir){
            this.setTranslateX(this.getTranslateX()+(5*-1));
        }

    }

}

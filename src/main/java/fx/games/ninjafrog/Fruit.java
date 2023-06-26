package fx.games.ninjafrog;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Fruit extends Pane {
    ImageView fruit;
    Image image;

    int columns = 17;
    int totalNumberOfFrames = 17;

    int FrameWidth = 32;
    int FrameHeight = 32;
    int FPS = 16;

    int rows = 1;

    public  boolean eaten = true;

    InfiniteSpriteAnimation anim;

    public Fruit(Image image, int x, int y) {
        this.image = image;
        fruit = new ImageView(image);
        fruit.setFitWidth(InGameController.BLOCK_SIZE);
        fruit.setFitHeight(InGameController.BLOCK_SIZE);
        setTranslateX(x);
        setTranslateY(y);
        fruit.setViewport(new Rectangle2D(0, 0, 20, 20));

        getChildren().add(fruit);
        InGameController.gameRoot.getChildren().add(this);

        anim = new InfiniteSpriteAnimation(fruit,  columns, rows, totalNumberOfFrames, FrameWidth, FrameHeight, FPS);
        anim.start();
    }
}

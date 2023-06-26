package fx.games.ninjafrog;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Block extends Pane {

    ImageView block;
    Image image;
    public Block(Image image, int x, int y) {
        this.image = image;
        block = new ImageView(image);
        block.setFitWidth(InGameController.BLOCK_SIZE);
        block.setFitHeight(InGameController.BLOCK_SIZE);
        setTranslateX(x);
        setTranslateY(y);
        block.setViewport(new Rectangle2D(0, 0, 20, 20));

        getChildren().add(block);
        InGameController.platforms.add(this);
        InGameController.gameRoot.getChildren().add(this);


    }



}

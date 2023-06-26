package fx.games.ninjafrog;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class InGameController implements Initializable {
    @FXML
    private Rectangle background;


    public static Pane gameRoot =new Pane();


    @FXML
    private Pane uiRoot;

    @FXML
    private Text txtEnemiesLives;

    @FXML
    private Text txtPlayerLives;

    @FXML
    private ImageView imgBullet;
    public static final int MARIO_SIZE = 40;
    public static final int BULLET_SIZE = 10;
    public static final int BLOCK_SIZE = 60;


    public static ArrayList<Block> platforms = new ArrayList<>();

    public static ArrayList<Enemy> enemies = new ArrayList<>();

    public static ArrayList<PlayerBullet> bullets = new ArrayList<>();
    public static HashMap<KeyCode,Boolean> keys = new HashMap<>();
    public static int score;
    public static boolean isFinish;

    public static Player player;

    public static boolean facingRight = true;

    private static final int appWidth = 1280;




    public static SimpleIntegerProperty playerLives = new SimpleIntegerProperty(5);

    public static SimpleIntegerProperty enemiesleft = new SimpleIntegerProperty();

    private int levelWidth;

    public static Fruit fruit;

    public static EndFlag flag;

    MediaPlayer mediaPlayer;

    Media sound;


    public static boolean canShoot = false;
    private Image blockImg = new Image(getClass().getResourceAsStream("block.png"));

    private Image enemyImg = new Image(getClass().getResourceAsStream("enemy.png"));

    private Image fruitImg = new Image(getClass().getResourceAsStream("fruit.png"));

    private Image flagImg = new Image(getClass().getResourceAsStream("flag.png"));


    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {

            update();
        }

    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            sound = new Media(getClass().getResource("background.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        imgBullet.setVisible(false);
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.play();


        background.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("background.png"))));
        player = new Player();

        player.setTranslateX(0);
        player.setTranslateY(600);
        timer.start();
        levelWidth = LevelData.LEVEL1[0].length() * BLOCK_SIZE;

        for (int i = 0; i < LevelData.LEVEL1.length; i++) {
            String line = LevelData.LEVEL1[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Block block = new Block(blockImg,j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '2':
                        Enemy enemy = new Enemy(enemyImg,j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '3':
                       fruit = new Fruit(fruitImg,j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '4':
                       flag = new EndFlag(flagImg,j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                }
            }



        }


        gameRoot.setLayoutX(0);


        player.translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();

            if (offset > appWidth / 2 && offset < levelWidth - appWidth / 2) {
                gameRoot.setLayoutX(-(offset - appWidth / 2));
            }
        });

        enemiesleft.setValue(enemies.size());
        txtEnemiesLives.setText(enemies.size()+"");


        playerLives.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                txtPlayerLives.textProperty().bind(playerLives.asString());
            }
        });

        enemiesleft.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                txtEnemiesLives.textProperty().bind(enemiesleft.asString());
            }
        });


        gameRoot.getChildren().add(player);
        uiRoot.getChildren().addAll(gameRoot);

    }

        private void update(){
                if(canShoot){
                    imgBullet.setVisible(true);
                }

                try{
                    if(bullets.size() > 0 && canShoot){

                        for (PlayerBullet bullet : bullets){
                            bullet.moveBullet();
                        }
                    }
                }catch (Exception e){

                }



                for (Enemy enemy : InGameController.enemies) {
                    enemy.move();
                }

            if((isPressed(KeyCode.UP) || isPressed(KeyCode.SPACE)) && player.getTranslateY()>=5){
                player.jumpPlayer();

            }


            if(isPressed(KeyCode.LEFT) && player.getTranslateX()>=5){
                facingRight = false;
                player.setScaleX(-1);
                player.animation.play();
                player.moveX(-5);

            }


            if(isPressed(KeyCode.RIGHT) && player.getTranslateX()+40 <=levelWidth-5){
                facingRight = true;
                player.setScaleX(1);
                player.animation.play();
                player.moveX(5);

            }

            if(isPressed(KeyCode.LEFT) && isPressed(KeyCode.SHIFT) && player.getTranslateX()>=5){
                System.out.println("Run");
                facingRight = false;
                player.setScaleX(-1);
                player.animation.play();
                player.moveX(-6);
            }

            if(isPressed(KeyCode.RIGHT) && isPressed(KeyCode.SHIFT) && player.getTranslateX()+40 <=levelWidth-5){
                System.out.println("Run");
                facingRight = true;
                player.setScaleX(1);
                player.animation.play();
                player.moveX(6);
            }

            if(player.playerVelocity.getY()<10){
                player.playerVelocity = player.playerVelocity.add(0,1);
            }
            player.moveY((int)player.playerVelocity.getY());
        }

    private boolean isPressed(KeyCode key){
        return keys.getOrDefault(key,false);
    }


    public void shoot(){
        if(!canShoot){
            return;
        }

        new Effect("shoot.wav",0.3);
        PlayerBullet b = new PlayerBullet();
        b.shootBullet();
        System.out.println(b.rightDir);
    }
}
package jp.pkg2.assignment.in.fx;

import java.io.File;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import static javafx.scene.input.KeyCode.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import static javafx.scene.text.FontPosture.ITALIC;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author USER
 */
public class GameStage2 extends Application {
    
    private enum UserAction{
        NONE, LEFT, RIGHT, UP, DOWN;
    }
    private Stage stage;
    
    private MediaPlayer mediaPlayer;

    private static final int APP_W = 1200;
    private static final int APP_H = 800;
    
    private static final int OBSTACLE_W = 50;
    private static final int OBSTACLE_H = 150;
    private static final int AVATAR_W = 50;
    private static final int AVATAR_H = 50;
    
    private Rectangle avatar = new Rectangle(AVATAR_W, AVATAR_H);
    
    private Rectangle obstacle1up = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle2up = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle3up = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle4up = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle5up = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle6up = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    
    private Rectangle obstacle1down = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle2down = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle3down = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle4down = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle5down = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle6down = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    
    private Polygon checkpoint = new Polygon ();
    private Rectangle buttonBarBackground = new Rectangle(1210, 60);
    
    private DropShadow dropShadow = new DropShadow();
    
    private boolean obstacleUp = false;
    private UserAction action = UserAction.NONE;
    
    private Timeline timeline = new Timeline();
    private Timeline obstacleTimeline = new Timeline();
    private Timeline checkpointTimeline = new Timeline();
    private boolean running = true;
    
    
    private Parent createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(APP_W, APP_H);                      
        
        ToggleButton playbtn = new ToggleButton();
            String playImageFile = "src\\Image\\OnSound.png";
            final Image soundImage2 = new Image(new File(playImageFile).toURI().toString());
            final ImageView playImage = new ImageView();
            playImage.setImage(soundImage2);
            playbtn.setGraphic(playImage);
        
        playbtn.setOnAction((ActionEvent e)->{
            if(playbtn.isSelected()){
                String pauseImageFile = "src\\Image\\OffSound.png";
                final Image soundImage1 = new Image(new File(pauseImageFile).toURI().toString());
                final ImageView pauseImage = new ImageView();
                pauseImage.setImage(soundImage1);
                playbtn.setGraphic(pauseImage);
                    stopSound();
            }
            else{
        playImage.setImage(soundImage2);
                playbtn.setGraphic(playImage);
                    playSound();
            }
        });
        
        Button restartbtn = new Button();
            restartbtn.setText("Restart");
            restartbtn.setPrefSize(150, soundImage2.getHeight()+10);
            restartbtn.setFont(Font.font("Lucida Console",ITALIC ,25));
            
        restartbtn.setOnAction((ActionEvent e)->{
            Alert restartAlert = new Alert(Alert.AlertType.CONFIRMATION);
            restartAlert.initStyle(StageStyle.UTILITY);
            restartAlert.setTitle("Confirmation");
            restartAlert.setHeaderText("Do you want to Restart?");
            restartAlert.setContentText("Click OK to confirm");

            Optional<ButtonType> result = restartAlert.showAndWait();
                if (result.get() == ButtonType.OK){
                    this.restartGame();
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            
        });
        Button mainMenubtn = new Button();
            mainMenubtn.setText("Main Menu");
            mainMenubtn.setPrefSize(150, soundImage2.getHeight()+10);
            mainMenubtn.setFont(Font.font("Lucida Console",ITALIC ,20));
            
        mainMenubtn.setOnAction((ActionEvent e)->{
           
            Alert MainMenuAlert = new Alert(Alert.AlertType.CONFIRMATION);
            MainMenuAlert.initStyle(StageStyle.UTILITY);
            MainMenuAlert.setTitle("Confirmation");
            MainMenuAlert.setHeaderText("Do you want to back to Main Menu?");
            MainMenuAlert.setContentText("Click OK to confirm");

            Optional<ButtonType> result = MainMenuAlert.showAndWait();
                if (result.get() == ButtonType.OK){
                    stopSound();
                    stage.close();
                    MainScreenStage mss = new MainScreenStage();// ... user chose OK
                    mss.start(stage);
                } else {
                    // ... user chose CANCEL or closed the dialog
                }

        });
        
        Button QuitBtn = new Button();
            QuitBtn.setText("Quit Game");
            QuitBtn.setFont(Font.font("Lucida Console",ITALIC ,20));
            QuitBtn.setPrefSize(150, soundImage2.getHeight()+10);
            
        QuitBtn.setOnAction((ActionEvent ae) -> {

            Alert QuitAlert = new Alert(Alert.AlertType.CONFIRMATION);
            QuitAlert.initStyle(StageStyle.UTILITY);
            QuitAlert.setTitle("Confirmation");
            QuitAlert.setHeaderText("Do you want to quit game?");
            QuitAlert.setContentText("Click OK to confirm");

            Optional<ButtonType> result = QuitAlert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Platform.exit();// ... user chose OK
                } else {
                    // ... user chose CANCEL or closed the dialog
                }

            });       
        

        buttonBarBackground.setFill(Color.FLORALWHITE);
        buttonBarBackground.toBack();
        
        dropShadow.setRadius(50.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.color(1.0, 1.0, 0.0));
        
        checkpoint.getPoints().addAll(new Double[]{
            1120.0, 370.0,
            1135.0, 420.0,
            1180.0, 420.0,
            1142.5, 450.0,
            1155.0, 490.0,
            1120.0, 470.0,
            1085.0, 490.0,
            1092.5, 450.0,
            1060.0, 420.0,
            1105.0, 420.0
        });
        checkpoint.setEffect(dropShadow);
        checkpoint.setFill(Color.ORANGE);
        
        
        obstacle1up.setTranslateX(APP_W/ 10 * 3);
        obstacle1up.setTranslateY(60);
        obstacle1up.setFill(Color.DARKRED);
        
        obstacle2up.setTranslateX(APP_W/ 10 * 4);
        obstacle2up.setTranslateY(60);
        obstacle2up.setFill(Color.LIGHTBLUE);
        
        obstacle3up.setTranslateX(APP_W/ 10 * 5);
        obstacle3up.setTranslateY(60);
        obstacle3up.setFill(Color.DARKRED);
        
        obstacle4up.setTranslateX(APP_W/ 10 * 6);
        obstacle4up.setTranslateY(60);
        obstacle4up.setFill(Color.GOLD);
        
        obstacle5up.setTranslateX(APP_W/ 10 * 7);
        obstacle5up.setTranslateY(60);
        obstacle5up.setFill(Color.DARKRED);
        
        obstacle6up.setTranslateX(APP_W/ 10 * 8);
        obstacle6up.setTranslateY(60);
        obstacle6up.setFill(Color.LAWNGREEN);
        
        
        obstacle1down.setTranslateX(APP_W/ 10 * 3);
        obstacle1down.setTranslateY(APP_H - OBSTACLE_H);
        obstacle1down.setFill(Color.LIGHTPINK);
        
        obstacle2down.setTranslateX(APP_W/ 10 * 4);
        obstacle2down.setTranslateY(APP_H - OBSTACLE_H);
        obstacle2down.setFill(Color.DARKRED);
        
        obstacle3down.setTranslateX(APP_W/ 10 * 5);
        obstacle3down.setTranslateY(APP_H - OBSTACLE_H);
        obstacle3down.setFill(Color.DODGERBLUE);
        
        obstacle4down.setTranslateX(APP_W/ 10 * 6);
        obstacle4down.setTranslateY(APP_H - OBSTACLE_H);
        obstacle4down.setFill(Color.DARKRED);
        
        obstacle5down.setTranslateX(APP_W/ 10 * 7);
        obstacle5down.setTranslateY(APP_H - OBSTACLE_H);
        obstacle5down.setFill(Color.LIGHTSKYBLUE);
        
        obstacle6down.setTranslateX(APP_W/ 10 * 8);
        obstacle6down.setTranslateY(APP_H - OBSTACLE_H);
        obstacle6down.setFill(Color.DARKRED);
        
        
        avatar.setTranslateX(APP_W / 20 * 1);
        avatar.setTranslateY(APP_H / 2);
        avatar.setArcHeight(20);
        avatar.setArcWidth(20);
        avatar.setFill(Color.DARKGRAY);
        avatar.setStroke(Color.BLACK);

        
        KeyFrame frame = new KeyFrame(Duration.seconds(0.016), event ->{
            if(!running)
                return;
            
            switch(action){
                case LEFT:
                    if (avatar.getTranslateX() - 5 >= 0)
                        avatar.setTranslateX(avatar.getTranslateX() - 5);
                    break;
                case RIGHT:
                    if (avatar.getTranslateX() + AVATAR_W + 5 <= APP_W)
                    avatar.setTranslateX(avatar.getTranslateX() + 5);
                    break;
                case UP:
                    if (avatar.getTranslateY() - 5 >= 60)
                    avatar.setTranslateY(avatar.getTranslateY() - 5);
                    break;
                case DOWN:
                    if (avatar.getTranslateY() + AVATAR_H + 5 <= APP_H)
                    avatar.setTranslateY(avatar.getTranslateY() + 5);
                    break;                    
                case NONE:
                    break;
            }
        });     
        
        KeyFrame obstacleFrame1 = new KeyFrame(Duration.seconds(0.005),event ->{
            if(!running)
                return; 
            
            obstacle1up.setTranslateX(obstacle1up.getTranslateX());
            obstacle1up.setTranslateY(obstacle1up.getTranslateY());
            
            if (obstacle1up.getTranslateX() <= avatar.getTranslateX() + AVATAR_W -1
                    && obstacle1up.getTranslateX() + OBSTACLE_W >= avatar.getTranslateX() 
                    && obstacle1up.getTranslateY() + OBSTACLE_H != APP_H/2){
                obstacle1up.setTranslateY(obstacle1up.getTranslateY() + (obstacleUp ? -10 : 10));
                obstacleUp = false;
            }

            Shape collision1 = Shape.intersect(avatar, obstacle1up);
            if(collision1.getBoundsInLocal().getWidth() != -1)
                restartGame();
            Shape collision2 = Shape.intersect(avatar, obstacle1down);
            if(collision2.getBoundsInLocal().getWidth() != -1)
                restartGame();
        });
        
        KeyFrame obstacleFrame2 = new KeyFrame(Duration.seconds(0.005),event ->{
            if(!running)
                return; 
            
            obstacle2down.setTranslateX(obstacle2down.getTranslateX());
            obstacle2down.setTranslateY(obstacle2down.getTranslateY());
            
            if (obstacle2down.getTranslateX() <= avatar.getTranslateX() + AVATAR_W -1
                    && obstacle2down.getTranslateX() + OBSTACLE_W >= avatar.getTranslateX() 
                    && obstacle2down.getTranslateY() != APP_H/2){
                obstacle2down.setTranslateY(obstacle2down.getTranslateY() + (obstacleUp ? -10 : 10));
                obstacleUp = true;
            }

            Shape collision3 = Shape.intersect(avatar, obstacle2down);
            if(collision3.getBoundsInLocal().getWidth() != -1)
                restartGame();
            Shape collision4 = Shape.intersect(avatar, obstacle2up);
            if(collision4.getBoundsInLocal().getWidth() != -1)
                restartGame();
        });
        
        KeyFrame obstacleFrame3 = new KeyFrame(Duration.seconds(0.005),event ->{
            if(!running)
                return; 
            
            obstacle3up.setTranslateX(obstacle3up.getTranslateX());
            obstacle3up.setTranslateY(obstacle3up.getTranslateY());
            
            if (obstacle3up.getTranslateX() <= avatar.getTranslateX() + AVATAR_W -1
                    && obstacle3up.getTranslateX() + OBSTACLE_W >= avatar.getTranslateX() 
                    && obstacle3up.getTranslateY() + OBSTACLE_H != APP_H/2){
                obstacle3up.setTranslateY(obstacle3up.getTranslateY() + (obstacleUp ? -10 : 10));
                obstacleUp = false;
            }

            Shape collision5 = Shape.intersect(avatar, obstacle3up);
            if(collision5.getBoundsInLocal().getWidth() != -1)
                restartGame();
            Shape collision6 = Shape.intersect(avatar, obstacle3down);
            if(collision6.getBoundsInLocal().getWidth() != -1)
                restartGame();
        });
        
        KeyFrame obstacleFrame4 = new KeyFrame(Duration.seconds(0.005),event ->{
            if(!running)
                return; 
            
            obstacle4down.setTranslateX(obstacle4down.getTranslateX());
            obstacle4down.setTranslateY(obstacle4down.getTranslateY());
            
            if (obstacle4down.getTranslateX() <= avatar.getTranslateX() + AVATAR_W -1
                    && obstacle4down.getTranslateX() + OBSTACLE_W >= avatar.getTranslateX() 
                    && obstacle4down.getTranslateY() != APP_H/2){
                obstacle4down.setTranslateY(obstacle4down.getTranslateY() + (obstacleUp ? -10 : 10));
                obstacleUp = true;
            }

            Shape collision7 = Shape.intersect(avatar, obstacle4down);
            if(collision7.getBoundsInLocal().getWidth() != -1)
                restartGame();
            Shape collision8 = Shape.intersect(avatar, obstacle4up);
            if(collision8.getBoundsInLocal().getWidth() != -1)
                restartGame();
        });
        
        KeyFrame obstacleFrame5 = new KeyFrame(Duration.seconds(0.005),event ->{
            if(!running)
                return; 
            
            obstacle5up.setTranslateX(obstacle5up.getTranslateX());
            obstacle5up.setTranslateY(obstacle5up.getTranslateY());
            
            if (obstacle5up.getTranslateX() <= avatar.getTranslateX() + AVATAR_W -1
                    && obstacle5up.getTranslateX() + OBSTACLE_W >= avatar.getTranslateX() 
                    && obstacle5up.getTranslateY() + OBSTACLE_H != APP_H/2){
                obstacle5up.setTranslateY(obstacle5up.getTranslateY() + (obstacleUp ? -10 : 10));
                obstacleUp = false;
            }

            Shape collision9 = Shape.intersect(avatar, obstacle5up);
            if(collision9.getBoundsInLocal().getWidth() != -1)
                restartGame();
            Shape collision10 = Shape.intersect(avatar, obstacle5down);
            if(collision10.getBoundsInLocal().getWidth() != -1)
                restartGame();
        });
                
        KeyFrame obstacleFrame6 = new KeyFrame(Duration.seconds(0.005),event ->{
            if(!running)
                return; 
            
            obstacle6up.setTranslateX(obstacle6up.getTranslateX());
            obstacle6up.setTranslateY(obstacle6up.getTranslateY());
            
            if (obstacle6up.getTranslateX() <= avatar.getTranslateX() + AVATAR_W -1
                    && obstacle6up.getTranslateX() + OBSTACLE_W >= avatar.getTranslateX() 
                    && obstacle6up.getTranslateY() + OBSTACLE_H != APP_H/2){
                obstacle6up.setTranslateY(obstacle6up.getTranslateY() + (obstacleUp ? -10 : 10));
                obstacleUp = false;
                obstacle6down.setFill(Color.LAWNGREEN);
                obstacle6up.setFill(Color.DARKRED);
            }

            Shape collision11 = Shape.intersect(avatar, obstacle6up);
            if(collision11.getBoundsInLocal().getWidth() != -1)
                restartGame();
            
            Shape collision12 = Shape.intersect(avatar, obstacle6down);
            if(collision12.getBoundsInLocal().getWidth() != -1)
                restartGame(); 
        });
        
        KeyFrame checkpointFrame = new KeyFrame(Duration.seconds(0.010),event ->{
            if(!running)
                return; 
            
            checkpoint.setTranslateX(checkpoint.getTranslateX());
            checkpoint.setTranslateY(checkpoint.getTranslateY());            

            Shape collision = Shape.intersect(avatar, checkpoint);
            if(collision.getBoundsInLocal().getWidth() != -1){
                if(this.mediaPlayer.isMute()){ 
                    playSound();
                    stopSound();                   
                }
                else{       
                    stopSound();
                }
                stopGame();//prevent open more than one GameOverStage
                stage.close();
                GameStage3 gs3 = new GameStage3();
                Stage gameStage3 = new Stage();
                gs3.start(gameStage3);
            }
        });
        
        timeline.getKeyFrames().addAll(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        obstacleTimeline.getKeyFrames().addAll(obstacleFrame1, obstacleFrame2, obstacleFrame3, obstacleFrame4, obstacleFrame5, obstacleFrame6);
        obstacleTimeline.setCycleCount(Timeline.INDEFINITE);
        
        checkpointTimeline.getKeyFrames().add(checkpointFrame);
        checkpointTimeline.setCycleCount(Timeline.INDEFINITE);               
        
        HBox buttonBar = new HBox(playbtn, restartbtn, mainMenubtn, QuitBtn);
        buttonBar.setSpacing(15);
        buttonBar.setAlignment(Pos.CENTER);      
        root.getChildren().addAll(buttonBarBackground, obstacle1up, obstacle2up, obstacle3up, obstacle4up, obstacle5up, obstacle6up, obstacle1down, obstacle2down, obstacle3down ,obstacle4down, obstacle5down, obstacle6down, avatar, checkpoint);
        root.setTop(buttonBar);
        return root;
    }
    public void restartGame(){
        stopGame();
        startGame();
    }
    
    public void stopGame(){
        running = false;
        timeline.stop();
        obstacleTimeline.stop();
        checkpointTimeline.stop();
    }
    
    public void startGame(){
        
        avatar.setTranslateX(APP_W / 20 * 1);
        avatar.setTranslateY(APP_H / 2);
        
        obstacle1up.setTranslateX(APP_W/ 10 * 3);
        obstacle1up.setTranslateY(60);
        obstacle2down.setTranslateX(APP_W/ 10 * 4);
        obstacle2down.setTranslateY(APP_H - OBSTACLE_H);
        obstacle3up.setTranslateX(APP_W/ 10 * 5);
        obstacle3up.setTranslateY(60);
        obstacle4down.setTranslateX(APP_W/ 10 * 6);
        obstacle4down.setTranslateY(APP_H - OBSTACLE_H);
        obstacle5up.setTranslateX(APP_W/ 10 * 7);
        obstacle5up.setTranslateY(60);
        obstacle6up.setTranslateX(APP_W/ 10 * 8);
        obstacle6up.setTranslateY(60);
        obstacle6up.setFill(Color.LAWNGREEN);
        obstacle6down.setFill(Color.DARKRED);

        timeline.play();
        obstacleTimeline.play();
        checkpointTimeline.play();
        running = true;
    }

    public void start(Stage gameStage2) {
        playSound();
        stage = gameStage2;
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(event ->{
            switch (event.getCode()){
                case A:
                    action = UserAction.LEFT;
                    break;
                case D:
                    action = UserAction.RIGHT;
                    break;
                case W:
                    action = UserAction.UP;
                    break;
                case S:
                    action = UserAction.DOWN;
                    break;                      
            }
        });
        
        scene.setOnKeyReleased(event ->{
            switch (event.getCode()){
            case A:
                action = UserAction.NONE;
                break;               
            case D:
                action = UserAction.NONE;
                break;
            case W:
                action = UserAction.NONE;
                break;
            case S:
                action = UserAction.NONE;
                break;                   
            }
        });       
        
        gameStage2.setTitle("Stage 2");
        gameStage2.setScene(scene);
        gameStage2.setResizable(false);
        gameStage2.show();
        startGame();
    }
    public void playSound(){
    String musicFile = "src\\Music\\Tours_-_01_-_Enthusiast.mp3";

    Media sound = new Media(new File(musicFile).toURI().toString());
    mediaPlayer = new MediaPlayer(sound);        
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.play();
    }
    public void stopSound(){
    mediaPlayer.stop();
    }
}

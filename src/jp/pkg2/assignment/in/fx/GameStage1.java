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
public class GameStage1 extends Application {
    
    private enum UserAction{
        NONE, LEFT, RIGHT, UP, DOWN;
    }
    private Stage stage;
    
    private MediaPlayer mediaPlayer;

    private static final int APP_W = 1200;
    private static final int APP_H = 800;
    
    private static final int OBSTACLE_W = 80;
    private static final int OBSTACLE_H = 150;
    private static final int AVATAR_W = 50;
    private static final int AVATAR_H = 50;
    

    private Rectangle avatar = new Rectangle(AVATAR_W, AVATAR_H);
    private Rectangle obstacle1 = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle2 = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle3 = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle4 = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Polygon checkpoint = new Polygon ();
    private Rectangle buttonBarBackground = new Rectangle(1210, 60);
    
    private DropShadow dropShadow = new DropShadow();

    private boolean obstacleUp1 = false;
    private boolean obstacleUp2 = false;
    private boolean obstacleUp3 = false;
    private boolean obstacleUp4 = false;
    private UserAction action = UserAction.NONE;
    
    private Timeline timeline = new Timeline();
    private Timeline obstacleTimeline1 = new Timeline();
    private Timeline obstacleTimeline2 = new Timeline();
    private Timeline obstacleTimeline3 = new Timeline();
    private Timeline obstacleTimeline4 = new Timeline();
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
        //Create restart button
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
            1000.0, 400.0,
            1015.0, 450.0,
            1060.0, 450.0,
            1022.5, 480.0,
            1035.0, 520.0,
            1000.0, 500.0,
            965.0,  520.0,
            972.5,  480.0,
            940.0,  450.0,
            985.0,  450.0,
        });
        checkpoint.setEffect(dropShadow);
        checkpoint.setFill(Color.ORANGE);
        
        obstacle1.setTranslateX(APP_W/ 20 * 5);
        obstacle1.setTranslateY(60);
        obstacle1.setFill(Color.BROWN);
        
        obstacle2.setTranslateX(APP_W/ 20 * 8);
        obstacle2.setTranslateY(60);
        obstacle2.setFill(Color.DARKGREEN);
        
        obstacle3.setTranslateX(APP_W/ 20 * 11);
        obstacle3.setTranslateY(60);
        obstacle3.setFill(Color.DARKBLUE);
        
        obstacle4.setTranslateX(APP_W/ 20 * 14);
        obstacle4.setTranslateY(60);
        obstacle4.setFill(Color.LIGHTSKYBLUE);
        
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
                        avatar.setTranslateX(avatar.getTranslateX() - 8);
                    break;
                case RIGHT:
                    if (avatar.getTranslateX() + AVATAR_W + 5 <= APP_W)
                    avatar.setTranslateX(avatar.getTranslateX() + 8);
                    break;
                case UP:
                    if (avatar.getTranslateY() - 5 >= 60)
                    avatar.setTranslateY(avatar.getTranslateY() - 8);
                    break;
                case DOWN:
                    if (avatar.getTranslateY() + AVATAR_H + 5 <= APP_H)
                    avatar.setTranslateY(avatar.getTranslateY() + 8);
                    break;                    
                case NONE:
                    break;
            }
        });
        
        KeyFrame obstacleFrame1 = new KeyFrame(Duration.seconds(0.010),event ->{
            if(!running)
                return; 
            
            obstacle1.setTranslateX(obstacle1.getTranslateX());
            obstacle1.setTranslateY(obstacle1.getTranslateY() + (obstacleUp1 ? -5 : 5));
            
            if (obstacle1.getTranslateY() == 60)
                obstacleUp1 = false;
            if (obstacle1.getTranslateY() + OBSTACLE_H == APP_H)
                obstacleUp1 = true;           

            Shape collision1 = Shape.intersect(avatar, obstacle1);
            if(collision1.getBoundsInLocal().getWidth() != -1)
                restartGame();                                          
        });
        
        KeyFrame obstacleFrame2 = new KeyFrame(Duration.seconds(0.008),event ->{
            if(!running)
                return; 
            
            obstacle2.setTranslateX(obstacle2.getTranslateX());
            obstacle2.setTranslateY(obstacle2.getTranslateY() + (obstacleUp2 ? -5 : 5));
            
            if (obstacle2.getTranslateY() == 60)
                obstacleUp2 = false;
            if (obstacle2.getTranslateY() + OBSTACLE_H == APP_H)
                obstacleUp2 = true;           

            Shape collision2 = Shape.intersect(avatar, obstacle2);
            if(collision2.getBoundsInLocal().getWidth() != -1)
                restartGame();                                          
        });

        KeyFrame obstacleFrame3 = new KeyFrame(Duration.seconds(0.006),event ->{
            if(!running)
                return; 
            
            obstacle3.setTranslateX(obstacle3.getTranslateX());
            obstacle3.setTranslateY(obstacle3.getTranslateY() + (obstacleUp3 ? -5 : 5));
            
            if (obstacle3.getTranslateY() == 60)
                obstacleUp3 = false;
            if (obstacle3.getTranslateY() + OBSTACLE_H == APP_H)
                obstacleUp3 = true;           

            Shape collision3 = Shape.intersect(avatar, obstacle3);
            if(collision3.getBoundsInLocal().getWidth() != -1)
                restartGame();                                          
        });

        KeyFrame obstacleFrame4 = new KeyFrame(Duration.seconds(0.004),event ->{
            if(!running)
                return; 
            
            obstacle4.setTranslateX(obstacle4.getTranslateX());
            obstacle4.setTranslateY(obstacle4.getTranslateY() + (obstacleUp4 ? -5 : 5));
            
            if (obstacle4.getTranslateY() == 60)
                obstacleUp4 = false;
            if (obstacle4.getTranslateY() + OBSTACLE_H == APP_H)
                obstacleUp4 = true;           

            Shape collision4 = Shape.intersect(avatar, obstacle4);
            if(collision4.getBoundsInLocal().getWidth() != -1)
                restartGame();                                          
        });        
        
        KeyFrame checkpointFrame = new KeyFrame(Duration.seconds(0.001),event ->{
            if(!running)
                return; 
            
            checkpoint.setTranslateX(checkpoint.getTranslateX());
            checkpoint.setTranslateY(checkpoint.getTranslateY());            

            Shape collision5 = Shape.intersect(avatar, checkpoint);
            if(collision5.getBoundsInLocal().getWidth() != -1){
                if(this.mediaPlayer.isMute()){ 
                    playSound();
                    stopSound();                   
                }
                else{       
                    stopSound();
                }
                stopGame();//prevent open more than one GameOverStage
                stage.close();
                GameStage2 go = new GameStage2();
                Stage gameStage2 = new Stage();
                go.start(gameStage2);
            }
        });
        
        timeline.getKeyFrames().addAll(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);      
        
        obstacleTimeline1.getKeyFrames().add(obstacleFrame1);
        obstacleTimeline1.setCycleCount(Timeline.INDEFINITE);
        obstacleTimeline2.getKeyFrames().add(obstacleFrame2);
        obstacleTimeline2.setCycleCount(Timeline.INDEFINITE);
        obstacleTimeline3.getKeyFrames().add(obstacleFrame3);
        obstacleTimeline3.setCycleCount(Timeline.INDEFINITE);
        obstacleTimeline4.getKeyFrames().add(obstacleFrame4);
        obstacleTimeline4.setCycleCount(Timeline.INDEFINITE);
        
        checkpointTimeline.getKeyFrames().add(checkpointFrame);
        checkpointTimeline.setCycleCount(Timeline.INDEFINITE);               
        
        HBox buttonBar = new HBox(playbtn, restartbtn, mainMenubtn, QuitBtn);
        buttonBar.setSpacing(15);
        buttonBar.setAlignment(Pos.CENTER);      
        root.getChildren().addAll(buttonBarBackground, obstacle1, obstacle2, obstacle3, obstacle4, avatar, checkpoint);
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
        obstacleTimeline1.stop();
        obstacleTimeline2.stop();
        obstacleTimeline3.stop();
        obstacleTimeline4.stop();
        checkpointTimeline.stop();
    }
    
    public void startGame(){
        
        avatar.setTranslateX(APP_W / 20 * 1);
        avatar.setTranslateY(APP_H / 2);

        timeline.play();
        obstacleTimeline1.play();
        obstacleTimeline2.play();
        obstacleTimeline3.play();
        obstacleTimeline4.play();
        checkpointTimeline.play();
        running = true;
    }

    public void start(Stage gameStage) {
        playSound();
        stage = gameStage;
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
        
        gameStage.setTitle("Stage 1");
        gameStage.setScene(scene);
        gameStage.setResizable(false);
        gameStage.show();
        startGame();
    }
    public void playSound(){
    String musicFile = "src\\Music\\02_-_Favorite_Secrets.mp3";

    Media sound = new Media(new File(musicFile).toURI().toString());
    mediaPlayer = new MediaPlayer(sound);        
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.play();        
    }
    public void stopSound(){
    mediaPlayer.stop();

    }
}

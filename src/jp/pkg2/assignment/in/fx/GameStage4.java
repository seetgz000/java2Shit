package jp.pkg2.assignment.in.fx;

import java.io.File;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
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
public class GameStage4 extends Application {
    
    private enum UserAction{
        NONE, LEFT, RIGHT, UP, DOWN;
    }
    private Stage stage;
    
    private MediaPlayer mediaPlayer;

    private static final int APP_W = 1200;
    private static final int APP_H = 800;
    
    private static final int OBSTACLE_W = 40;
    private static final int OBSTACLE_H = 500;
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
        
        obstacle1.setTranslateX(APP_W/ 10 * 6);
        obstacle1.setTranslateY(60);
        obstacle1.setArcHeight(50);
        obstacle1.setArcWidth(50);
        obstacle1.setFill(Color.FIREBRICK);
        
        obstacle2.setTranslateX(APP_W/ 10 * 5);
        obstacle2.setTranslateY(APP_H - OBSTACLE_H);
        obstacle2.setArcHeight(50);
        obstacle2.setArcWidth(50);
        obstacle2.setFill(Color.DARKCYAN);
        
        obstacle3.setTranslateX(APP_W/ 10 * 4);
        obstacle3.setTranslateY(60);
        obstacle3.setArcHeight(50);
        obstacle3.setArcWidth(50);
        obstacle3.setFill(Color.DARKTURQUOISE);
        
        obstacle4.setTranslateX(APP_W/ 10 * 3);
        obstacle4.setTranslateY(APP_H - OBSTACLE_H);
        obstacle4.setArcHeight(50);
        obstacle4.setArcWidth(50);
        obstacle4.setFill(Color.CORNFLOWERBLUE);
        
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
      
        KeyFrame obstacleFrame = new KeyFrame(Duration.seconds(0.005),event ->{
            if(!running)
                return;                    

            Shape collision1 = Shape.intersect(avatar, obstacle1);
            if(collision1.getBoundsInLocal().getWidth() != -1)
                restartGame();
            
            Shape collision2 = Shape.intersect(avatar, obstacle2);
            if(collision2.getBoundsInLocal().getWidth() != -1)
                restartGame();
            
            Shape collision3 = Shape.intersect(avatar, obstacle3);
            if(collision3.getBoundsInLocal().getWidth() != -1)
                restartGame();
            
            Shape collision4 = Shape.intersect(avatar, obstacle4);
            if(collision4.getBoundsInLocal().getWidth() != -1)
                restartGame();
        });
        KeyFrame checkpointFrame = new KeyFrame(Duration.seconds(0.010),event ->{
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
                GameStage5 gs5 = new GameStage5();
                Stage gameStage5 = new Stage();
                gs5.start(gameStage5);
            }
        });
        
        RotateTransition rt1 = new RotateTransition(Duration.seconds(1.000), obstacle1);
            rt1.setByAngle(180);
            rt1.setCycleCount(Timeline.INDEFINITE);
            rt1.setAutoReverse(true);
            rt1.play();
        RotateTransition rt2 = new RotateTransition(Duration.seconds(1.000), obstacle2);
            rt2.setByAngle(360);
            rt2.setCycleCount(Timeline.INDEFINITE);
            rt2.setAutoReverse(true);
            rt2.play();
        RotateTransition rt3 = new RotateTransition(Duration.seconds(1.000), obstacle3);
            rt3.setByAngle(180);
            rt3.setCycleCount(Timeline.INDEFINITE);
            rt3.play();
        RotateTransition rt4 = new RotateTransition(Duration.seconds(1.000), obstacle4);
            rt4.setByAngle(360);
            rt4.setCycleCount(Timeline.INDEFINITE);
            rt4.play();
        
        timeline.getKeyFrames().addAll(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);    
        
        obstacleTimeline.getKeyFrames().add(obstacleFrame);
        obstacleTimeline.setCycleCount(Timeline.INDEFINITE);
        
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
        obstacleTimeline.stop();
        checkpointTimeline.stop();
    }
    
    public void startGame(){     
        avatar.setTranslateX(APP_W / 20 * 1);
        avatar.setTranslateY(APP_H / 2);

        timeline.play();
        obstacleTimeline.play();
        checkpointTimeline.play();
        running = true;
    }

    public void start(Stage gameStage4) {
        playSound();
        stage = gameStage4;
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
        
        gameStage4.setTitle("Stage 4");
        gameStage4.setScene(scene);
        gameStage4.setResizable(false);
        gameStage4.show();
        startGame();
    }
    public void playSound(){
    String musicFile = "src\\Music\\Jahzzar_-_05_-_Siesta.mp3";

    Media sound = new Media(new File(musicFile).toURI().toString());
    mediaPlayer = new MediaPlayer(sound);        
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.play();        
    }
    public void stopSound(){
    mediaPlayer.stop();

    }
}

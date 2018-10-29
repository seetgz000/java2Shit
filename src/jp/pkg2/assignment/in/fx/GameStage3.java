package jp.pkg2.assignment.in.fx;

import java.io.File;
import java.util.Optional;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
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
import javafx.scene.shape.Circle;
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
public class GameStage3 extends Application {
    
    private enum UserAction{
        NONE, LEFT, RIGHT, UP, DOWN;
    }
    private Stage stage;
    
    private MediaPlayer mediaPlayer;

    private static final int APP_W = 1200;
    private static final int APP_H = 800;
    
    private static final int BALL_RADIUS = 50;
    private static final int AVATAR_W = 50;
    private static final int AVATAR_H = 50;
    
    private Circle ball1 = new Circle(BALL_RADIUS);
    private Circle ball2 = new Circle(BALL_RADIUS);
    private Circle ball3 = new Circle(BALL_RADIUS);
    private Circle ball4 = new Circle(BALL_RADIUS);
    private Circle ball5 = new Circle(BALL_RADIUS);
    private Rectangle avatar = new Rectangle(AVATAR_W, AVATAR_H);
    private Polygon checkpoint = new Polygon ();
    private Rectangle buttonBarBackground = new Rectangle(1210, 60);
    
    private DropShadow dropShadow = new DropShadow();
    
    private boolean ballUp1 = true, ballLeft1 = false;
    private boolean ballUp2 = true, ballLeft2 = false;
    private boolean ballUp3 = true, ballLeft3 = false;
    private boolean ballUp4 = true, ballLeft4 = false;
    private boolean ballUp5 = true, ballLeft5 = false;

    private UserAction action = UserAction.NONE;
    
    private Timeline timeline = new Timeline();
    private Timeline ballTimeline1 = new Timeline();
    private Timeline ballTimeline2 = new Timeline();
    private Timeline ballTimeline3 = new Timeline();
    private Timeline ballTimeline4 = new Timeline();
    private Timeline ballTimeline5 = new Timeline();
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
        
        avatar.setTranslateX(APP_W / 20 * 1);
        avatar.setTranslateY(APP_H / 2);
        avatar.setArcHeight(20);
        avatar.setArcWidth(20);
        avatar.setFill(Color.DARKGRAY);
        avatar.setStroke(Color.BLACK);
        
        ball1.setFill(Color.DARKSLATEBLUE);      
        ball2.setFill(Color.DARKTURQUOISE);      
        ball3.setFill(Color.DARKOLIVEGREEN);       
        ball4.setFill(Color.DARKMAGENTA);
        ball5.setFill(Color.DARKSEAGREEN);
        
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
        
        KeyFrame ballFrame1 = new KeyFrame(Duration.seconds(0.012), event ->{
            if(!running)
                return;
                        
            ball1.setTranslateX(ball1.getTranslateX() + (ballLeft1 ? -5 : 5));
            ball1.setTranslateY(ball1.getTranslateY() + (ballUp1 ? -5 : 5));
                        
            
            if (ball1.getTranslateX() - BALL_RADIUS == 0)
                ballLeft1 = false;
            else if (ball1.getTranslateX() + BALL_RADIUS == APP_W)
                ballLeft1 = true;
            if(ball1.getTranslateY() - BALL_RADIUS == 60){
                ballUp1 = false;
                FadeTransition ft = new FadeTransition(Duration.seconds(3.000), ball1);
                ft.setFromValue(1.0);
                ft.setToValue(0.001);
                ft.setCycleCount(Timeline.INDEFINITE);
                ft.setAutoReverse(true);
                ft.play();
            }
            
            if (ball1.getTranslateY() + BALL_RADIUS == APP_H)
                ballUp1 = true;
                       
            Shape collision = Shape.intersect(avatar, ball1);
            if (collision.getBoundsInLocal().getWidth() != -1){               
                restartGame();
            }
        });
        
        KeyFrame ballFrame2 = new KeyFrame(Duration.seconds(0.014), event ->{
            if(!running)
                return;
                        
            ball2.setTranslateX(ball2.getTranslateX() + (ballLeft2 ? -5 : 5));
            ball2.setTranslateY(ball2.getTranslateY() + (ballUp2 ? -5 : 5));
                        
            
            if (ball2.getTranslateX() - BALL_RADIUS == 0)
                ballLeft2 = false;
            else if (ball2.getTranslateX() + BALL_RADIUS == APP_W)
                ballLeft2 = true;
            if(ball2.getTranslateY() - BALL_RADIUS == 60){
                ballUp2 = false;
                FadeTransition ft = new FadeTransition(Duration.seconds(3.000), ball2);
                ft.setFromValue(1.0);
                ft.setToValue(0.001);
                ft.setCycleCount(Timeline.INDEFINITE);
                ft.setAutoReverse(true);
                ft.play();
            }
            
            if (ball2.getTranslateY() + BALL_RADIUS == APP_H)
                ballUp2 = true;
                       
            Shape collision = Shape.intersect(avatar, ball2);
            if (collision.getBoundsInLocal().getWidth() != -1){               
                restartGame();
            }
        });
                
        KeyFrame ballFrame3 = new KeyFrame(Duration.seconds(0.016), event ->{
            if(!running)
                return;
                        
            ball3.setTranslateX(ball3.getTranslateX() + (ballLeft3 ? -5 : 5));
            ball3.setTranslateY(ball3.getTranslateY() + (ballUp3 ? -5 : 5));
                        
            
            if (ball3.getTranslateX() - BALL_RADIUS == 0)
                ballLeft3 = false;
            else if (ball3.getTranslateX() + BALL_RADIUS == APP_W)
                ballLeft3 = true;
            if(ball3.getTranslateY() - BALL_RADIUS == 60){
                ballUp3 = false;
                FadeTransition ft = new FadeTransition(Duration.seconds(3.000), ball3);
                ft.setFromValue(1.0);
                ft.setToValue(0.001);
                ft.setCycleCount(Timeline.INDEFINITE);
                ft.setAutoReverse(true);
                ft.play();
            }
            
            if (ball3.getTranslateY() + BALL_RADIUS == APP_H)
                ballUp3 = true;
                       
            Shape collision = Shape.intersect(avatar, ball3);
            if (collision.getBoundsInLocal().getWidth() != -1){               
                restartGame();
            }
        });
                
        KeyFrame ballFrame4 = new KeyFrame(Duration.seconds(0.018), event ->{
            if(!running)
                return;
                        
            ball4.setTranslateX(ball4.getTranslateX() + (ballLeft4 ? -5 : 5));
            ball4.setTranslateY(ball4.getTranslateY() + (ballUp4 ? -5 : 5));
                        
            
            if (ball4.getTranslateX() - BALL_RADIUS == 0)
                ballLeft4 = false;
            else if (ball4.getTranslateX() + BALL_RADIUS == APP_W)
                ballLeft4 = true;
            if(ball4.getTranslateY() - BALL_RADIUS == 60){
                ballUp4 = false;
                FadeTransition ft = new FadeTransition(Duration.seconds(3.000), ball4);
                ft.setFromValue(1.0);
                ft.setToValue(0.001);
                ft.setCycleCount(Timeline.INDEFINITE);
                ft.setAutoReverse(true);
                ft.play();
            }
            
            if (ball4.getTranslateY() + BALL_RADIUS == APP_H)
                ballUp4 = true;
                       
            Shape collision = Shape.intersect(avatar, ball4);
            if (collision.getBoundsInLocal().getWidth() != -1){               
                restartGame();
            }
        });
        
        KeyFrame ballFrame5 = new KeyFrame(Duration.seconds(0.020), event ->{
            if(!running)
                return;
                        
            ball5.setTranslateX(ball5.getTranslateX() + (ballLeft5 ? -5 : 5));
            ball5.setTranslateY(ball5.getTranslateY() + (ballUp5 ? -5 : 5));
                        
            
            if (ball5.getTranslateX() - BALL_RADIUS == 0)
                ballLeft5 = false;
            else if (ball5.getTranslateX() + BALL_RADIUS == APP_W)
                ballLeft5 = true;
            if(ball5.getTranslateY() - BALL_RADIUS == 60){
                ballUp5 = false;
                FadeTransition ft = new FadeTransition(Duration.seconds(3.000), ball5);
                ft.setFromValue(1.0);
                ft.setToValue(0.001);
                ft.setCycleCount(Timeline.INDEFINITE);
                ft.setAutoReverse(true);
                ft.play();
            }
            
            if (ball5.getTranslateY() + BALL_RADIUS == APP_H)
                ballUp5 = true;
                       
            Shape collision = Shape.intersect(avatar, ball5);
            if (collision.getBoundsInLocal().getWidth() != -1){               
                restartGame();
            }
        });
        
        KeyFrame checkpointFrame = new KeyFrame(Duration.seconds(0.080),event ->{
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
                GameStage4 gs4 = new GameStage4();
                Stage gameStage4 = new Stage();
                gs4.start(gameStage4);
            }
        });
        
        timeline.getKeyFrames().addAll(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        ballTimeline1.getKeyFrames().add(ballFrame1);
        ballTimeline1.setCycleCount(Timeline.INDEFINITE);
        ballTimeline2.getKeyFrames().add(ballFrame2);
        ballTimeline2.setCycleCount(Timeline.INDEFINITE);
        ballTimeline3.getKeyFrames().add(ballFrame3);
        ballTimeline3.setCycleCount(Timeline.INDEFINITE);
        ballTimeline4.getKeyFrames().add(ballFrame4);
        ballTimeline4.setCycleCount(Timeline.INDEFINITE);
        ballTimeline5.getKeyFrames().add(ballFrame5);
        ballTimeline5.setCycleCount(Timeline.INDEFINITE);
        
        checkpointTimeline.getKeyFrames().add(checkpointFrame);
        checkpointTimeline.setCycleCount(Timeline.INDEFINITE);            
        
        HBox buttonBar = new HBox(playbtn, restartbtn, mainMenubtn, QuitBtn);
        buttonBar.setSpacing(15);
        buttonBar.setAlignment(Pos.CENTER);      
        root.getChildren().addAll(buttonBarBackground, ball1, ball2, ball3, ball4, ball5, avatar, checkpoint);
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
        ballTimeline1.stop();
        ballTimeline2.stop();
        ballTimeline3.stop();
        ballTimeline4.stop();
        ballTimeline5.stop();
        checkpointTimeline.stop();
    }
    
    public void startGame(){
        ballUp1 = true;
        ballUp2 = true;
        ballUp3 = true;
        ballUp4 = true;
        ball1.setTranslateX(APP_W / 10 * 8);
        ball1.setTranslateY(APP_H / 5 * 1);
        
        ball2.setTranslateX(APP_W / 10 * 7);
        ball2.setTranslateY(APP_H / 5 * 2);
        
        ball3.setTranslateX(APP_W / 10 * 6);
        ball3.setTranslateY(APP_H /  5 * 3);
        
        ball4.setTranslateX(APP_W / 10 * 5);
        ball4.setTranslateY(APP_H / 5 * 4);
        
        ball5.setTranslateX(APP_W / 10 * 4);
        ball5.setTranslateY(APP_H / 5);
        
        avatar.setTranslateX(APP_W / 20 * 1);
        avatar.setTranslateY(APP_H / 2);       

        timeline.play();
        ballTimeline1.play();
        ballTimeline2.play();
        ballTimeline3.play();
        ballTimeline4.play();
        ballTimeline5.play();
        checkpointTimeline.play();
        running = true;
    }

    public void start(Stage gameStage3) {
        playSound();
        stage = gameStage3;
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
        
        gameStage3.setTitle("Stage 3");
        gameStage3.setScene(scene);
        gameStage3.setResizable(false);
        gameStage3.show();
        startGame();
    }
    public void playSound(){
    String musicFile = "src\\Music\\Broke_For_Free_-_01_-_Night_Owl.mp3";

    Media sound = new Media(new File(musicFile).toURI().toString());
    mediaPlayer = new MediaPlayer(sound);        
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.play();        
    }
    public void stopSound(){
    mediaPlayer.stop();

    }
}

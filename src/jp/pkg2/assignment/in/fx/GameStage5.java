package jp.pkg2.assignment.in.fx;

import java.io.File;
import java.util.Optional;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
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
public class GameStage5 extends Application {
    
    private enum UserAction{
        NONE, LEFT, RIGHT, UP, DOWN;
    }
    private Stage stage;
    
    private MediaPlayer mediaPlayer;
    private ToggleButton playbtn;

    private static final int APP_W = 1200;
    private static final int APP_H = 800;
    
    private static final int BALL_RADIUS = 50;
    private static final int OBSTACLE_W = 50;
    private static final int OBSTACLE_H = 300;
    private static final int ROTATEO_W = 250;
    private static final int ROTATEO_H = 20;
    private static final int ROTATEO2_W = 30;
    private static final int ROTATEO2_H = 550;
    private static final int AVATAR_W = 50;
    private static final int AVATAR_H = 50;
    
    private Circle ball = new Circle(BALL_RADIUS);
    private Rectangle avatar = new Rectangle(AVATAR_W, AVATAR_H);
    private Rectangle obstacle1 = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle2 = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle3 = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle4 = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle obstacle5 = new Rectangle (OBSTACLE_W, OBSTACLE_H);
    private Rectangle rotateObstacle1 = new Rectangle (ROTATEO_W, ROTATEO_H);
    private Rectangle rotateObstacle2 = new Rectangle (ROTATEO_W, ROTATEO_H);
    private Rectangle bigRotateObstacle = new Rectangle (ROTATEO2_W, ROTATEO2_H);
    private Polygon checkpoint = new Polygon ();
    private Polygon timestoper = new Polygon ();
    private Rectangle buttonBarBackground = new Rectangle(1210, 60);
    private Rectangle wall = new Rectangle(APP_W / 20 * 17, 25);
    
    private DropShadow dropShadow = new DropShadow();
    private DropShadow dropShadow2 = new DropShadow();
    
    private RotateTransition rt1 = new RotateTransition(Duration.seconds(1.500), rotateObstacle1);
    private RotateTransition rt2 = new RotateTransition(Duration.seconds(1.500), rotateObstacle2);
    private RotateTransition rt3 = new RotateTransition(Duration.seconds(0.800), bigRotateObstacle);
    
    private boolean ballUp = true, ballLeft = false;
    private boolean obstacleUp1 = false;
    private boolean obstacleUp2 = false;
    private boolean obstacleUp3 = false;
    private boolean obstacleUp4 = false;
    private boolean obstacleUp5 = false;
    private UserAction action = UserAction.NONE;
    
    private Timeline timeline = new Timeline();
    private Timeline ballTimeline = new Timeline();
    private Timeline obstacleTimeline = new Timeline();
    private Timeline rotateObstacleTimeline = new Timeline();
    private Timeline checkpointTimeline = new Timeline();
    private boolean running = true;
    
    
    private Parent createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(APP_W, APP_H);                      
        
        playbtn = new ToggleButton();
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
        
        dropShadow2.setRadius(80.0);
        dropShadow2.setOffsetX(0);
        dropShadow2.setOffsetY(0);
        dropShadow2.setColor(Color.color(0.4, 0.4, 0.4));
        
        timestoper.getPoints().addAll(new Double[]{
            1120.0, 670.0,
            1135.0, 720.0,
            1180.0, 720.0,
            1142.5, 750.0,
            1155.0, 790.0,
            1120.0, 770.0,
            1085.0, 790.0,
            1092.5, 750.0,
            1060.0, 720.0,
            1105.0, 720.0
        });
        timestoper.setStroke(Color.WHITE);
        timestoper.setStrokeWidth(5);
        timestoper.setEffect(dropShadow2);
        timestoper.setFill(Color.BLACK);
        
        checkpoint.getPoints().addAll(new Double[]{
            1120.0, 270.0,
            1135.0, 320.0,
            1180.0, 320.0,
            1142.5, 350.0,
            1155.0, 390.0,
            1120.0, 370.0,
            1085.0, 390.0,
            1092.5, 350.0,
            1060.0, 320.0,
            1105.0, 320.0
        });
        checkpoint.setEffect(dropShadow);
        checkpoint.setFill(Color.ORANGE);
        
        wall.setTranslateX(APP_W / 20 * 3);
        wall.setTranslateY(APP_H - 180);
        
        
        obstacle1.setTranslateX(APP_W/ 20 * 12);
        obstacle1.setTranslateY(60);
        
        obstacle2.setTranslateX(obstacle1.getTranslateX() + OBSTACLE_W);
        obstacle2.setTranslateY(80);
        
        obstacle3.setTranslateX(obstacle2.getTranslateX() + OBSTACLE_W);
        obstacle3.setTranslateY(100);
        
        obstacle4.setTranslateX(obstacle3.getTranslateX() + OBSTACLE_W);
        obstacle4.setTranslateY(120);
        
        obstacle5.setTranslateX(obstacle4.getTranslateX() + OBSTACLE_W);
        obstacle5.setTranslateY(140);
             
        rotateObstacle1.setTranslateX(APP_W/ 10 * 3);
        rotateObstacle1.setTranslateY(wall.getTranslateY() + 40);
        rotateObstacle1.setArcHeight(50);
        rotateObstacle1.setArcWidth(50);       
        
        rotateObstacle2.setTranslateX(APP_W/ 10 * 4);
        rotateObstacle2.setTranslateY(wall.getTranslateY() + 40);
        rotateObstacle2.setArcHeight(50);
        rotateObstacle2.setArcWidth(50);      
        
        bigRotateObstacle.setTranslateX(APP_W/ 10 * 4);
        bigRotateObstacle.setTranslateY(APP_H - ROTATEO2_H - 180);
        bigRotateObstacle.setArcHeight(50);
        bigRotateObstacle.setArcWidth(50);  
        
        avatar.setTranslateX(APP_W / 20 * 1);
        avatar.setTranslateY(APP_H / 2);
        avatar.setArcHeight(20);
        avatar.setArcWidth(20);

        
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
        
        KeyFrame ballFrame = new KeyFrame(Duration.seconds(0.010), event ->{
            if(!running)
                return;
                        
            ball.setTranslateX(ball.getTranslateX() + (ballLeft ? -5 : 5));
            ball.setTranslateY(ball.getTranslateY() + (ballUp ? -5 : 5));
                        
            
            if (ball.getTranslateX() - BALL_RADIUS == 0)
                ballLeft = false;
            else if (ball.getTranslateX() + BALL_RADIUS == APP_W)
                ballLeft = true;
            if(ball.getTranslateY() - BALL_RADIUS == 60){
                FadeTransition ft = new FadeTransition(Duration.seconds(3.000), ball);
                    ft.setFromValue(1.0);
                    ft.setToValue(0.001);
                    ft.setCycleCount(Timeline.INDEFINITE);
                    ft.setAutoReverse(true);
                ScaleTransition st = new ScaleTransition(Duration.seconds(1.000), ball);
                    st.setToX(2f);
                    st.setToY(2f);
                    st.setCycleCount(Timeline.INDEFINITE);
                    st.setAutoReverse(true);
                ParallelTransition ps = new ParallelTransition();
                ps.getChildren().addAll(ft, st);
                ps.play();
        
                ballUp = false; 
            }
                            
            
            Shape collision = Shape.intersect(avatar, ball);
            if (collision.getBoundsInLocal().getWidth() != -1){               
                restartGame();
            }
            
            if (ball.getTranslateY() + BALL_RADIUS == APP_H)
                ballUp = true;
        });
        
        KeyFrame obstacleFrame1 = new KeyFrame(Duration.seconds(0.010),event ->{
            if(!running)
                return; 
            
            obstacle1.setTranslateX(obstacle1.getTranslateX());
            obstacle1.setTranslateY(obstacle1.getTranslateY() + (obstacleUp1 ? -5 : 5));
            
            if (obstacle1.getTranslateY() == 60)
                obstacleUp1 = false;
                
            if (obstacle1.getTranslateY() + OBSTACLE_H == wall.getTranslateY())
                obstacleUp1 = true;           

            Shape collision1 = Shape.intersect(avatar, obstacle1);
            if(collision1.getBoundsInLocal().getWidth() != -1)
                restartGame();                                          
        });
        
        KeyFrame obstacleFrame2 = new KeyFrame(Duration.seconds(0.010),event ->{
            if(!running)
                return; 
            
            obstacle2.setTranslateX(obstacle2.getTranslateX());
            obstacle2.setTranslateY(obstacle2.getTranslateY() + (obstacleUp2 ? -5 : 5));
                        
            if (obstacle2.getTranslateY() == 60)
                obstacleUp2 = false;
                
            if (obstacle2.getTranslateY() + OBSTACLE_H == wall.getTranslateY())
                obstacleUp2 = true;           

            Shape collision2 = Shape.intersect(avatar, obstacle2);
            if(collision2.getBoundsInLocal().getWidth() != -1)
                restartGame();                                          
        });
        
        KeyFrame obstacleFrame3 = new KeyFrame(Duration.seconds(0.010),event ->{
            if(!running)
                return; 
            
            obstacle3.setTranslateX(obstacle3.getTranslateX());
            obstacle3.setTranslateY(obstacle3.getTranslateY() + (obstacleUp3 ? -5 : 5));
                        
            if (obstacle3.getTranslateY() == 60)
                obstacleUp3 = false;
                
            if (obstacle3.getTranslateY() + OBSTACLE_H == wall.getTranslateY())
                obstacleUp3 = true;           

            Shape collision3 = Shape.intersect(avatar, obstacle3);
            if(collision3.getBoundsInLocal().getWidth() != -1)
                restartGame();                                          
        });
        
        KeyFrame obstacleFrame4 = new KeyFrame(Duration.seconds(0.010),event ->{
            if(!running)
                return; 
            
            obstacle4.setTranslateX(obstacle4.getTranslateX());
            obstacle4.setTranslateY(obstacle4.getTranslateY() + (obstacleUp4 ? -5 : 5));        
            
            if (obstacle4.getTranslateY() == 60)
                obstacleUp4 = false;
                
            if (obstacle4.getTranslateY() + OBSTACLE_H == wall.getTranslateY())
                obstacleUp4 = true;           

            Shape collision4 = Shape.intersect(avatar, obstacle4);
            if(collision4.getBoundsInLocal().getWidth() != -1)
                restartGame();                                          
        });
        
        KeyFrame obstacleFrame5 = new KeyFrame(Duration.seconds(0.010),event ->{
            if(!running)
                return; 
            
            obstacle5.setTranslateX(obstacle5.getTranslateX());
            obstacle5.setTranslateY(obstacle5.getTranslateY() + (obstacleUp5 ? -5 : 5));  
                        
            if (obstacle5.getTranslateY() == 60)
                obstacleUp5 = false;
                
            if (obstacle5.getTranslateY() + OBSTACLE_H == wall.getTranslateY())
                obstacleUp5 = true;           

            Shape collision5 = Shape.intersect(avatar, obstacle5);
            if(collision5.getBoundsInLocal().getWidth() != -1)
                restartGame();                                          
        });
        
        KeyFrame rotateObstaclesFrame = new KeyFrame(Duration.seconds(0.010),event ->{
            if(!running)
                return;                                   

            rotateObstacle1.setTranslateX(rotateObstacle1.getTranslateX());
            rotateObstacle1.setTranslateY(rotateObstacle1.getTranslateY());
            rotateObstacle2.setTranslateX(rotateObstacle2.getTranslateX());
            rotateObstacle2.setTranslateY(rotateObstacle2.getTranslateY());
            bigRotateObstacle.setTranslateX(bigRotateObstacle.getTranslateX());
            bigRotateObstacle.setTranslateY(bigRotateObstacle.getTranslateY());
            
            Shape collision6 = Shape.intersect(avatar, rotateObstacle1);
            if(collision6.getBoundsInLocal().getWidth() != -1)
                restartGame();
            Shape collision7 = Shape.intersect(avatar, rotateObstacle2);
            if(collision7.getBoundsInLocal().getWidth() != -1)
                restartGame(); 
            Shape collision8 = Shape.intersect(avatar, bigRotateObstacle);
            if(collision8.getBoundsInLocal().getWidth() != -1)
                restartGame(); 
            
            Shape collision9 = Shape.intersect(avatar, wall);
            if(collision9.getBoundsInLocal().getWidth() != -1){
                restartGame();
            }
        });
        
        KeyFrame checkpointFrame = new KeyFrame(Duration.seconds(0.010),event ->{
            if(!running)
                return; 
            
            checkpoint.setTranslateX(checkpoint.getTranslateX());
            checkpoint.setTranslateY(checkpoint.getTranslateY());

            

            Shape collision10 = Shape.intersect(avatar, checkpoint);
            if(collision10.getBoundsInLocal().getWidth() != -1){
                stopSound();
                stopGame();//prevent open more than one GameOverStage
                stage.close();
                GameOver go = new GameOver();
                Stage GameOverStage = new Stage();
                go.start(GameOverStage);
            }
            Shape collision11 = Shape.intersect(avatar, timestoper);
            if (collision11.getBoundsInLocal().getWidth() != -1){
                stopSound();
                playSound2();
                
                rt1.stop();
                rt2.stop();
                rt3.stop();
                
                ballTimeline.stop();
                ball.setFill(Color.TRANSPARENT);
                ball.setStroke(Color.BLACK);
                
                rotateObstacleTimeline.stop();
                rotateObstacle1.setFill(Color.TRANSPARENT);
                rotateObstacle1.setStroke(Color.BLACK);
                rotateObstacle2.setFill(Color.TRANSPARENT);
                rotateObstacle2.setStroke(Color.BLACK);
                bigRotateObstacle.setFill(Color.TRANSPARENT);
                bigRotateObstacle.setStroke(Color.BLACK);
                
                obstacleTimeline.stop();
                obstacle1.setFill(Color.TRANSPARENT);
                obstacle1.setStroke(Color.BLACK);
                obstacle2.setFill(Color.TRANSPARENT);
                obstacle2.setStroke(Color.BLACK);
                obstacle3.setFill(Color.TRANSPARENT);
                obstacle3.setStroke(Color.BLACK);
                obstacle4.setFill(Color.TRANSPARENT);
                obstacle4.setStroke(Color.BLACK);
                obstacle5.setFill(Color.TRANSPARENT);
                obstacle5.setStroke(Color.BLACK);
                
                avatar.setFill(Color.BLACK);
                
                wall.setFill(Color.TRANSPARENT);
                wall.setStroke(Color.BLACK);
                
                timestoper.setStroke(Color.TRANSPARENT);
                timestoper.setFill(Color.TRANSPARENT);
            }
           
        });               
              
        timeline.getKeyFrames().addAll(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        ballTimeline.getKeyFrames().add(ballFrame);
        ballTimeline.setCycleCount(Timeline.INDEFINITE);
        
        obstacleTimeline.getKeyFrames().addAll(obstacleFrame1, obstacleFrame2, 
                obstacleFrame3, obstacleFrame4, obstacleFrame5);
        obstacleTimeline.setCycleCount(Timeline.INDEFINITE);
        
        rotateObstacleTimeline.getKeyFrames().add(rotateObstaclesFrame);
        rotateObstacleTimeline.setCycleCount(Timeline.INDEFINITE);
        
        checkpointTimeline.getKeyFrames().add(checkpointFrame);
        checkpointTimeline.setCycleCount(Timeline.INDEFINITE);               
        
        HBox buttonBar = new HBox(playbtn, restartbtn, mainMenubtn, QuitBtn);
        buttonBar.setSpacing(15);
        buttonBar.setAlignment(Pos.CENTER);      
        root.getChildren().addAll(buttonBarBackground, ball, bigRotateObstacle, rotateObstacle1, 
                rotateObstacle2, obstacle1, obstacle2, obstacle3, obstacle4, 
                obstacle5, avatar, wall, timestoper, checkpoint);
        root.setTop(buttonBar);
        return root;
    }
    public void restartGame(){
        stopGame();
        startGame();
    }
    
    public void stopGame(){
        running = false;
        rt1.stop();
        rt2.stop();
        rt3.stop();
        timeline.stop();
        ballTimeline.stop();
        obstacleTimeline.stop();
        rotateObstacleTimeline.stop();
        checkpointTimeline.stop();
    }
    
    public void startGame(){
        
        rt1.setByAngle(-180);
        rt1.setCycleCount(Timeline.INDEFINITE);
        rt1.play();

        rt2.setByAngle(180);
        rt2.setCycleCount(Timeline.INDEFINITE);
        rt2.play();

        rt3.setByAngle(360);
        rt3.setCycleCount(Timeline.INDEFINITE);
        rt3.play();
        
        wall.setFill(Color.BROWN);
        wall.setStroke(Color.TRANSPARENT);
        
        ballUp = true;
        ball.setFill(Color.GREENYELLOW);
        ball.setStroke(Color.TRANSPARENT);
        ball.setTranslateX(APP_W / 2);
        ball.setTranslateY(APP_H / 2);
        
        avatar.setTranslateX(APP_W / 20 * 1);
        avatar.setTranslateY(APP_H / 2);
        avatar.setFill(Color.DARKGRAY);
        avatar.setStroke(Color.BLACK);
        
        rotateObstacle1.setFill(Color.DARKCYAN);
        rotateObstacle1.setStroke(Color.TRANSPARENT);
        rotateObstacle2.setFill(Color.DARKSEAGREEN);
        rotateObstacle2.setStroke(Color.TRANSPARENT);
        bigRotateObstacle.setFill(Color.LIGHTCORAL);
        bigRotateObstacle.setStroke(Color.TRANSPARENT);


        obstacle1.setFill(Color.rgb(15, 59, 115));
        obstacle1.setStroke(Color.TRANSPARENT);
        obstacle2.setFill(Color.rgb(49, 78, 115));
        obstacle2.setStroke(Color.TRANSPARENT);
        obstacle3.setFill(Color.rgb(25, 98, 191));
        obstacle3.setStroke(Color.TRANSPARENT);
        obstacle4.setFill(Color.rgb(31, 124, 242));
        obstacle4.setStroke(Color.TRANSPARENT);
        obstacle5.setFill(Color.rgb(105, 166, 244));
        obstacle5.setStroke(Color.TRANSPARENT);
        
        timestoper.setStroke(Color.WHITE);
        timestoper.setStrokeWidth(5);
        timestoper.setEffect(dropShadow2);
        timestoper.setFill(Color.BLACK);
        
        timeline.play();
        ballTimeline.play();
        obstacleTimeline.play();
        rotateObstacleTimeline.play();
        checkpointTimeline.play();
        running = true;
    }

    public void start(Stage gameStage5) {
        playSound();
        stage = gameStage5;
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
        gameStage5.setTitle("Stage 5");
        gameStage5.setScene(scene);
        gameStage5.setResizable(false);
        gameStage5.show();
        startGame();
    }
    public void playSound(){
      String musicFile = "src\\Music\\BoxCat_Games_-_10_-_Epic_Song.mp3";

      Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);        
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();        
    }
    
    public void playSound2(){
      String musicFile = "src\\Music\\The_Kyoto_Connection_-_09_-_Hachiko_The_Faithtful_Dog.mp3";

      Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);        
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        
    }    
    
    public void stopSound(){
        mediaPlayer.stop();
    }
}
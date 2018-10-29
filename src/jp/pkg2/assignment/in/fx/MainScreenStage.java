package jp.pkg2.assignment.in.fx;

import java.io.File;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import static javafx.scene.text.FontPosture.ITALIC;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainScreenStage extends Application {   
    

    private MediaPlayer mediaPlayer;
    
    public void start(Stage primaryStage){
        
        playSound();//play background music
        //create ToggleButton to control background music
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
                playbtn.setGraphic(pauseImage);//change the icon when clicked
                    stopSound();//stop bgm
            }
            else{
                playImage.setImage(soundImage2);
                playbtn.setGraphic(playImage);//change the icon when clicked
                    playSound();//play bgm
            }
        });    
        //Create a VBox to inset multiple button and arrange them vertically
        VBox vbox = new VBox();
            vbox.setPadding(new Insets(15, 12, 15, 12));
            vbox.setSpacing(15);
            vbox.setAlignment(Pos.CENTER);

        //Create Start Button    
        Button btn1 = new Button();
        btn1.setText("Start Game");
        btn1.setFont(Font.font("Lucida Console",ITALIC ,40));
        btn1.setPrefSize(300,80);
        btn1.setOnAction((ActionEvent ae) -> {
            stopSound();
            primaryStage.close();
            SelectStage ss = new SelectStage();
            ss.start(primaryStage);
            
       });
        btn1.setOnMouseEntered((MouseEvent me)->{
            btn1.setScaleX(1.5);
            btn1.setScaleY(1.5);
        });
        btn1.setOnMouseExited((MouseEvent me) -> {
        btn1.setScaleX(1);
        btn1.setScaleY(1);
        });

        //Create button link to tutorial
        Button btn2 = new Button();
        btn2.setText("Tutorial");
        btn2.setFont(Font.font("Lucida Console",ITALIC ,40));
        btn2.setPrefSize(300,80);
        btn2.setOnAction((ActionEvent ae) -> {
            stopSound();
            primaryStage.close();
            Tutorial tt = new Tutorial();
            tt.start(primaryStage);
            
       });
        btn2.setOnMouseEntered((MouseEvent me)->{
            btn2.setScaleX(1.5);
            btn2.setScaleY(1.5);
        });
        btn2.setOnMouseExited((MouseEvent me) -> {
        btn2.setScaleX(1);
        btn2.setScaleY(1);
        });
        
        //Create button to close the program
        Button btn3 = new Button();
        btn3.setText("Quit Game");
        btn3.setFont(Font.font("Lucida Console",ITALIC ,40));
        btn3.setPrefSize(300,80);
        btn3.setOnAction((ActionEvent ae) -> {
            Platform.exit();
            });
        btn3.setOnMouseEntered((MouseEvent me)->{
            btn3.setScaleX(1.5);
            btn3.setScaleY(1.5);
        });
        btn3.setOnMouseExited((MouseEvent me) -> {
        btn3.setScaleX(1);
        btn3.setScaleY(1);
        });               
        
        //add buttons to vbox
        vbox.getChildren().addAll(btn1, btn2, btn3);  
        //add soundbtn to hbox
        HBox soundbtn = new HBox(playbtn);
        soundbtn.setAlignment(Pos.TOP_LEFT);
        
        Text gameTitle = new Text("Shapes");
        gameTitle.setFont(Font.font("Forte",ITALIC ,100));
        
        BorderPane border = new BorderPane();
        border.setCenter(gameTitle);
        border.setBottom(vbox);
        border.setLeft(soundbtn);      
        
        Scene scene = new Scene(border, 1200, 800);
        
        primaryStage.setTitle("Shapes");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
//method to play sound
    private void playSound(){
        String musicFile = "src\\Music\\Jason_Shaw_-_RUNNING_WATERS.mp3";

    Media sound = new Media(new File(musicFile).toURI().toString());
    mediaPlayer = new MediaPlayer(sound);        
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();        
    }
    //method to stop sound
    private void stopSound(){
    mediaPlayer.stop();

    }
}

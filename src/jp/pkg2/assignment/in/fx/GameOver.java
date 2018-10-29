package jp.pkg2.assignment.in.fx;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import static javafx.scene.text.FontPosture.ITALIC;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.StageStyle;
import javafx.application.Platform;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameOver {
	public void start(Stage GameOverStage) {
            //Create a VBox to inset multiple Text and arrange them vertically
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(15, 12, 15, 12));
            vbox.setSpacing(10);
            vbox.setAlignment(Pos.CENTER);
            
            //Create DropShadow effect
            DropShadow ds = new DropShadow();
            ds.setOffsetY(3.0f);
            ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
            
            //Create "GAME OVER" Text
            Text endingText = new Text();
            endingText.setText("GAME OVER");
            endingText.setFont(Font.font("Broadway", ITALIC, 60));
            endingText.setFill(Color.DARKBLUE);
            endingText.setEffect(ds);//set DropShadow effect to this Text
            endingText.setX(10.0f);
            endingText.setY(270.0f);
            
            //Create "Thanks for playing" Text
            Text endingText2 = new Text();
            endingText2.setText("Thanks for playing");
            endingText2.setFont(Font.font("Bodoni MT", ITALIC, 40));
	
            //Create a HBox to insert multiple button and arrange them horizontally
            HBox hbox = new HBox();
            hbox.setPadding(new Insets(15, 12, 15, 12));
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);
            
            //Create Main Menu button
            Button MainMenuBtn = new Button();
            MainMenuBtn.setText("Main Menu");
            MainMenuBtn.setFont(Font.font("Lucida Console",ITALIC ,20));
            MainMenuBtn.setPrefWidth(165);
            MainMenuBtn.setOnAction((ActionEvent ae) -> {

            //Create a confirmation dialog
                Alert MainMenuAlert = new Alert(AlertType.CONFIRMATION);
                MainMenuAlert.initStyle(StageStyle.UTILITY);
                MainMenuAlert.setTitle("Confirmation");
                MainMenuAlert.setHeaderText("Do you want to back to Main Menu?");
                MainMenuAlert.setContentText("Click OK to confirm");

                Optional<ButtonType> result = MainMenuAlert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        GameOverStage.close();
                        MainScreenStage mss = new MainScreenStage();
                        mss.start(GameOverStage);
// if user chose OK this window will closed and Main Interface will opened                      
                    }

            });
            
            //Create button to close program
            Button QuitBtn = new Button();
            QuitBtn.setText("Quit Game");
            QuitBtn.setFont(Font.font("Lucida Console",ITALIC ,20));
            QuitBtn.setPrefWidth(165);
            QuitBtn.setOnAction((ActionEvent ae) -> {

            //Create a confirmation dialog
            Alert QuitAlert = new Alert(AlertType.CONFIRMATION);
            QuitAlert.initStyle(StageStyle.UTILITY);
            QuitAlert.setTitle("Confirmation");
            QuitAlert.setHeaderText("Do you want to quit game?");
            QuitAlert.setContentText("Click OK to confirm");

            Optional<ButtonType> result = QuitAlert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Platform.exit();
// if user chose OK this window will closed and Main Interface will opened
                }

            });

//Add endingText and endingText2 to vbox
        vbox.getChildren().addAll(endingText,endingText2);
//Add MainMenuBtn and QuitBtn to hbox        
        hbox.getChildren().addAll(MainMenuBtn,QuitBtn);
        BorderPane root = new BorderPane();
        root.setBottom(hbox);
        root.setCenter(vbox);
        Scene scene = new Scene(root, 600, 400);

	GameOverStage.setTitle("Game Over");
        GameOverStage.setScene(scene);
        GameOverStage.setResizable(false);
        GameOverStage.show();
	}
}

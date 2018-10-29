package jp.pkg2.assignment.in.fx;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import static javafx.scene.text.FontPosture.ITALIC;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Tutorial{
        private static final int APP_W = 1200;
    private static final int APP_H = 800;
    
    public void start(Stage tutorialStage) {
        
        VBox tutorials = new VBox();
            tutorials.setPadding(new Insets(15, 12, 15, 12));
            tutorials.setSpacing(10);
            tutorials.setAlignment(Pos.CENTER_LEFT);
        
            //Tutorial texts
        Text tutorial1 = new Text();
        tutorial1.setText("* Press w to move up");
        tutorial1.setFont(Font.font("Lucida Console",ITALIC ,40));
        Text tutorial2 = new Text();
        tutorial2.setText("* Press a to move left");
        tutorial2.setFont(Font.font("Lucida Console",ITALIC ,40));
        Text tutorial3 = new Text();
        tutorial3.setText("* Press s to move down");
        tutorial3.setFont(Font.font("Lucida Console",ITALIC ,40));
        Text tutorial4 = new Text();
        tutorial4.setText("* Press d to move right");
        tutorial4.setFont(Font.font("Lucida Console",ITALIC ,40));
        Text tutorial5 = new Text();
        tutorial5.setText("* Reach the YELLOW STAR shape to complete the stage");
        tutorial5.setFont(Font.font("Lucida Console",ITALIC ,35));
        Text tutorial6 = new Text();
        tutorial6.setText("* OTHER STAR shape might help you to clear the stage");
        tutorial6.setFont(Font.font("Lucida Console",ITALIC ,35));
//Add texts to vbox        
        tutorials.getChildren().addAll(tutorial1, tutorial2, tutorial3, 
                tutorial4, tutorial5, tutorial6);
        
//Create a HBox to inset multiple button and arrange them vertically
            HBox mainBtn = new HBox();
            mainBtn.setPadding(new Insets(15, 12, 15, 12));
            mainBtn.setSpacing(10);
            mainBtn.setAlignment(Pos.CENTER);
            
            Button MainMenuBtn = new Button();
            MainMenuBtn.setText("Main Menu");
            MainMenuBtn.setFont(Font.font("Lucida Console",ITALIC ,40));
            MainMenuBtn.setPrefSize(300,80);
            MainMenuBtn.setOnAction((ActionEvent ae) -> {

            Alert MainMenuAlert = new Alert(Alert.AlertType.CONFIRMATION);
            MainMenuAlert.initStyle(StageStyle.UTILITY);
            MainMenuAlert.setTitle("Confirmation");
            MainMenuAlert.setHeaderText("Do you want to back to Main Menu?");
            MainMenuAlert.setContentText("Click OK to confirm");

            Optional<ButtonType> result = MainMenuAlert.showAndWait();
                if (result.get() == ButtonType.OK){
                    tutorialStage.close();
                    MainScreenStage mss = new MainScreenStage();// ... user chose OK
                    mss.start(tutorialStage);
                } else {
                    // ... user chose CANCEL or closed the dialog
                }

            });

            Button QuitBtn = new Button();
            QuitBtn.setText("Quit Game");
            QuitBtn.setFont(Font.font("Lucida Console",ITALIC ,40));
            QuitBtn.setPrefSize(300,80);
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
        
        mainBtn.getChildren().addAll(MainMenuBtn,QuitBtn);
        
        BorderPane root = new BorderPane();
        root.setCenter(tutorials);
        root.setBottom(mainBtn);
        
        Scene scene = new Scene(root, APP_W, APP_H);
        
        tutorialStage.setTitle("Tutorial");
        tutorialStage.setScene(scene);
        tutorialStage.show();
    }
}

package jp.pkg2.assignment.in.fx;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import static javafx.scene.text.FontPosture.ITALIC;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author USER
 */
public class SelectStage{
    
    public void start(Stage selectionStage) {
        //Create button link to Stage 1
        Button Stage1Btn = new Button();
        Stage1Btn.setText("Stage 1");
        Stage1Btn.setFont(Font.font("Lucida Console",ITALIC ,25));
        Stage1Btn.setPrefSize(150,150);
        Stage1Btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                selectionStage.close();
                GameStage1 gs1= new GameStage1();
                Stage gameStage = new Stage();
                gs1.start(gameStage);
            }
        });
        
        //Create button link to Stage 2
        Button Stage2Btn = new Button();
        Stage2Btn.setText("Stage 2");
        Stage2Btn.setFont(Font.font("Lucida Console",ITALIC ,25));
        Stage2Btn.setPrefSize(150,150);
        Stage2Btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                selectionStage.close();
                GameStage2 gs2= new GameStage2();
                Stage gameStage2 = new Stage();
                gs2.start(gameStage2);
            }
        });
        
        //Create button link to Stage 3
        Button Stage3Btn = new Button();
        Stage3Btn.setText("Stage 3");
        Stage3Btn.setFont(Font.font("Lucida Console",ITALIC ,25));
        Stage3Btn.setPrefSize(150,150);
        Stage3Btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                selectionStage.close();
                GameStage3 gs3= new GameStage3();
                Stage gameStage3 = new Stage();
                gs3.start(gameStage3);
            }
        });
        
        //Create button link to Stage 4
        Button Stage4Btn = new Button();
        Stage4Btn.setText("Stage 4");
        Stage4Btn.setFont(Font.font("Lucida Console",ITALIC ,25));
        Stage4Btn.setPrefSize(150,150);
        Stage4Btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                selectionStage.close();
                GameStage4 gs4= new GameStage4();
                Stage gameStage4 = new Stage();
                gs4.start(gameStage4);
            }
        });
        
        //Create button link to Stage 5
        Button Stage5Btn = new Button();
        Stage5Btn.setText("Stage 5");
        Stage5Btn.setFont(Font.font("Lucida Console",ITALIC ,25));
        Stage5Btn.setPrefSize(150,150);
        Stage5Btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                selectionStage.close();
                GameStage5 gs5= new GameStage5();
                Stage gameStage5 = new Stage();
                gs5.start(gameStage5);
            }
        });
         //Create a HBox to insert multiple button and arrange them horizontally
            HBox mainBtn = new HBox();
            mainBtn.setPadding(new Insets(15, 12, 15, 12));
            mainBtn.setSpacing(10);
            mainBtn.setAlignment(Pos.CENTER);
            
            //Create Main Menu button
            Button MainMenuBtn = new Button();
            MainMenuBtn.setText("Main Menu");
            MainMenuBtn.setFont(Font.font("Lucida Console",ITALIC ,40));
            MainMenuBtn.setPrefSize(300,80);
            MainMenuBtn.setOnAction((ActionEvent ae) -> {

            //Create a confirmation dialog
            Alert MainMenuAlert = new Alert(Alert.AlertType.CONFIRMATION);
            MainMenuAlert.initStyle(StageStyle.UTILITY);
            MainMenuAlert.setTitle("Confirmation");
            MainMenuAlert.setHeaderText("Do you want to back to Main Menu?");
            MainMenuAlert.setContentText("Click OK to confirm");

            Optional<ButtonType> result = MainMenuAlert.showAndWait();
                if (result.get() == ButtonType.OK){
                    selectionStage.close();
                    MainScreenStage mss = new MainScreenStage();
                    mss.start(selectionStage);
// if user chose OK this window will closed and Main Interface will opened
                } 
            });

            //Create button to close program
            Button QuitBtn = new Button();
            QuitBtn.setText("Quit Game");
            QuitBtn.setFont(Font.font("Lucida Console",ITALIC ,40));
            QuitBtn.setPrefSize(300,80);
            QuitBtn.setOnAction((ActionEvent ae) -> {

            //Create a confirmation dialog
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
        //Create HBox to add button
        HBox selectionBtns = new HBox();
        selectionBtns.setSpacing(10);
        selectionBtns.setAlignment(Pos.CENTER);
        selectionBtns.getChildren().addAll(Stage1Btn, Stage2Btn, Stage3Btn, Stage4Btn, Stage5Btn);
        mainBtn.getChildren().addAll(MainMenuBtn,QuitBtn);
        
        BorderPane root = new BorderPane();
        root.setCenter(selectionBtns);
        root.setBottom(mainBtn);
        
        Scene scene = new Scene(root, 1200, 800);
        
        selectionStage.setTitle("Shapes");
        selectionStage.setScene(scene);
        selectionStage.show();
    }
}

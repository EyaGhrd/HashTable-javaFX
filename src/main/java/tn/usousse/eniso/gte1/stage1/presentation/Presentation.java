package tn.usousse.eniso.gte1.stage1.presentation;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.usousse.eniso.gte1.stage1.presentation.controller.AppController;

import tn.usousse.eniso.gte1.stage1.presentation.model.Table;
import tn.usousse.eniso.gte1.stage1.presentation.view.HashTableOfComponents;

import tn.usousse.eniso.gte1.stage1.service.AppService;

import java.util.Optional;


public class Presentation extends Application {
    AppController controller=new AppController(new AppService(new Table(100))) ;
    HashTableOfComponents tableModel=new HashTableOfComponents(controller);
    BorderPane rootPane = new BorderPane();
    private Scene scene=new Scene(rootPane,800,600);

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("https://d1nhio0ox7pgb.cloudfront.net/_img/o_collection_png/green_dark_grey/512x512/plain/symbol_hash.png"));
        MenuBar menubar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        MenuItem menuSize = new MenuItem("Size");
        MenuItem menuAdd = new MenuItem("Add");
        MenuItem menuClear = new MenuItem("Clear");
        MenuItem about = new MenuItem("About");
        helpMenu.getItems().add(about);
        fileMenu.getItems().addAll(menuSize, menuAdd, menuClear);
        menubar.getMenus().addAll(fileMenu, helpMenu);
        rootPane.setTop(menubar);

        menuSize.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Text Input Size");
            dialog.setContentText("Please enter the Size:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String input = result.get();
                try {
                    int size = Integer.parseInt(input);
                    controller.resetController();
                    controller.setSize(size);
                    Pane tablePane = tableModel.drawTable();
                    tableModel.animateTableCreation();
                    rootPane.setCenter(tablePane);
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Size");
                    alert.setHeaderText(null);
                    alert.setContentText("The value entered is not a valid size. Please enter a numeric value.");
                    alert.showAndWait();
                }
            }
        });
        menuAdd.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Add String");
            dialog.setContentText("Please enter a string:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String inputString = result.get();
                if (inputString.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Empty String");
                    alert.setHeaderText(null);
                    alert.setContentText("The string cannot be empty. Please enter a valid string.");
                    alert.showAndWait();
                } else {
                    tableModel.setIndex(controller.getService().hachF(inputString));
                    controller.add(inputString);
                    Pane tablePane = tableModel.drawTable();
                    rootPane.setCenter(tablePane);
                    controller.getService().list();
                }
            }
        });

        menuClear.setOnAction(e -> {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText("Are you sure you want to clear?");
            confirmAlert.setContentText("Click OK to confirm.");

            confirmAlert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    controller.resetController();
                    rootPane.getChildren().clear();
                    rootPane.setTop(menubar);
                }
            });
        });

        Pane tablePane = tableModel.drawTable();
        rootPane.setCenter(tablePane);
        primaryStage.setTitle("Hash Table by EYA");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

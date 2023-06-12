package tn.usousse.eniso.gte1.stage1.presentation;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.usousse.eniso.gte1.stage1.presentation.controller.AppController;

import tn.usousse.eniso.gte1.stage1.presentation.model.Table;
import tn.usousse.eniso.gte1.stage1.presentation.view.HashTableOfComponents;

import tn.usousse.eniso.gte1.stage1.service.AppService;

import java.util.Optional;

public class Presentation extends Application {
    AppController controller=new AppController(new AppService(new Table(20))) ;
    HashTableOfComponents tableModel=new HashTableOfComponents(controller);

    BorderPane rootPane = new BorderPane();

    private Scene scene=new Scene(rootPane,800,600);

    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuBar menubar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        MenuItem menuSize = new MenuItem("Size");
        MenuItem menuAdd = new MenuItem("Add");
        MenuItem menuExit = new MenuItem("Exit");
        MenuItem about = new MenuItem("About");
        helpMenu.getItems().add(about);
        fileMenu.getItems().addAll(menuSize, menuAdd, menuExit);
        menubar.getMenus().addAll(fileMenu, helpMenu);
        rootPane.setTop(menubar);

        menuSize.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Text Input Size");
            dialog.setContentText("Please enter the Size:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int size = Integer.parseInt(result.get());
                    controller.setSize(size);
                    System.out.println(controller.getSize());
                    Pane tablePane = tableModel.drawTable();
                    rootPane.setCenter(tablePane);

                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
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
                controller.add(inputString);
                Pane tablePane = tableModel.drawTable();
                rootPane.setCenter(tablePane);
                controller.getService().list();
            }
        });

        Pane tablePane = tableModel.drawTable();
        rootPane.setCenter(tablePane);
        primaryStage.setTitle("Hash Table");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

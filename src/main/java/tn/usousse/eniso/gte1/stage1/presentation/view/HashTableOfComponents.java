package tn.usousse.eniso.gte1.stage1.presentation.view;

import javafx.animation.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import javafx.scene.text.Text;
import javafx.util.Duration;
import tn.usousse.eniso.gte1.stage1.presentation.controller.AppController;
import tn.usousse.eniso.gte1.stage1.presentation.model.Node;
import tn.usousse.eniso.gte1.stage1.presentation.model.Table;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class HashTableOfComponents {
    private  AppController controller;
    private  Pane tablePane;
    private int index;

    public void setController(AppController controller) {
        this.controller = controller;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public HashTableOfComponents(AppController controller) {
        this.controller = controller;
        tablePane = new Pane();
        tablePane.setOnMouseClicked(this::handleMouseClick);
    }
    private void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int caseSize = 100;
            int startX = (int) ((tablePane.getWidth() - caseSize) / 2);
            int startY = (int) ((tablePane.getHeight() - (caseSize * controller.getSize())) / 2);
            int nodeIndex = (y - startY) / caseSize;
            Node node = controller.getModel().getNodes()[nodeIndex];
            if (node != null) {
                int r = startX;
                while (node != null) {
                    int nodeX = r + caseSize + 60;
                    int nodeY = startY + (caseSize * nodeIndex) + caseSize -11;
                    if (x >= nodeX && x <= nodeX + caseSize - 20 && y >= nodeY - caseSize - 30 && y <= nodeY - 40) {
                        Button confirmButton = new Button("Delete");
                        confirmButton.setLayoutX(nodeX);
                        confirmButton.setLayoutY(nodeY);
                        Node finalNode1 = node;
                        confirmButton.setOnAction(buttonEvent -> {
                            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmAlert.setTitle("Confirmation");
                            confirmAlert.setHeaderText("Are you sure you want to delete this node?");
                            confirmAlert.setContentText("Click OK to confirm.");
                            Node finalNode = finalNode1;

                            confirmAlert.showAndWait().ifPresent(result -> {
                                if (result == ButtonType.OK) {
                                    boolean removed = controller.getService().remove(finalNode.getValue());
                                    if (removed) {
                                        drawTableAfterRemove(nodeIndex);
                                    } else {
                                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                        errorAlert.setTitle("Error");
                                        errorAlert.setHeaderText(null);
                                        errorAlert.setContentText("Node not found.");
                                        errorAlert.showAndWait();
                                    }
                                }
                                tablePane.getChildren().remove(confirmButton);
                            });
                        });

                        tablePane.getChildren().add(confirmButton);
                        break;
                    }
                    r += caseSize + 30;
                    node = node.getNext();
                }
            }
        }
    }
    public void animateTableCreation() {
        tablePane.getChildren().clear();
        Table table = controller.getModel();
        int size = controller.getSize();
        table.setSize(size);
        System.out.println("table size : " + size);
        int caseSize = 100;
        int tableHeight = caseSize * size;
        Node[] nodes = table.getNodes();
        int startX = (int) ((tablePane.getWidth() - caseSize) / 2);
        int startY = (int) ((tablePane.getHeight() - tableHeight) / 2);
        int x = startX;
        int y = startY;

       Timeline timeline = new Timeline();

        for (int i = 0; i <= controller.getSize(); i++) {
            Line line = new Line(startX, y, startX + caseSize, y);
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(3);

            Rectangle rectangle = new Rectangle(startX, y, caseSize, caseSize);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(3);

            Circle dot = new Circle(startX - 10, y + caseSize / 2, 5);
            dot.setFill(Color.GREEN);
            dot.setVisible(false);

            KeyFrame lineFrame = new KeyFrame(Duration.seconds(0.4 * (i + 1)), e -> tablePane.getChildren().add(line));
            KeyFrame rectangleFrame = new KeyFrame(Duration.seconds(0.4 * (i + 1)), e -> {
                tablePane.getChildren().addAll(rectangle, dot);
                rectangle.setOnMouseEntered(event -> dot.setVisible(true));
                rectangle.setOnMouseExited(event -> dot.setVisible(false));
                StrokeTransition strokeTransition = new StrokeTransition(Duration.seconds(0.5), rectangle);
                strokeTransition.setFromValue(Color.BLACK);
                strokeTransition.setToValue(Color.GREEN);
                strokeTransition.setCycleCount(2);
                strokeTransition.setAutoReverse(true);
                strokeTransition.play();
            });
            timeline.getKeyFrames().addAll(lineFrame, rectangleFrame);
            Node n = nodes[i];
            int r = startX;
            while (n != null) {
                Node finalN = n;
                KeyFrame nodeFrame = new KeyFrame(Duration.seconds(0.4 * (i + 1)), e -> {
                });
                timeline.getKeyFrames().add(nodeFrame);
                r += caseSize + 30;
                n = n.getNext();
            }
            y = startY + i * caseSize;
        }
        timeline.play();
    }

    private void animateNodePosition(Rectangle nodeRectangle) {
        FillTransition fillTransition = new FillTransition(Duration.seconds(0.4), nodeRectangle);
        fillTransition.setToValue(Color.ORANGE);
        fillTransition.setAutoReverse(true);
        fillTransition.setCycleCount(2);
        fillTransition.setOnFinished(event -> nodeRectangle.setFill(Color.TRANSPARENT));
        fillTransition.play();
    }

    public Pane drawTable() {
        tablePane.getChildren().clear();
        Table table = controller.getModel();
        int size = controller.getSize();
        table.setSize(size);
        System.out.println("table size : " + size);
        int caseSize = 100;
        int tableHeight = caseSize * size;
        Node[] nodes = table.getNodes();
        int startX = (int) ((tablePane.getWidth() - caseSize) / 2);
        int startY = (int) ((tablePane.getHeight() - tableHeight) / 2);
        int x = startX;
        int y = startY;
        for (int i = 0; i < controller.getSize(); i++) {
            Line line = new Line(startX, y, startX + caseSize, y);
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(3);
            tablePane.getChildren().add(line);
            y = startY + i * caseSize;
            Rectangle rectangle = new Rectangle(startX, y, caseSize, caseSize);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(3);
            tablePane.getChildren().addAll(rectangle);
            Node n = nodes[i];
            int r = startX;
            int z = caseSize;
           if(i<=index && nodes[index]!=null){
            Text text = new Text(String.valueOf(i));
            text.setX(startX - 50);
            text.setY(y + 50);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds((i + 1)), event -> {
                        rectangle.setFill(Color.GREEN);
                        tablePane.getChildren().add(text);
                    }));
            timeline.play();}
            int j=0;
            while (n != null) {
                Rectangle rectangleNode=drawNode(n, tablePane, caseSize, r, startY);
                r += caseSize + 30;
                j+=1;
                if(index==i){
                    Rectangle rect=rectangleNode;
                    Node finalN = n;
                    Timeline timelineNode = new Timeline(
                            new KeyFrame(Duration.seconds((i+j+1)), event -> {
                                animateLastNode(rect);
                            }));
                    timelineNode.play();
                }
                if (n.getNext() == null) {
                    n.setLast(false);
                    Node finalN1 = n;
                    int finalR = r;
                    if (index==i){
                    Timeline timelineNode = new Timeline(
                            new KeyFrame(Duration.seconds((i+j+2)), event -> {
                                drawMass(finalN1, tablePane, caseSize, finalR, startY);
                            }));
                    timelineNode.play();}
                    else {drawMass(finalN1, tablePane, caseSize, finalR, startY);
                    }
                }
                n = n.getNext();
            }
        }
        return tablePane;
    }
    private void animateLastNode(Rectangle nodeRectangle) {
        FillTransition fillTransition = new FillTransition(Duration.seconds(0.4), nodeRectangle);
            fillTransition.setToValue(Color.RED);
            fillTransition.setAutoReverse(true);
            fillTransition.setCycleCount(2);
            fillTransition.setOnFinished(event -> nodeRectangle.setFill(Color.WHITE));
        fillTransition.play();
    }

    private Rectangle drawNode(Node node, Pane tablePane, int caseSize, int startX, int startY) {
        int i = controller.getService().hachF(node.getValue());
        double lineY = startY + caseSize * i + 50;
        Line line = new Line(startX + caseSize, lineY, startX + caseSize + 50, lineY);
        tablePane.getChildren().add(line);
        double rectY = lineY + caseSize;
        Rectangle rectangle = new Rectangle(startX + caseSize + 50, rectY - caseSize - 30, caseSize - 20, caseSize - 40);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        tablePane.getChildren().add(rectangle);
        Text text = new Text(startX + caseSize + 75, lineY, node.getValue());
        tablePane.getChildren().add(text);
        Line line1 = new Line(startX + caseSize * 2 + 30, lineY, startX + caseSize * 2 + 80, lineY);
        tablePane.getChildren().add(line1);
        return rectangle;
    }

    private void drawMass(Node node, Pane tablePane, double caseSize, double startX, double startY) {
        int i = controller.getService().hachF(node.getValue());
        double lineY = startY + caseSize * i + 50;
        double lineX = startX + caseSize * 2 - 50;
        Line line1 = new Line(lineX, lineY + 20, lineX, lineY - 20);
        tablePane.getChildren().add(line1);
        Line line2 = new Line(lineX, lineY + 20, lineX + 10, lineY + 10);
        tablePane.getChildren().add(line2);
        Line line3 = new Line(lineX, lineY + 10, lineX + 10, lineY);
        tablePane.getChildren().add(line3);
        Line line4 = new Line(lineX, lineY, lineX + 10, lineY - 10);
        tablePane.getChildren().add(line4);
        Line line5 = new Line(lineX, lineY - 10, lineX + 10, lineY - 20);
        tablePane.getChildren().add(line5);
        Line line6 = new Line(lineX, lineY - 20, lineX + 10, lineY - 30);
        tablePane.getChildren().add(line6);
    }

    public Pane drawTableAfterRemove(int index) {
        tablePane.getChildren().clear();
        Table table = controller.getModel();
        int size = controller.getSize();
        table.setSize(size);
        System.out.println("table size : " + size);
        int caseSize = 100;
        int tableHeight = caseSize * size;
        Node[] nodes = table.getNodes();
        int startX = (int) ((tablePane.getWidth() - caseSize) / 2);
        int startY = (int) ((tablePane.getHeight() - tableHeight) / 2);
        int x = startX;
        int y = startY;
        for (int i = 0; i < controller.getSize(); i++) {
            Line line = new Line(startX, y, startX + caseSize, y);
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(3);
            tablePane.getChildren().add(line);
            y = startY + i * caseSize;
            Rectangle rectangle = new Rectangle(startX, y, caseSize, caseSize);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(3);
            tablePane.getChildren().addAll(rectangle);
            if(i==index){ animateNodePosition(rectangle);}
            Node n = nodes[i];
            int r = startX;
           // int z = caseSize;
            while (n != null) {
                Rectangle rectangleNode=drawNode(n, tablePane, caseSize, r, startY);
                r += caseSize + 30;
                if (n.getNext() == null) {
                    drawMass(n, tablePane, caseSize, r, startY);
                }
                n = n.getNext();
            }
        }
        return tablePane;
    }
}
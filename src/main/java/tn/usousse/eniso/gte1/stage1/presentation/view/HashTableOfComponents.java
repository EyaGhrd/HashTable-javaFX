package tn.usousse.eniso.gte1.stage1.presentation.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import tn.usousse.eniso.gte1.stage1.presentation.controller.AppController;
import tn.usousse.eniso.gte1.stage1.presentation.model.Node;
import tn.usousse.eniso.gte1.stage1.presentation.model.Table;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class HashTableOfComponents {
    private AppController controller;
    private Table tableModel;
    private Pane tablePane;

    public HashTableOfComponents(AppController controller) {
        this.controller = controller;
        tablePane = new Pane();
        tablePane.setOnMouseClicked(this::handleMouseClick);
    }
    public void setModel(Table tableModel) {
        this.tableModel = tableModel;
        drawTable();
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
                    int nodeX = r + caseSize + 50;
                    int nodeY = startY + (caseSize * nodeIndex) + caseSize + 20;
                    if (x >= nodeX && x <= nodeX + caseSize - 20 && y >= nodeY - caseSize - 30 && y <= nodeY - 40) {
                        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmAlert.setTitle("Confirmation");
                        confirmAlert.setHeaderText("Are you sure you want to delete this node?");
                        confirmAlert.setContentText("Click OK to confirm.");
                        Node finalNode = node;
                        confirmAlert.showAndWait().ifPresent(result -> {
                            if (result == ButtonType.OK) {
                                boolean removed = controller.getService().remove(finalNode.getValue());
                                if (removed) {
                                    drawTable();
                                } else {
                                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                    errorAlert.setTitle("Error");
                                    errorAlert.setHeaderText(null);
                                    errorAlert.setContentText("Node not found.");
                                    errorAlert.showAndWait();
                                }
                            }
                        });
                        break;
                    }
                    r += caseSize + 30;
                    node = node.getNext();
                }
            }
        }
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
            Circle dot = new Circle(startX - 10, y + caseSize / 2, 5);
            dot.setFill(Color.GREEN);
            dot.setVisible(false);
            rectangle.setOnMouseEntered(event -> dot.setVisible(true));
            rectangle.setOnMouseExited(event -> dot.setVisible(false));
            tablePane.getChildren().addAll(rectangle, dot);
            Node n = nodes[i];
            int r = startX;
            int z = caseSize;
            while (n != null) {
                drawNode(n, tablePane, caseSize, r, startY);
                r += caseSize + 30;
                if (n.getNext() == null) {
                    drawMass(n, tablePane, caseSize, r, startY);
                }
                n = n.getNext();
            }
        }
        return tablePane;
    }



    private void drawNode(Node node, Pane tablePane, int caseSize, int startX, int startY) {
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
}


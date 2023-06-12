package tn.usousse.eniso.gte1.stage1.presentation.model;

import java.util.Arrays;

public class Table {
    private Node[] nodes;
    private int size;

    public Table(int size) {
        this.nodes =new Node[size];
    }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Table" + Arrays.toString(nodes) ;
    }

}

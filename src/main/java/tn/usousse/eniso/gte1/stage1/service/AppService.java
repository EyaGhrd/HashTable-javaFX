package tn.usousse.eniso.gte1.stage1.service;

import tn.usousse.eniso.gte1.stage1.presentation.model.Node;
import tn.usousse.eniso.gte1.stage1.presentation.model.Table;

public class AppService {
    Table table;

    public Table getTable() {
        return table;
    }

    public AppService(Table table) {
        this.table = table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public int hachF(String value) {
        int s = 0;
        if (value != null) {
            for (char ch : value.toCharArray()) {
                s = s + (int) ch;
            }
            s = s % 3;
        } else {
            s = 0;
        }
        return s;
    }

    public boolean find(String value) {
        int index = hachF(value);
        boolean test = false;
        if (table.getNodes().length <= index) {
            return false;
        } else {
            Node currentNode = table.getNodes()[index];
            while (
                    currentNode != null) {
                if (currentNode.getValue().equals(value)) {
                    test = true;
                    break;
                }
                currentNode = currentNode.getNext();
            }
        }
        return test;
    }
    public boolean add(String v) {
        int index = hachF(v);
        Node n = new Node(v, null);
        Node list = table.getNodes()[index];
        Node nc;
        nc = list;

        if (!(find(v))) {
            if (list == null) {
                list = n;
            } else {
                while (nc.next != null) {
                    nc = nc.next;
                }
                nc.next = n;
            }
            table.getNodes()[index] = list;

            return true;
        } else {
            return false;
        }

    }
    public boolean remove(String value) {
        int index = hachF(value);
        if (table.getNodes()[index] == null) {
            return false;
        }
        Node current = table.getNodes()[index];
        Node previous = null;
        if (current.getNext() == null && current.getValue().equals(value)) {
            table.getNodes()[index] = null;
            return true;
        }
        while (current != null) {
            if (current.getValue().equals(value)) {
                if (previous != null) {
                    previous.next = current.getNext();
                } else {
                    table.getNodes()[index] = current.getNext();
                }
                return true;
            }
            previous = current;
            current = current.getNext();
        }
        return false;
    }

    public void list() {
        Node nodeTab = null;
        Node currentNode = table.getNodes()[0];
        String s = "";
        for (int i = 0; i < table.getNodes().length; i++) {
            s = "Node " + i + ":" + "\n";
            nodeTab = table.getNodes()[i];
            if (nodeTab != null) {
                currentNode = nodeTab;
                while (currentNode.getNext() != null) {
                    s = s + currentNode.getValue() + " ";
                    currentNode = currentNode.getNext();
                }
                s = s + currentNode.getValue();
                System.out.println(s);
            }
        }
    }
    @Override
    public String toString() {
        return table.toString();
    }
}

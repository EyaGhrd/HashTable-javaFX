package tn.usousse.eniso.gte1.stage1.presentation.model;
public class Node {
        private String value;
        public Node next;

        public Node(String value,Node next) {
            this.value=value;
            this.next = next;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value='" + value + '\'' +
                    ", next=" + next +
                    '}';
        }
    }
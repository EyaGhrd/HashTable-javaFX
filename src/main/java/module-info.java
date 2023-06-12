module tn.usousse.eniso.gte1.stage1 {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires jdk.accessibility;

    opens tn.usousse.eniso.gte1.stage1 to javafx.fxml;
    exports tn.usousse.eniso.gte1.stage1;
    exports tn.usousse.eniso.gte1.stage1.presentation;
    opens tn.usousse.eniso.gte1.stage1.presentation to javafx.fxml;
}

module teams.reconnaissance {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires opencv;
    requires javafx.swing;
    exports teams.reconnaissance.controller;
    opens teams.reconnaissance to javafx.fxml;
    opens teams.reconnaissance.controller to javafx.fxml; 
    exports teams.reconnaissance;
}
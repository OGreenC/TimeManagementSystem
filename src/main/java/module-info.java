module dtu.timeManagement.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens dtu.timeManagement.app to javafx.fxml;
    exports dtu.timeManagement.app;
    exports dtu.timeManagement.app.presentationLayer;
    opens dtu.timeManagement.app.presentationLayer to javafx.fxml;
}
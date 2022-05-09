module dtu.timeManagement.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens dtu.timeManagement.app to javafx.fxml;
    exports dtu.timeManagement.app;
    exports dtu.timeManagement.app.presentationLayer;
    opens dtu.timeManagement.app.presentationLayer to javafx.fxml;
    exports dtu.timeManagement.app.Exceptions;
    opens dtu.timeManagement.app.Exceptions to javafx.fxml;
    exports dtu.timeManagement.app.domains;
    opens dtu.timeManagement.app.domains to javafx.fxml;
    exports dtu.timeManagement.app.domains.timeRegistration;
    opens dtu.timeManagement.app.domains.timeRegistration to javafx.fxml;
}
module ouiam.cruduser {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens Presentation to javafx.fxml;
    exports Presentation;
    exports database;
    exports Model;
    exports Traitement;
}

package Controller;

/**
 * Created by Julio Savigny on 2/9/2016.
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class NandetechCekController implements Initializable {

    @FXML
    private TextField Nama_alat;

    @FXML
    private DatePicker ButtonDate;

    @FXML
    private Button ButtonCek;

    @FXML
    private TableView<ResultRow> TableCek;

    @FXML
    private TableColumn<ResultRow, String> TableID;

    @FXML
    private TableColumn<ResultRow, String> TableName;

    @FXML
    private TableColumn<ResultRow, String> TableStatus;

    @FXML
    private Label notFound;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        TableCek.setVisible(false);
        CekKetersediaan check = new CekKetersediaan();
        final Timestamp tanggalPinjam;
        ButtonDate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDate date = ButtonDate.getValue();
            }
        });

        ButtonCek.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (ButtonDate.getValue()!=null) {
                    ArrayList<ResultRow> tableOut = check.cek(Nama_alat.getText(), Timestamp.valueOf(ButtonDate.getValue().atStartOfDay()));
                    if (!tableOut.isEmpty()) {
                        TableCek.setVisible(true);
                        ObservableList<ResultRow> listBuffer = FXCollections.observableArrayList(tableOut);
                        TableID.setCellValueFactory(new PropertyValueFactory<ResultRow, String>("id_alat"));
                        TableName.setCellValueFactory(new PropertyValueFactory<ResultRow, String>("nama_alat"));
                        TableStatus.setCellValueFactory(new PropertyValueFactory<ResultRow, String>("availability"));
                        TableCek.setItems(listBuffer);
                    } else {
                        notFound.setVisible(true);
                    }
                }
            }
        });

    }

}

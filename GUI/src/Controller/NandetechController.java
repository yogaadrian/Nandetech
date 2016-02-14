package Controller;

/**
 * Created by Julio Savigny on 2/9/2016.
 */
import Model.Ketersediaan.CekKetersediaan;
import Model.Ketersediaan.ResultRow;
import Model.Perbaikan.Perbaikan;
import javafx.beans.property.SimpleObjectProperty;
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
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;

public class NandetechController implements Initializable {

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

    @FXML
    private TextField searchIDField;

    @FXML
    private TableView<String> tablePerbaikan;

    @FXML
    private TableColumn<String, String> kolomIDPerbaikan;

    @FXML
    private TableColumn<String, String> kolomNamaPerbaikan;

    @FXML
    private TableColumn<String, String> kolomKondisiPerbaikan;

    @FXML
    private Button searchButtonID;

    @FXML
    private Button buttonPerbaiki;

    @FXML
    private Button buttonSelesaiPerbaiki;

    @FXML
    private Label labelKondisiID;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        CekKetersediaan check = new CekKetersediaan();
        Perbaikan perbaikan = new Perbaikan();
        TableCek.setVisible(false);
        buttonPerbaiki.setDisable(true);
        buttonSelesaiPerbaiki.setDisable(true);
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

        searchButtonID.setOnAction(event -> {
            ArrayList<String> status;
            int N;
            try {
                N = Integer.parseInt(searchIDField.getText());
            } catch (NumberFormatException ex){
                N=0;
            }
            status=perbaikan.tampilkanPerbaikan(N);
            if (!status.isEmpty()){
                tablePerbaikan.setVisible(true);
                ObservableList<String> listBuffer = FXCollections.observableArrayList(Arrays.asList("Supaya arraylist nya size 1"));
                kolomIDPerbaikan.setCellValueFactory(x->new SimpleObjectProperty<String>(status.get(0)));
                kolomNamaPerbaikan.setCellValueFactory(x->new SimpleObjectProperty<String>(status.get(1)));
                kolomKondisiPerbaikan.setCellValueFactory(x->new SimpleObjectProperty<String>(status.get(3)));
                tablePerbaikan.setItems(listBuffer);
                if(status.get(3).equalsIgnoreCase("TIDAK RUSAK")){

                }
            }
        });
    }

}

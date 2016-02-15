package Controller;

/**
 * Created by Julio Savigny on 2/9/2016.
 */
import Model.Ketersediaan.CekKetersediaan;
import Model.Ketersediaan.ResultRow;
import Model.Perbaikan.Perbaikan;
import Model.Perbaikan.RowPerbaikan;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Stream;

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
    private TableView<RowPerbaikan> tablePerbaikan;

    @FXML
    private TableColumn<RowPerbaikan, String> kolomIDPerbaikan;

    @FXML
    private TableColumn<RowPerbaikan, String> kolomNamaPerbaikan;

    @FXML
    private TableColumn<RowPerbaikan, String> kolomKondisiPerbaikan;

    @FXML
    private Button searchButtonID;

    @FXML
    private Button buttonPerbaiki;

    @FXML
    private Button buttonSelesaiPerbaiki;

    @FXML
    private Label labelKondisiID;

    @FXML
    private ComboBox<ArrayList<String>> choiceID;

    @FXML
    private TextField peminjaman_search_field;

    @FXML
    private ComboBox<?> peminjaman_combo_search;

    @FXML
    private Button peminjaman_search_button;

    @FXML
    private Button peminjaman_add_button;

    @FXML
    private TableView<?> peminjaman_table;

    @FXML
    private TableColumn<?, ?> peminjaman_kolom_idPeminjaman;

    @FXML
    private TableColumn<?, ?> peminjaman_kolom_idAlat;

    @FXML
    private TableColumn<?, ?> peminjaman_kolom_idPeminjam;

    @FXML
    private TableColumn<?, ?> peminjaman_kolom_Peminjaman;

    @FXML
    private TableColumn<?, ?> peminjaman_kolom_Pengembalian;

    @FXML
    private TableColumn<?, ?> peminjaman_kolom_cancel;

    @FXML
    private BarChart<?, ?> statistik_chart_penggunaan;

    @FXML
    private TextField statistik_nama_alat;

    @FXML
    private Button statistik_search_nama;

    @FXML
    private BarChart<?, ?> statistik_chart_penggunaankelompok;

    @FXML
    private TextField statistik_golongan;

    @FXML
    private Button statistik_search_golongan;

    @FXML
    private ComboBox<?> statistik_choice_ID;

    @FXML
    private BarChart<?, ?> statistik_chart_perbaikan;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        CekKetersediaan check = new CekKetersediaan();
        Perbaikan perbaikan = new Perbaikan();
        TableCek.setVisible(false);
        buttonPerbaiki.setDisable(true);
        buttonSelesaiPerbaiki.setDisable(true);
        final Timestamp tanggalPinjam;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ArrayList<String>> listAlat = perbaikan.semuaAlat();
                ArrayList<String> viewableAlat = new ArrayList<String>();
                for (int i=0;i<listAlat.size();i++){
                    viewableAlat.add(i,listAlat.get(i).get(0)+" | "+listAlat.get(i).get(1));
                    listAlat.get(i).remove(2);
                    listAlat.get(i).remove(2);
                }
                ObservableList<ArrayList<String>> observableAlat = FXCollections.observableArrayList(listAlat);
                choiceID.getItems().clear();
                choiceID.setItems(observableAlat);
            }
        }).start();
        /* CEK KETERSEDIAAN */
        ButtonDate.setOnAction(event -> {
            LocalDate date = ButtonDate.getValue();
        });

        ButtonCek.setOnAction(event -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
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
                            TableCek.setVisible(false);
                            notFound.setVisible(true);
                        }
                    }
                }
            }).start();

        });

        /* PERBAIKAN */
        searchButtonID.setOnAction(event -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<String> status;
                    int N;
                    /*try {
                        N = Integer.parseInt(searchIDField.getText());
                    } catch (NumberFormatException ex){
                        N=0;
                    }*/
                    N = Integer.parseInt(choiceID.getValue().get(0));
                    System.out.println(N);
                    status=perbaikan.tampilkanPerbaikan(N);
                    if (!status.isEmpty()){
                        tablePerbaikan.setVisible(true);
                        ArrayList<RowPerbaikan> aRowPerbaikan = new ArrayList<RowPerbaikan>();
                        aRowPerbaikan.add(0,new RowPerbaikan(status.get(0),status.get(1),status.get(3)));
                        ObservableList<RowPerbaikan> listBuffer = FXCollections.observableArrayList(aRowPerbaikan);
                        kolomIDPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("idPerbaikan"));
                        kolomNamaPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("namaPerbaikan"));
                        kolomKondisiPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("kondisiPerbaikan"));
                        tablePerbaikan.setItems(listBuffer);
                        /*ObservableList<String> listBuffer = FXCollections.observableArrayList(Arrays.asList("Supaya arraylist nya size 1"));
                        kolomIDPerbaikan.setCellValueFactory(x->new SimpleObjectProperty<String>(status.get(0)));
                        kolomNamaPerbaikan.setCellValueFactory(x->new SimpleObjectProperty<String>(status.get(1)));
                        kolomKondisiPerbaikan.setCellValueFactory(x->new SimpleObjectProperty<String>(status.get(3)));
                        System.out.println(status.get(3));
                        tablePerbaikan.setItems(listBuffer);*/
                        if(status.get(3).equalsIgnoreCase("TIDAK RUSAK")){
                            buttonPerbaiki.setDisable(false);
                            buttonSelesaiPerbaiki.setDisable(true);
                        } else if (status.get(3).equalsIgnoreCase("RUSAK")) {
                            buttonPerbaiki.setDisable(true);
                            buttonSelesaiPerbaiki.setDisable(false);
                        }
                    }
                }
            }).start();

            buttonPerbaiki.setOnAction(event1 -> {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> status;
                        int N = Integer.parseInt(kolomIDPerbaikan.getCellObservableValue(0).getValue());
                        perbaikan.mulaiPerbaikan(N);
                        status=perbaikan.tampilkanPerbaikan(N);
                        if (!status.isEmpty()){
                            tablePerbaikan.setVisible(true);
                            ArrayList<RowPerbaikan> aRowPerbaikan = new ArrayList<RowPerbaikan>();

                            aRowPerbaikan.add(0,new RowPerbaikan(status.get(0),status.get(1),status.get(3)));

                            ObservableList<RowPerbaikan> listBuffer = FXCollections.observableArrayList(aRowPerbaikan);
                            kolomIDPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("idPerbaikan"));
                            kolomNamaPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("namaPerbaikan"));
                            kolomKondisiPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("kondisiPerbaikan"));
                            tablePerbaikan.setItems(listBuffer);
                        tablePerbaikan.setItems(listBuffer);
                            if(status.get(3).equalsIgnoreCase("TIDAK RUSAK")){
                                buttonPerbaiki.setDisable(false);
                                buttonSelesaiPerbaiki.setDisable(true);
                            } else if (status.get(3).equalsIgnoreCase("RUSAK")) {
                                buttonPerbaiki.setDisable(true);
                                buttonSelesaiPerbaiki.setDisable(false);
                            }
                        }
                    }
                }).start();
            });

        });
    }

}

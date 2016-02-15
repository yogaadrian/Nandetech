package Controller;

/**
 * Created by Julio Savigny on 2/9/2016.
 */
import Model.Ketersediaan.CekKetersediaan;
import Model.Ketersediaan.ResultRow;
import Model.Peminjaman.Peminjaman;
import Model.Peminjaman.RowPeminjaman;
import Model.Perbaikan.Perbaikan;
import Model.Perbaikan.RowPerbaikan;
import com.sun.rowset.internal.Row;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
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
    private ComboBox<String> peminjaman_combo_search;

    @FXML
    private Button peminjaman_search_button;

    @FXML
    private Button peminjaman_add_button;

    @FXML
    private TableView<RowPeminjaman> peminjaman_table;

    @FXML
    private TableColumn<RowPeminjaman, String> peminjaman_kolom_idPeminjaman;

    @FXML
    private TableColumn<RowPeminjaman, String> peminjaman_kolom_idAlat;

    @FXML
    private TableColumn<RowPeminjaman, String> peminjaman_kolom_idPeminjam;

    @FXML
    private TableColumn<RowPeminjaman, String> peminjaman_kolom_Peminjaman;

    @FXML
    private TableColumn<RowPeminjaman, String> peminjaman_kolom_Pengembalian;

    @FXML
    private TableColumn<RowPeminjaman, String> peminjaman_kolom_deskripsi;

    @FXML
    private TableColumn<RowPeminjaman, Button> peminjaman_kolom_cancel;

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
        Peminjaman peminjaman = new Peminjaman();
        TableCek.setVisible(false);
        buttonPerbaiki.setDisable(true);
        buttonSelesaiPerbaiki.setDisable(true);
        final Timestamp tanggalPinjam;
        peminjaman_combo_search.getItems().add(0,"ID Peminjaman");
        peminjaman_combo_search.getItems().add(0,"ID Alat");
        peminjaman_combo_search.getItems().add(0,"ID Peminjam");

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

        Nama_alat.setOnAction(event->{
            ButtonCek.setDefaultButton(true);
        });

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

        choiceID.setOnAction(event->{
            searchButtonID.setDefaultButton(true);
        });

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

        buttonSelesaiPerbaiki.setOnAction(event1 -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<String> status;
                    int N = Integer.parseInt(kolomIDPerbaikan.getCellObservableValue(0).getValue());
                    perbaikan.selesaiPerbaikan(N);
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

        /* Peminjaman */
        peminjaman_search_button.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int pilihan;
                    int N;
                    if (peminjaman_combo_search.getValue().equalsIgnoreCase("ID Peminjaman")){
                        pilihan=1;
                    } else if (peminjaman_combo_search.getValue().equalsIgnoreCase("ID User")){
                        pilihan=2;
                    } else if (peminjaman_combo_search.getValue().equalsIgnoreCase("ID Alat")){
                        pilihan=3;
                    } else {
                        pilihan=2;
                    }
                    try {
                        N = Integer.parseInt(peminjaman_search_field.getText());
                    } catch (Exception e){
                        N = 0;
                    }
                    ArrayList<ArrayList<String>> tabelPeminjamanBuffer = new ArrayList<ArrayList<String>>();
                    tabelPeminjamanBuffer = peminjaman.tampilkanPeminjaman(N,pilihan);
                    ArrayList<RowPeminjaman> tabelPeminjaman = new ArrayList<RowPeminjaman>();
                    System.out.println(tabelPeminjamanBuffer.size());
                    for (int i=1;i<tabelPeminjamanBuffer.size();i++ ){
                        tabelPeminjaman.add(new RowPeminjaman(Integer.parseInt(tabelPeminjamanBuffer.get(i).get(0)),
                                Integer.parseInt(tabelPeminjamanBuffer.get(i).get(6)),
                                tabelPeminjamanBuffer.get(i).get(1),
                                Timestamp.valueOf(tabelPeminjamanBuffer.get(i).get(3)),
                                Timestamp.valueOf(tabelPeminjamanBuffer.get(i).get(4)),
                                tabelPeminjamanBuffer.get(i).get(2)));
                    }
                    for (int i=0;i<tabelPeminjamanBuffer.size();i++){
                        for (int j=0;j<tabelPeminjamanBuffer.get(i).size();j++){
                            System.out.println(tabelPeminjamanBuffer.get(i).get(j));
                        }
                    }
                    if (!tabelPeminjaman.isEmpty()) {
                        peminjaman_table.setVisible(true);
                        ObservableList<RowPeminjaman> listBuffer = FXCollections.observableArrayList(tabelPeminjaman);
                        peminjaman_kolom_idPeminjaman.setCellValueFactory(new PropertyValueFactory<RowPeminjaman, String>("idPeminjaman"));
                        peminjaman_kolom_idAlat.setCellValueFactory(new PropertyValueFactory<RowPeminjaman, String>("idAlat"));
                        peminjaman_kolom_idPeminjam.setCellValueFactory(new PropertyValueFactory<RowPeminjaman, String>("idPeminjam"));
                        peminjaman_kolom_Peminjaman.setCellValueFactory(new PropertyValueFactory<RowPeminjaman, String>("tanggalPeminjaman"));
                        peminjaman_kolom_Pengembalian.setCellValueFactory(new PropertyValueFactory<RowPeminjaman, String>("tanggalPengembalian"));
                        peminjaman_kolom_deskripsi.setCellValueFactory(new PropertyValueFactory<RowPeminjaman,String>("deskripsi"));
                        peminjaman_table.setItems(listBuffer);
                    } else {
                        peminjaman_table.setVisible(true);
                    }
                }
            }).start();
        });
    }

}

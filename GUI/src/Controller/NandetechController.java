package Controller;

/**
 * Created by Julio Savigny on 2/9/2016.
 */
import Model.Ketersediaan.CekKetersediaan;
import Model.Ketersediaan.ResultRow;
import Model.Peminjaman.Peminjaman;
import Model.Peminjaman.RowAlat;
import Model.Peminjaman.RowPeminjaman;
import Model.Perbaikan.Perbaikan;
import Model.Perbaikan.RowPerbaikan;
import Model.Statistik.Statistik;
import com.sun.javafx.scene.control.skin.LabeledText;
import com.sun.rowset.internal.Row;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

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
    private Label notFound1;

    @FXML
    private Label notFound2;

    @FXML
    private Label notFound3;

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

    //Peminjaman
    @FXML
    private TextField peminjaman_search_field;

    @FXML
    private ComboBox<String> peminjaman_combo_search;

    @FXML
    private Button peminjaman_search_button;

    @FXML
    private Button peminjaman_add_button;

    @FXML
    private Button peminjaman_delete_button;

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
    private TableColumn<RowPeminjaman, String> peminjaman_kolom_namaUser;

    @FXML
    private Button booking_add_button;

    @FXML
    private Button booking_cancel_button;

    @FXML
    private TextField booking_idPeminjam_field;

    @FXML
    private DatePicker booking_tanggalPeminjaman;

    @FXML
    private DatePicker booking_tanggalPengembalian;

    @FXML
    private TextField booking_deskripsi_field;

    @FXML
    private Text title;

    @FXML
    private Text text_1;

    @FXML
    private Text text_2;

    @FXML
    private Text text_3;

    @FXML
    private Text text_4;

    @FXML
    private TableView<RowAlat> booking_alat_table;

    @FXML
    private TableColumn<RowAlat, Integer> booking_kolom_id;

    @FXML
    private TableColumn<RowAlat, String> booking_kolom_nama;

    @FXML
    private TableColumn<RowAlat, Boolean> booking_kolom_checkBox;

    @FXML
    private BarChart<String, Integer> statistik_chart_penggunaan;

    @FXML
    private Text validator;

    @FXML
    private Button validator_yes;

    @FXML
    private Button validator_no;

    //Statistik
    @FXML
    private TextField statistik_nama_alat;

    @FXML
    private Button statistik_search_nama;

    @FXML
    private BarChart<String, Integer> statistik_chart_penggunaankelompok;

    @FXML
    private TextField statistik_golongan;

    @FXML
    private Button statistik_search_golongan;

    @FXML
    private ComboBox<ArrayList<String>> statistik_choice_ID;

    @FXML
    private BarChart<String, Integer> statistik_chart_perbaikan;

    @FXML
    private Button searchStatistikPerbaikan;

    //Alat
    @FXML
    private Button alat_add_button;

    @FXML
    private Button alat_delete_button;

    @FXML
    private Button alat_update_button;

    @FXML
    private TableView<RowAlat> alat_table;

    @FXML
    private TableColumn<RowAlat, Integer> alat_kolom_id;

    @FXML
    private TableColumn<RowAlat, String> alat_kolom_nama;

    @FXML
    private TableColumn<RowAlat, String> alat_kolom_lokasi;

    @FXML
    private TableColumn<RowAlat, String> alat_kolom_kondisi;

    @FXML
    private TextField alat_search_field;

    @FXML
    private ComboBox<String> alat_combo_search;

    @FXML
    private Button alat_search_button;

    @FXML
    private ComboBox<String> alat_combo_update;

    @FXML
    private Group alat_group_1;

    @FXML
    private Button alat_proceed_button;

    @FXML
    private Button alat_cancel_button;

    @FXML
    private TextField alat_id;

    @FXML
    private TextField alat_nama;

    @FXML
    private TextField alat_lokasi;

    @FXML
    private TextField alat_kondisi;




    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        CekKetersediaan check = new CekKetersediaan();
        Perbaikan perbaikan = new Perbaikan();
        Statistik statistik = new Statistik();
        Peminjaman peminjaman = new Peminjaman();
        TableCek.setVisible(false);
        buttonPerbaiki.setDisable(true);
        buttonSelesaiPerbaiki.setDisable(true);
        statistik_chart_penggunaan.setVisible(false);
        statistik_chart_penggunaankelompok.setVisible(false);
        statistik_chart_perbaikan.setVisible(false);

        final Timestamp tanggalPinjam;
        peminjaman_combo_search.getItems().add(0,"ID Peminjaman");
        peminjaman_combo_search.getItems().add(0, "ID Alat");
        peminjaman_combo_search.getItems().add(0, "ID Peminjam");

        alat_combo_search.getItems().add("ID");
        alat_combo_search.getItems().add("Nama");
        alat_combo_search.getItems().add("Lokasi");
        alat_combo_search.getItems().add("Kondisi");

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ArrayList<String>> listAlat = perbaikan.semuaAlat();
                ArrayList<String> viewableAlat = new ArrayList<String>();
                for (int i = 0; i < listAlat.size(); i++) {
                    alat_combo_update.getItems().add(listAlat.get(i).get(0) + "-" + listAlat.get(i).get(1));
                    viewableAlat.add(i, listAlat.get(i).get(0) + " | " + listAlat.get(i).get(1));
                    listAlat.get(i).remove(2);
                    listAlat.get(i).remove(2);
                }
                ObservableList<ArrayList<String>> observableAlat = FXCollections.observableArrayList(listAlat);
                choiceID.getItems().clear();
                choiceID.setItems(observableAlat);
                statistik_choice_ID.getItems().clear();
                statistik_choice_ID.setItems(observableAlat);
            }
        }).start();
        /* CEK KETERSEDIAAN */

        Nama_alat.setOnAction(event -> {
            ButtonCek.setDefaultButton(true);
        });

        ButtonDate.setOnAction(event -> {
            LocalDate date = ButtonDate.getValue();
        });

        ButtonCek.setOnAction(event -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ButtonDate.getValue() != null) {
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

        choiceID.setOnAction(event -> {
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
                    status = perbaikan.tampilkanPerbaikan(N);
                    if (!status.isEmpty()) {
                        tablePerbaikan.setVisible(true);
                        ArrayList<RowPerbaikan> aRowPerbaikan = new ArrayList<RowPerbaikan>();
                        aRowPerbaikan.add(0, new RowPerbaikan(status.get(0), status.get(1), status.get(3)));
                        ObservableList<RowPerbaikan> listBuffer = FXCollections.observableArrayList(aRowPerbaikan);
                        kolomIDPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("idPerbaikan"));
                        kolomNamaPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("namaPerbaikan"));
                        kolomKondisiPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("kondisiPerbaikan"));
                        tablePerbaikan.setItems(listBuffer);
                        if (status.get(3).equalsIgnoreCase("TIDAK RUSAK")) {
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
                    status = perbaikan.tampilkanPerbaikan(N);
                    if (!status.isEmpty()) {
                        tablePerbaikan.setVisible(true);
                        ArrayList<RowPerbaikan> aRowPerbaikan = new ArrayList<RowPerbaikan>();
                        aRowPerbaikan.add(0, new RowPerbaikan(status.get(0), status.get(1), status.get(3)));
                        ObservableList<RowPerbaikan> listBuffer = FXCollections.observableArrayList(aRowPerbaikan);
                        kolomIDPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("idPerbaikan"));
                        kolomNamaPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("namaPerbaikan"));
                        kolomKondisiPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("kondisiPerbaikan"));
                        tablePerbaikan.setItems(listBuffer);
                        tablePerbaikan.setItems(listBuffer);
                        if (status.get(3).equalsIgnoreCase("TIDAK RUSAK")) {
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
                    status = perbaikan.tampilkanPerbaikan(N);
                    if (!status.isEmpty()) {
                        tablePerbaikan.setVisible(true);
                        ArrayList<RowPerbaikan> aRowPerbaikan = new ArrayList<RowPerbaikan>();
                        aRowPerbaikan.add(0, new RowPerbaikan(status.get(0), status.get(1), status.get(3)));
                        ObservableList<RowPerbaikan> listBuffer = FXCollections.observableArrayList(aRowPerbaikan);
                        kolomIDPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("idPerbaikan"));
                        kolomNamaPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("namaPerbaikan"));
                        kolomKondisiPerbaikan.setCellValueFactory(new PropertyValueFactory<RowPerbaikan, String>("kondisiPerbaikan"));
                        tablePerbaikan.setItems(listBuffer);
                        tablePerbaikan.setItems(listBuffer);
                        if (status.get(3).equalsIgnoreCase("TIDAK RUSAK")) {
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
        peminjaman_search_button.setDefaultButton(true);
        peminjaman_search_button.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    peminjaman_delete_button.setDisable(false);
                    int pilihan;
                    String N;
                    if (peminjaman_combo_search.getValue().equalsIgnoreCase("ID Peminjaman")){
                        pilihan=1;
                    } else if (peminjaman_combo_search.getValue().equalsIgnoreCase("ID User")){
                        pilihan=2;
                    } else if (peminjaman_combo_search.getValue().equalsIgnoreCase("ID Alat")){
                        pilihan=3;
                    } else {
                        pilihan=2;
                    }
                    if(!peminjaman_search_field.getText().isEmpty()){
                        N = peminjaman_search_field.getText();
                    } else {
                        N = "1 OR 1=1";
                    }
                    ArrayList<ArrayList<String>> tabelPeminjamanBuffer = new ArrayList<ArrayList<String>>();
                    tabelPeminjamanBuffer = peminjaman.tampilkanPeminjaman(N,pilihan);
                    ArrayList<RowPeminjaman> tabelPeminjaman = new ArrayList<RowPeminjaman>();
                    System.out.println(tabelPeminjamanBuffer.size());
                    for (int i=0;i<tabelPeminjamanBuffer.size();i++ ){
                        tabelPeminjaman.add(new RowPeminjaman(Integer.parseInt(tabelPeminjamanBuffer.get(i).get(0)),
                                Integer.parseInt(tabelPeminjamanBuffer.get(i).get(6)),
                                tabelPeminjamanBuffer.get(i).get(1),
                                Timestamp.valueOf(tabelPeminjamanBuffer.get(i).get(3)),
                                Timestamp.valueOf(tabelPeminjamanBuffer.get(i).get(4)),
                                tabelPeminjamanBuffer.get(i).get(2),
                                tabelPeminjamanBuffer.get(i).get(8)));
                    }
                    for (int i=0;i<tabelPeminjamanBuffer.size();i++){
                        for (int j=0;j<tabelPeminjamanBuffer.get(i).size();j++){
                            System.out.print(tabelPeminjamanBuffer.get(i).get(j) + "#");
                        }
                        System.out.println();
                    }

                    peminjaman_table.setVisible(true);
                    ObservableList<RowPeminjaman> listBuffer = FXCollections.observableArrayList(tabelPeminjaman);
                    peminjaman_kolom_idPeminjaman.setCellValueFactory(new PropertyValueFactory<RowPeminjaman, String>("idPeminjaman"));
                    peminjaman_kolom_idAlat.setCellValueFactory(new PropertyValueFactory<RowPeminjaman, String>("idAlat"));
                    peminjaman_kolom_idPeminjam.setCellValueFactory(new PropertyValueFactory<RowPeminjaman, String>("idPeminjam"));
                    peminjaman_kolom_Peminjaman.setCellValueFactory(new PropertyValueFactory<RowPeminjaman, String>("tanggalPeminjaman"));
                    peminjaman_kolom_Pengembalian.setCellValueFactory(new PropertyValueFactory<RowPeminjaman, String>("tanggalPengembalian"));
                    peminjaman_kolom_deskripsi.setCellValueFactory(new PropertyValueFactory<RowPeminjaman,String>("deskripsi"));
                    peminjaman_kolom_namaUser.setCellValueFactory(new PropertyValueFactory<RowPeminjaman, String>("namaUser"));
                    peminjaman_table.setItems(listBuffer);
                }
            }).start();
        });

        peminjaman_add_button.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    peminjaman_search_field.setVisible(false);
                    peminjaman_combo_search.setVisible(false);
                    peminjaman_search_button.setVisible(false);
                    peminjaman_add_button.setVisible(false);
                    peminjaman_delete_button.setVisible(false);
                    peminjaman_table.setVisible(false);

                    title.setVisible(true);
                    text_1.setVisible(true);
                    text_2.setVisible(true);
                    text_3.setVisible(true);
                    text_4.setVisible(true);

                    booking_add_button.setVisible(true);
                    booking_cancel_button.setVisible(true);
                    booking_idPeminjam_field.setVisible(true);
                    booking_tanggalPeminjaman.setVisible(true);
                    booking_tanggalPengembalian.setVisible(true);
                    booking_deskripsi_field.setVisible(true);

                    ArrayList<ArrayList<String>> tabelBookingBuffer = new ArrayList<ArrayList<String>>();
                    tabelBookingBuffer = peminjaman.semuaAlat();
                    ArrayList<RowAlat> tabelBooking = new ArrayList<RowAlat>();
                    System.out.println(tabelBookingBuffer.size());
                    for (int i=0;i<tabelBookingBuffer.size();i++ ){
                        tabelBooking.add(new RowAlat(
                                        Integer.parseInt(tabelBookingBuffer.get(i).get(0)),
                                        tabelBookingBuffer.get(i).get(1),
                                        false)
                        );
                    }
                    for (int i=0;i<tabelBookingBuffer.size();i++){
                        for (int j=0;j<tabelBookingBuffer.get(i).size();j++){
                            System.out.print(tabelBookingBuffer.get(i).get(j) + "#");
                        }
                        System.out.println();
                    }
                    if (!tabelBooking.isEmpty()) {

                        booking_alat_table.setVisible(true);
                        booking_alat_table.setEditable(true);
                        booking_kolom_checkBox.setEditable(true);
                        ObservableList<RowAlat> listBuffer = FXCollections.observableArrayList(tabelBooking);

                        booking_alat_table.setItems(listBuffer);
                        booking_kolom_id.setCellValueFactory(new PropertyValueFactory<RowAlat, Integer>("id_alat"));
                        booking_kolom_nama.setCellValueFactory(new PropertyValueFactory<RowAlat, String>("nama_alat"));
                        booking_kolom_checkBox.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RowAlat, Boolean>, ObservableValue<Boolean>>() {
                            @Override
                            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Model.Peminjaman.RowAlat, Boolean> param) {
                                return param.getValue().isChecked();
                            }
                        });
                        booking_kolom_checkBox.setCellFactory(CheckBoxTableCell.forTableColumn(booking_kolom_checkBox));

                    } else {
                        booking_alat_table.setVisible(true);
                    }
                    booking_alat_table.setVisible(true);

                }
            }).start();
        });

        peminjaman_delete_button.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    peminjaman_add_button.setVisible(false);
                    peminjaman_delete_button.setVisible(false);
                    RowPeminjaman selected = peminjaman_table.getSelectionModel().getSelectedItem();
                    int id_peminjaman = selected.getIdPeminjaman();
                    validator.setText("Are you sure you want to delete Peminjaman with id=" + id_peminjaman + "?");
                    validator.setVisible(true);
                    validator_no.setVisible(true);
                    validator_yes.setVisible(true);
                }
            }).start();
        });

        validator_no.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    peminjaman_add_button.setVisible(true);
                    peminjaman_delete_button.setVisible(true);
                    validator.setText("");
                    validator.setVisible(false);
                    validator_no.setVisible(false);
                    validator_yes.setVisible(false);
                }
            }).start();
        });

        validator_yes.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    peminjaman_add_button.setVisible(true);
                    peminjaman_delete_button.setVisible(true);
                    String id = validator.getText().replaceAll("[^\\d.]", "");
                    System.out.println("id: " + id);
                    int id_peminjaman = Integer.parseInt(id);
                    validator.setText("");
                    validator.setVisible(false);
                    validator_no.setVisible(false);
                    validator_yes.setVisible(false);
                    peminjaman.cancelPeminjaman(id_peminjaman);
                }
            }).start();
        });

        booking_add_button.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int [] alat;
                    int count=0;
                    for (int i=0;i<booking_alat_table.getItems().size();i++){
                        if(booking_alat_table.getItems().get(i).isChecked().getValue()){
                            count++;
                        }
                    }
                    alat = new int[count];
                    int j=0;
                    for (int i=0;i<booking_alat_table.getItems().size();i++){
                        if(booking_alat_table.getItems().get(i).isChecked().getValue()){
                            alat[j]=booking_alat_table.getItems().get(i).getId_alat();
                            j++;
                        }
                    }

                    peminjaman.addPeminjaman(alat,
                            booking_idPeminjam_field.getText(),
                            Timestamp.valueOf(booking_tanggalPeminjaman.getValue().atStartOfDay()).toString(),
                            Timestamp.valueOf(booking_tanggalPengembalian.getValue().atStartOfDay()).toString(),
                            booking_deskripsi_field.getText()
                            );

                    title.setVisible(false);
                    booking_add_button.setVisible(false);
                    booking_cancel_button.setVisible(false);
                    booking_idPeminjam_field.setVisible(false);
                    booking_tanggalPeminjaman.setVisible(false);
                    booking_tanggalPengembalian.setVisible(false);
                    booking_deskripsi_field.setVisible(false);
                    booking_alat_table.setVisible(false);

                    text_1.setVisible(false);
                    text_2.setVisible(false);
                    text_3.setVisible(false);
                    text_4.setVisible(false);

                    peminjaman_search_field.setVisible(true);
                    peminjaman_combo_search.setVisible(true);
                    peminjaman_search_button.setVisible(true);
                    peminjaman_add_button.setVisible(true);
                    peminjaman_delete_button.setVisible(true);
                    peminjaman_table.setVisible(true);

                }
            }).start();
        });

        booking_cancel_button.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    booking_add_button.setVisible(false);
                    booking_cancel_button.setVisible(false);
                    booking_idPeminjam_field.setVisible(false);
                    booking_tanggalPeminjaman.setVisible(false);
                    booking_tanggalPengembalian.setVisible(false);
                    booking_deskripsi_field.setVisible(false);
                    booking_alat_table.setVisible(false);

                    title.setVisible(false);
                    text_1.setVisible(false);
                    text_2.setVisible(false);
                    text_3.setVisible(false);
                    text_4.setVisible(false);

                    peminjaman_search_field.setVisible(true);
                    peminjaman_combo_search.setVisible(true);
                    peminjaman_search_button.setVisible(true);
                    peminjaman_add_button.setVisible(true);
                    peminjaman_delete_button.setVisible(true);
                    peminjaman_table.setVisible(true);
                }
            }).start();
        });


        /*STATISTIK */
        statistik_choice_ID.setOnAction(event -> {
            searchStatistikPerbaikan.setDefaultButton(true);
        });
        statistik_golongan.setOnAction(event->{
            statistik_search_golongan.setDefaultButton(true);
        });
        statistik_nama_alat.setOnAction(event->{
            statistik_search_nama.setDefaultButton(true);
        });
        //Perbaikan Alat
        searchStatistikPerbaikan.setOnAction(event -> {
            statistik_chart_perbaikan.getData().removeAll();//BELUM BERFUNGSI
            statistik_chart_perbaikan.getData().clear();

            ArrayList<String> status;
            int N;
            N = Integer.parseInt(statistik_choice_ID.getValue().get(0));
            //System.out.println(N);

            try {
                status = statistik.ShowStatistikPerbaikanAlat(N);
                if (!status.isEmpty()) {
                    notFound3.setVisible(false);
                    for (int i = 0; i < status.size() / 3; i++) {
                        XYChart.Series<String, Integer> series = new XYChart.Series<>();
                        System.out.println(i);
                        Integer y = Integer.parseInt(status.get((i * 3) + 2));
                        String x = "";
                        x = x.concat(status.get(i * 3));
                        x = x.concat(" / ");
                        x = x.concat(status.get((i * 3) + 1));
                        series.getData().add(new XYChart.Data<>("", y));
                        series.setName(x);
                        statistik_chart_perbaikan.getData().add(series);
                    }
                    statistik_chart_perbaikan.setVisible(true);
                } else {
                    statistik_chart_perbaikan.setVisible(false);
                    notFound3.setVisible(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        //Penggunaan nama alat
        statistik_search_nama.setOnAction(event -> {

            statistik_chart_penggunaan.getData().clear();
    //System.out.println(N);
            try {

                ArrayList<String> status;
                String N;
                N = statistik_nama_alat.getText();
                N = N.toLowerCase();
                status = statistik.ShowStatistikPenggunaanAlat(N);
                if (!status.isEmpty()) {
                    notFound1.setVisible(false);
                    for (int i = 0; i < status.size() / 4; i++) {
                        XYChart.Series<String, Integer> series = new XYChart.Series<>();
                        System.out.println(i);
                        Integer y = Integer.parseInt(status.get((i * 4) + 3));
                        String x = "";
                        x = x.concat(status.get(i * 4));
                        x = x.concat(" / ");
                        x = x.concat(status.get((i * 4) + 1));
                        x = x.concat(" - ");
                        x = x.concat(status.get((i * 4) + 2));
                        series.getData().add(new XYChart.Data<>("", y));
                        series.setName(x);
                        statistik_chart_penggunaan.getData().add(series);
                    }
                    statistik_chart_penggunaan.setVisible(true);

                } else {
                    notFound1.setVisible(true);
                    statistik_chart_penggunaan.setVisible(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


    });
    //penggunaan alat oleh suatu kelompok
        statistik_search_golongan.setOnAction(event -> {
            statistik_chart_penggunaankelompok.getData().removeAll();//BELUM BERFUNGSI
            statistik_chart_penggunaankelompok.getData().clear();

            ArrayList<String> status;
            String N;
            N = statistik_golongan.getText();
            N = N.toLowerCase();
            //System.out.println(N);
            try {
                status = statistik.ShowStatistikPenggunaanKelompok(N);
                if (!status.isEmpty()) {
                    notFound2.setVisible(false);
                    for (int i = 0; i < status.size() / 4; i++) {
                        XYChart.Series<String, Integer> series = new XYChart.Series<>();
                        System.out.println(i);
                        Integer y = Integer.parseInt(status.get((i * 4) + 3));
                        String x = "";
                        x = x.concat(status.get(i * 4));
                        x = x.concat(" / ");
                        x = x.concat(status.get((i * 4) + 1));
                        x = x.concat(" - ");
                        x = x.concat(status.get((i * 4) + 2));
                        series.getData().add(new XYChart.Data<>("", y));
                        series.setName(x);

                        statistik_chart_penggunaankelompok.getData().add(series);
                    }
                    statistik_chart_penggunaankelompok.setVisible(true);

                } else {
                    notFound2.setVisible(true);
                    statistik_chart_penggunaankelompok.setVisible(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        alat_search_button.setDefaultButton(true);
        alat_search_button.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<ArrayList<String>> tabelBuffer = new ArrayList<ArrayList<String>>();
                    int pilihan = 2;
                    String N;
                    if (alat_combo_search.getValue().equalsIgnoreCase("ID")){
                        pilihan=1;
                    } else if (alat_combo_search.getValue().equalsIgnoreCase("Nama")){
                        pilihan=2;
                    } else if (alat_combo_search.getValue().equalsIgnoreCase("Lokasi")){
                        pilihan=3;
                    } else if (alat_combo_search.getValue().equalsIgnoreCase("Kondisi")){
                        pilihan=4;
                    }

                    if(!alat_search_field.getText().isEmpty()){
                        N = alat_search_field.getText();
                    } else {
                        N = "1 OR 1=1";
                    }

                    RowAlat rowAlat = new RowAlat();
                    tabelBuffer = rowAlat.tampilkanAlat(N, pilihan);

                    ArrayList<RowAlat> tabelBooking = new ArrayList<RowAlat>();
                    System.out.println(tabelBuffer.size());

                    for (int i=0;i<tabelBuffer.size();i++ ){
                        tabelBooking.add(new RowAlat(
                                Integer.parseInt(tabelBuffer.get(i).get(0)),
                                tabelBuffer.get(i).get(1),
                                tabelBuffer.get(i).get(2),
                                tabelBuffer.get(i).get(3))
                        );
                    }

                    for (int i=0;i<tabelBooking.size();i++){
                        System.out.println(tabelBooking.get(i).getLokasi());
                    }

                    alat_table.setVisible(true);
                    alat_table.setEditable(true);
                    ObservableList<RowAlat> listBuffer = FXCollections.observableArrayList(tabelBooking);

                    alat_table.setItems(listBuffer);
                    alat_kolom_id.setCellValueFactory(new PropertyValueFactory<RowAlat, Integer>("id_alat"));
                    alat_kolom_nama.setCellValueFactory(new PropertyValueFactory<RowAlat, String>("nama_alat"));
                    alat_kolom_lokasi.setCellValueFactory(new PropertyValueFactory<RowAlat, String>("lokasi"));
                    alat_kolom_kondisi.setCellValueFactory(new PropertyValueFactory<RowAlat, String>("kondisi"));
                }
            }).start();
        });

        alat_delete_button.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RowAlat rowAlat = alat_table.getSelectionModel().getSelectedItem();
                    int id_alat = rowAlat.getId_alat();
                    rowAlat.hapusAlat(id_alat);
                    alat_table.setVisible(false);
                    Perbaikan perbaikan = new Perbaikan();
                    ArrayList<ArrayList<String>> temp = perbaikan.semuaAlat();
                    if(alat_combo_update.getItems().size() > 0)
                        alat_combo_update.getItems().remove(0, alat_combo_update.getItems().size()-1);
                    else if(alat_combo_update.getItems().size() == 1)
                        alat_combo_update.getItems().remove(0);

                    for(int i=0; i<temp.size(); i++){
                        alat_combo_update.getItems().add(temp.get(i).get(0) + "-" + temp.get(i).get(1));
                    }
                }
            }).start();
        });

        alat_update_button.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(alat_combo_update.getItems().size() > 1)
                        alat_combo_update.getItems().remove(0, alat_combo_update.getItems().size()-1);
                    else if(alat_combo_update.getItems().size() == 1)
                        alat_combo_update.getItems().remove(0);
                    System.out.println(alat_combo_update.getItems().size());
                    alat_group_1.setVisible(true);
                    alat_id.setVisible(true);
                    alat_combo_update.setVisible(true);

                    alat_proceed_button.setDefaultButton(true);
                    alat_add_button.setVisible(false);
                    alat_update_button.setVisible(false);
                    alat_id.setText(null);

                    Perbaikan perbaikan = new Perbaikan();
                    ArrayList<ArrayList<String>> temp = perbaikan.semuaAlat();

                    for(int i=0; i<temp.size(); i++){
                        alat_combo_update.getItems().add(temp.get(i).get(0) + "-" + temp.get(i).get(1));
                    }
                }
            }).start();
        });

        alat_add_button.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    alat_group_1.setVisible(true);
                    alat_id.setVisible(false);
                    alat_proceed_button.setDefaultButton(true);

                    alat_add_button.setVisible(false);
                    alat_update_button.setVisible(false);
                    alat_id.setText(null);
                }
            }).start();
        });

        alat_cancel_button.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    alat_group_1.setVisible(false);
                    alat_combo_update.setVisible(false);
                    alat_proceed_button.setDefaultButton(false);

                    alat_add_button.setVisible(true);
                    alat_update_button.setVisible(true);
                }
            }).start();
        });

        alat_proceed_button.setOnAction(event->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    alat_group_1.setVisible(false);
                    alat_combo_update.setVisible(false);
                    alat_proceed_button.setDefaultButton(false);

                    //Proses data
                    int id_alat = -1;
                    if(alat_id.getText() != null)
                        id_alat = Integer.parseInt(alat_id.getText());
                    String nama_alat = alat_nama.getText();
                    String lokasi_alat = alat_lokasi.getText();
                    String kondisi_alat = alat_kondisi.getText();

                    String query = "";
                    RowAlat rowAlat = new RowAlat();
                    if(rowAlat.exists(id_alat)){
                        rowAlat.ubahAlat(id_alat, nama_alat, lokasi_alat, kondisi_alat);
                    } else {
                        rowAlat.tambahAlat(nama_alat, lokasi_alat, kondisi_alat);
                    }

                    Perbaikan perbaikan = new Perbaikan();
                    ArrayList<ArrayList<String>> temp = perbaikan.semuaAlat();

                    for(int i=0; i<temp.size(); i++){
                        alat_combo_update.getItems().add(temp.get(i).get(0) + "-" + temp.get(i).get(1));
                    }

                    alat_add_button.setVisible(true);
                    alat_update_button.setVisible(true);
                }
            }).start();
        });

        alat_combo_update.setOnAction(event -> {
            new Thread(new Runnable() {
                @Override
                public void run(){
                    String alat = alat_combo_update.getSelectionModel().getSelectedItem();
                    int i = alat.indexOf("-");
                    String id_alat = alat.substring(0, i);
                    RowAlat rowAlat = new RowAlat();
                    ArrayList<ArrayList<String>> result = rowAlat.tampilkanAlat(id_alat, 1);
                    alat_id.setText(result.get(0).get(0));
                    alat_id.setDisable(true);
                    alat_nama.setText(result.get(0).get(1));
                    alat_lokasi.setText(result.get(0).get(2));
                    alat_kondisi.setText(result.get(0).get(3));
                }
            }).start();
        });

    }
}

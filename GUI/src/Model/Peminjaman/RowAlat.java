package Model.Peminjaman;

/**
 * Created by acel on 16-Feb-16.
 */
public class RowAlat {
    private int id_alat;
    private String nama_alat;
    private boolean checked;

    public RowAlat(int id_alat, String nama_alat, boolean checked) {
        this.id_alat = id_alat;
        this.nama_alat = nama_alat;
        this.checked = checked;
    }

    public int getId_alat() {
        return id_alat;
    }

    public void setId_alat(int id_alat) {
        this.id_alat = id_alat;
    }

    public String getNama_alat() {
        return nama_alat;
    }

    public void setNama_alat(String nama_alat) {
        this.nama_alat = nama_alat;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

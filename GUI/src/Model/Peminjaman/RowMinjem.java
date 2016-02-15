package Model.Peminjaman;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by jsavigny on 2/16/16.
 */
public class RowMinjem {
    private int idAlat;
    private String namaAlat;

    public int getIdAlat() {
        return idAlat;
    }

    public void setIdAlat(int idAlat) {
        this.idAlat = idAlat;
    }

    public RowMinjem(int idAlat, String namaAlat, boolean checked) {
        this.idAlat = idAlat;
        this.namaAlat = namaAlat;
        this.checked = new SimpleBooleanProperty(checked);
    }

    public String getNamaAlat() {
        return namaAlat;
    }

    public void setNamaAlat(String namaAlat) {
        this.namaAlat = namaAlat;
    }

    public boolean getChecked() {
        return checked.get();
    }

    public BooleanProperty checkedProperty() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    private BooleanProperty checked;
}

package Model.Perbaikan;

/**
 * Created by jsavigny on 2/15/16.
 */
public class RowPerbaikan {
    public String getIdPerbaikan() {
        return idPerbaikan;
    }

    public void setIdPerbaikan(String idPerbaikan) {
        this.idPerbaikan = idPerbaikan;
    }

    public String getNamaPerbaikan() {
        return namaPerbaikan;
    }

    public void setNamaPerbaikan(String namaPerbaikan) {
        this.namaPerbaikan = namaPerbaikan;
    }

    public String getKondisiPerbaikan() {
        return kondisiPerbaikan;
    }

    public void setKondisiPerbaikan(String kondisiPerbaikan) {
        this.kondisiPerbaikan = kondisiPerbaikan;
    }

    public RowPerbaikan(String idPerbaikan, String namaPerbaikan, String kondisiPerbaikan) {
        this.idPerbaikan = idPerbaikan;
        this.namaPerbaikan = namaPerbaikan;
        this.kondisiPerbaikan = kondisiPerbaikan;
    }

    String idPerbaikan;
    String namaPerbaikan;
    String kondisiPerbaikan;
}

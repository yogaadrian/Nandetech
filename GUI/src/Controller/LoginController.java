package Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jsavigny on 2/15/16.
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField login_password_field;

    @FXML
    private Button login_password_button;

    @FXML
    private Label passwordError;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        login_password_button.setOnAction(event->{
            String password=login_password_field.getText();
            if (password.equals("Adm1nDuktek")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Parent root;
                        try {
                            root = FXMLLoader.load(getClass().getResource("../View/Nandetech.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setTitle("Nandetech");
                            stage.setScene(scene);
                            stage.show();
                            ((Node)(event.getSource())).getScene().getWindow().hide();
                        } catch (IOException exc) {
                            exc.printStackTrace();
                        }
                    }
                }).start();
            } else {
                passwordError.setVisible(true);
            }
        });
    }

}

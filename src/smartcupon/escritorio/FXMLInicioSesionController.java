/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.escritorio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import smartcupon.modelo.dao.InicioSesionDAO;
import smartcupon.modelo.pojo.RespuestaInicioSesion;
import smartcupon.modelo.pojo.Usuario;
import smartcupon.utils.Utilidades;

/**
 *
 * @author Manuel Hernandez
 */
public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private Label lbErrorUsername;
    @FXML
    private Label lbErrorPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbErrorUsername.setText("");
        lbErrorPassword.setText("");
    }

    @FXML
    private void btnIniciarSesion(ActionEvent event) {
        lbErrorUsername.setText("");
        lbErrorPassword.setText("");

        String username = tfUsername.getText();
        String password = tfPassword.getText();

        if (verificarCampos(username, password)) {
            verificarInicioSesion(username, password);
        }
    }

    private boolean verificarCampos(String username, String password) {
        boolean isValido = true;

        if (username.trim().isEmpty()) {
            lbErrorUsername.setText("Campo de usuario requerido");
            isValido = false;
        }

        if (password.trim().isEmpty()) {
            lbErrorPassword.setText("Campo de contraseña requerido");
            isValido = false;
        }

        return isValido;
    }

    private void verificarInicioSesion(String username, String password) {
        RespuestaInicioSesion respuesta = InicioSesionDAO.validarSesion(username, password);

        if (!respuesta.getError()) {
            Utilidades.mostrarAlertaSimple(
                    "Credenciales correctas",
                    respuesta.getContenido(),
                    Alert.AlertType.INFORMATION);

            irPantallaHome(respuesta.getUsuario());
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error:",
                    respuesta.getContenido(),
                    Alert.AlertType.ERROR);
        }
    }

    private void irPantallaHome(Usuario usuario) {
        try {
            Stage stage = (Stage) tfUsername.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLHome.fxml"));
            Parent vista = loader.load();
            
            FXMLHomeController controller = loader.getController();
            controller.inicializarVariables(usuario);

            Scene scene = new Scene(vista);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

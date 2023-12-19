/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.escritorio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import smartcupon.modelo.pojo.Usuario;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLHomeController implements Initializable {

    Usuario usuario = null;
    
    @FXML
    private Label lbNombreUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void inicializarVariables(Usuario usuario) {
        this.usuario = usuario;
        lbNombreUsuario.setText(usuario.getNombre());
    }

    @FXML
    private void btnIrPantallaEmpresa(ActionEvent event) {
    }

    @FXML
    private void btnIrPantallaPromocion(ActionEvent event) {
    }

    @FXML
    private void btnIrPantallaCupon(ActionEvent event) {
    }

    @FXML
    private void btnIrPantallaSucursal(ActionEvent event) {
    }

    @FXML
    private void btnIrPantallaUsuario(ActionEvent event) {
    }

}

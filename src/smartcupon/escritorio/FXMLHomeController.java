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
import javafx.scene.control.TextField;
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
    @FXML
    private Label lbEmpresa;
    @FXML
    private TextField tfEmpresa;
    @FXML
    private TextField tfCurp;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfRol;
    @FXML
    private TextField tfNombre;

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
        tfNombre.setText(usuario.getNombre() + " " + usuario.getApellidoPaterno() + " " + usuario.getApellidoMaterno());
        tfCurp.setText(usuario.getCurp());
        tfEmail.setText(usuario.getEmail());
        tfRol.setText(usuario.getNombreRol());
        
        detectarRolUsuario();
    }
    
    private void detectarRolUsuario() {
        if(this.usuario.getRol() != 1){
            lbEmpresa.setVisible(false);
            tfEmpresa.setVisible(false);
        }
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

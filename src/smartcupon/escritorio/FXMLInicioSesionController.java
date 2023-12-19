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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Manuel Hernandez
 */
public class FXMLInicioSesionController implements Initializable {
    
    private Label label;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private Label lbErrorUsername;
    @FXML
    private Label lbErrorPassword;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnIniciarSesion(ActionEvent event) {
        lbErrorUsername.setText("");
        lbErrorPassword.setText("");
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        boolean isValido = true;
        
        if(username.isEmpty()){
            lbErrorUsername.setText("Campo de usuario requerido");
            isValido = false;
        }
        if(password.isEmpty()){
            lbErrorPassword.setText("Campo de contraseña requerido");
            isValido = false;
        }
        if(isValido){
            verificarInicioSesion(username,password);
        }
    }
    
    private void verificarInicioSesion(String username, String password){
        
    }
    
    private void IrPantallaHome(){
    
    }
}

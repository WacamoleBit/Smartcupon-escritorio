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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    @FXML
    private Button btnEmpresa;
    @FXML
    private Label lbBotonEmpresa;
    @FXML
    private Button btnUsuarios;
    @FXML
    private Label lbUsuarios;
    @FXML
    private ImageView ivUsuarios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void inicializarVariables(Usuario usuario) {
        this.usuario = usuario;
        lbNombreUsuario.setText(usuario.getNombre()+" !");
        tfNombre.setText(usuario.getNombre() + " " + usuario.getApellidoPaterno() + " " + usuario.getApellidoMaterno());
        tfCurp.setText(usuario.getCurp());
        tfEmail.setText(usuario.getEmail());
        tfRol.setText(usuario.getNombreRol());
        
        detectarRolUsuario();
    }
    
    private void detectarRolUsuario() {
        if (this.usuario.getRol() != 2) {
            btnEmpresa.setVisible(false);
            lbBotonEmpresa.setVisible(false);
            btnUsuarios.setVisible(false);
            lbUsuarios.setVisible(false);
            ivUsuarios.setVisible(false);
            
            tfEmpresa.setText(usuario.getNombreEmpresa().toString());
        } else {
            lbEmpresa.setVisible(false);
            tfEmpresa.setVisible(false);
        }
    }
    
    @FXML
    private void btnIrPantallaEmpresa(ActionEvent event) {
        try {
            FXMLLoader vistaLoader = new FXMLLoader(getClass().getResource("FXMLAdminEmpresas.fxml"));
            Parent vista = vistaLoader.load();
            
            FXMLAdminEmpresasController controlador = vistaLoader.getController();
            controlador.consultarEmpresas();
            
            Stage stage = new Stage();
            Scene escenaFormularioEdicion = new Scene(vista);
            stage.setScene(escenaFormularioEdicion);
            stage.setTitle("Gestión empresas");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void btnIrPantallaPromocion(ActionEvent event) {
        try {
            FXMLLoader vistaLoader = new FXMLLoader(getClass().getResource("FXMLAdminPromociones.fxml"));
            Parent vista = vistaLoader.load();
            
            FXMLAdminPromocionesController controlador = vistaLoader.getController();
            controlador.definirEmpresa(usuario.getEmpresa());
            
            Stage stage = new Stage();
            Scene escenaFormularioEdicion = new Scene(vista);
            stage.setScene(escenaFormularioEdicion);
            stage.setTitle("Gestión promociones");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void btnIrPantallaCupon(ActionEvent event) {
        try {
            FXMLLoader vistaLoader = new FXMLLoader(getClass().getResource("FXMLAdminCupones.fxml"));
            Parent vista = vistaLoader.load();
            
            FXMLAdminCuponesController controlador = vistaLoader.getController();
            controlador.obtenerEmpresa(usuario.getEmpresa());
            
            
            Stage stage = new Stage();
            Scene escenaFormularioEdicion = new Scene(vista);
            stage.setScene(escenaFormularioEdicion);
            stage.setTitle("Gestión cupones");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void btnIrPantallaSucursal(ActionEvent event) {
        try {
            FXMLLoader vistaLoader = new FXMLLoader(getClass().getResource("FXMLAdminSucursales.fxml"));
            Parent vista = vistaLoader.load();
            
            FXMLAdminSucursalesController controlador = vistaLoader.getController();
            controlador.consultarSucursales();
            controlador.definirEmpresa(usuario.getEmpresa());
            
            Stage stage = new Stage();
            Scene escenaFormularioEdicion = new Scene(vista);
            stage.setScene(escenaFormularioEdicion);
            stage.setResizable(false);
            stage.setTitle("Gestión sucursales");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void btnIrPantallaUsuarios(ActionEvent event) {
        try {
            FXMLLoader vistaLoader = new FXMLLoader(getClass().getResource("FXMLAdminUsuarios.fxml"));
            Parent vista = vistaLoader.load();
            
            FXMLAdminUsuariosController controlador = vistaLoader.getController();
            
            Stage stage = new Stage();
            Scene escenaAdminUsuarios = new Scene(vista);
            stage.setScene(escenaAdminUsuarios);
            stage.sizeToScene();
            stage.setResizable(false);
            stage.setTitle("Gestión usuarios");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}

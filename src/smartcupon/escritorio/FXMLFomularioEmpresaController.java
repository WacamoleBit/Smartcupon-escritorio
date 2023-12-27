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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import smartcupon.modelo.pojo.DatosEmpresa;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLFomularioEmpresaController implements Initializable {

    private DatosEmpresa datosEmpresa;
    
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private TextField tfNombreComercial;
    @FXML
    private TextField tfRepresentanteLegal;
    @FXML
    private TextField tfRfc;
    @FXML
    private TextField tfPaginaWeb;
    @FXML
    private TextField tfEmail;
    @FXML
    private ImageView ivFoto;
    @FXML
    private TextField tfCuidad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void iniciarlizarInformacion(DatosEmpresa datosEmpresa){
        if (datosEmpresa!=null) {
            this.datosEmpresa=datosEmpresa;
            rellenarCampos();
        }
    }
    
    @FXML
    private void btnGuardar(ActionEvent event) {
        
    }
    
    private void rellenarCampos(){
        tfNombre.setText(datosEmpresa.getEmpresa().getNombre());
        tfCodigoPostal.setText(datosEmpresa.getDireccion().getCodigoPostal());
    }
    
    
}

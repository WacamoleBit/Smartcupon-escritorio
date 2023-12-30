/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.escritorio;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import smartcupon.modelo.dao.DireccionDAO;
import smartcupon.modelo.pojo.Ciudad;
import smartcupon.modelo.pojo.DatosEmpresa;
import smartcupon.modelo.pojo.Direccion;
import smartcupon.modelo.pojo.Empresa;
import smartcupon.modelo.pojo.Estado;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLFomularioEmpresaController implements Initializable {

    DatosEmpresa datosEmpresa;
    
    private ObservableList<Ciudad> ciudades;
    private ObservableList<Estado> estados;
    
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
    private ComboBox<Ciudad> cbCiudad;
    @FXML
    private ComboBox<Estado> cbEstado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cargarInformacionEstados();
        this.configurarSeleccionEstado();
    }    

    public void iniciarlizarInformacion(DatosEmpresa datosEmpresa){
        if (datosEmpresa!=null && datosEmpresa.getDireccion() != null ) {
            this.datosEmpresa=datosEmpresa;
            rellenarCampos();
        }
    }
    
     private void rellenarCampos(){
        tfNombre.setText(datosEmpresa.getEmpresa().getNombre());
        tfNombreComercial.setText(datosEmpresa.getEmpresa().getNombreComercial());
        tfTelefono.setText(datosEmpresa.getEmpresa().getTelefono());
        tfPaginaWeb.setText(datosEmpresa.getEmpresa().getPaginaWeb());
        tfRfc.setText(datosEmpresa.getEmpresa().getRfc());
        tfEmail.setText(datosEmpresa.getEmpresa().getEmail());
        
        
        tfRepresentanteLegal.setText(datosEmpresa.getPersona().getNombreTipoPersona());
        
        int numeroEstado = buscarIdEstado(datosEmpresa.getDireccion().getIdDireccion());
        cbEstado.getSelectionModel().select(numeroEstado);
        int numeroCiudad = buscarIdCiudad(datosEmpresa.getDireccion().getCiudad());
        cbCiudad.getSelectionModel().select(numeroCiudad);
        tfCalle.setText(datosEmpresa.getDireccion().getCalle());
        tfNumero.setText(datosEmpresa.getDireccion().getNumero().toString());
        tfCodigoPostal.setText(datosEmpresa.getDireccion().getCodigoPostal());
    }
    
    private void registrarEmpresa(){
        datosEmpresa = new DatosEmpresa();
        Empresa empresa = new Empresa();
        Direccion direccion = new Direccion();
    }
    
    
    private void cargarInformacionEstados() {
        estados = FXCollections.observableArrayList();
        List<Estado> listaEstados = DireccionDAO.obtenerEstados();
        estados.addAll(listaEstados);
        cbEstado.setItems(estados);
        
    }
    
    private void configurarSeleccionEstado() {
        cbEstado.valueProperty().addListener(new ChangeListener<Estado>(){
            @Override
            public void changed(ObservableValue<? extends Estado> observable, Estado oldValue, Estado newValue) {
                cargarInformacionCiudades(newValue.getIdEstado());
            }
        });
    }
    
    private void cargarInformacionCiudades(Integer idEstado) {
        ciudades = FXCollections.observableArrayList();
        List<Ciudad> listaCiudades = DireccionDAO.obtenerCiudades(idEstado);
        ciudades.addAll(listaCiudades);
        cbCiudad.setItems(ciudades);
    }
    
    private int buscarIdEstado(Integer idEstado){
        for (int i=0; i < estados.size();i++){
            if(estados.get(i).getIdEstado() == idEstado){
                return i;
            }
        }
        
        return 0;
    }
    
    private int buscarIdCiudad(Integer idCiudad){
        for (int i=0; i < ciudades.size();i++){
            if(ciudades.get(i).getIdCiudad() == idCiudad){
                return i;
            }
        }
        return 0;
    }
    
    private void editarEmpresa(){
    
    }
     
    @FXML
    private void btnGuardar(ActionEvent event) {
        if(datosEmpresa != null){
            editarEmpresa();
        }else{
            registrarEmpresa();
        }
        
    }

    @FXML
    private void btnSubirFoto(ActionEvent event) {
    }

    @FXML
    private void btnSeleccionarFoto(ActionEvent event) {
    }

}

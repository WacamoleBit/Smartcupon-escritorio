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
import javafx.util.StringConverter;
import smartcupon.modelo.dao.DireccionDAO;
import smartcupon.modelo.dao.SucursalDAO;
import smartcupon.modelo.pojo.Ciudad;
import smartcupon.modelo.pojo.DatosSucursal;
import smartcupon.modelo.pojo.Estado;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLFormularioSucursalController implements Initializable {

    DatosSucursal datosSucursal;
    
    private ObservableList<Estado> estados;
    private ObservableList<Ciudad> ciudades;
    
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfNombreEncargado;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfLatitud;
    @FXML
    private TextField tfLongitud;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private TextField tfColonia;
    @FXML
    private ComboBox<Estado> cbEstado;
    @FXML
    private ComboBox<Ciudad> cbCiudad;
    @FXML
    private TextField tfEmpresaAsociada;
    @FXML
    private TextField tfApellidoMaterno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cargarInformacionEstados();
        this.configurarSeleccionEstado();
    }    

    @FXML
    private void btnGuardar(ActionEvent event) {
    }
    
    public void inicializarInformacion(DatosSucursal datosSucursal){
        if(datosSucursal!= null){
            this.datosSucursal = datosSucursal;
            rellenarCampos();
        }
    }
    
    private void rellenarCampos(){
        tfNombre.setText(datosSucursal.getSucursal().getNombre());
        tfTelefono.setText(datosSucursal.getSucursal().getTelefono());
        tfLatitud.setText(datosSucursal.getSucursal().getLatitud().toString());
        tfLongitud.setText(datosSucursal.getSucursal().getLongitud().toString());
        tfNombreEncargado.setText(datosSucursal.getPersona().getNombre());
        tfApellidoMaterno.setText(datosSucursal.getPersona().getApellidoMaterno());
        tfApellidoPaterno.setText(datosSucursal.getPersona().getApellidoPaterno());
        tfCalle.setText(datosSucursal.getDireccion().getCalle());
        tfCodigoPostal.setText(datosSucursal.getDireccion().getCodigoPostal());
        tfColonia.setText(datosSucursal.getDireccion().getColonia());
        tfNumero.setText(datosSucursal.getDireccion().getNumero().toString());
        int numeroEstado = buscarIdEstado(datosSucursal.getDireccion().getIdDireccion());
        cbEstado.getSelectionModel().select(numeroEstado);
        int numeroCiudad = buscarIdCiudad(datosSucursal.getDireccion().getCiudad());
        cbCiudad.getSelectionModel().select(numeroCiudad);
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
            if(estados.get(i).getIdEstado() == idCiudad){
                return i;
            }
        }
        return 0;
    }
}

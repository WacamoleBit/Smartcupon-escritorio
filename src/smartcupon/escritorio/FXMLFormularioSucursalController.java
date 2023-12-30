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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import smartcupon.modelo.dao.DireccionDAO;
import smartcupon.modelo.dao.EmpresaDAO;
import smartcupon.modelo.dao.SucursalDAO;
import smartcupon.modelo.pojo.Ciudad;
import smartcupon.modelo.pojo.DatosSucursal;
import smartcupon.modelo.pojo.Direccion;
import smartcupon.modelo.pojo.Empresa;
import smartcupon.modelo.pojo.Estado;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Persona;
import smartcupon.modelo.pojo.Sucursal;
import smartcupon.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLFormularioSucursalController implements Initializable {

    DatosSucursal datosSucursal;
    
    private ObservableList<Estado> estados;
    private ObservableList<Ciudad> ciudades;
    private ObservableList<Empresa> empresas;
    
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
    private TextField tfApellidoMaterno;
    @FXML
    private ComboBox<Empresa> cbEmpresa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cargarInformacionEstados();
        this.configurarSeleccionEstado();
        this.cargarInformacionEmpresas();
    }    

    @FXML
    private void btnGuardar(ActionEvent event) {
        if(datosSucursal!= null){
            
        }else{
            registrarSucursal();
        }
        
    }
    
    public void inicializarInformacion(DatosSucursal datosSucursal){
        if(datosSucursal!= null && datosSucursal.getSucursal()!=null && datosSucursal.getPersona()!=null && datosSucursal.getDireccion()!=null){
            this.datosSucursal = datosSucursal;
            rellenarCampos();
        }
    }
    
    private void rellenarCampos(){
        tfNombre.setText(datosSucursal.getSucursal().getNombre());
        tfTelefono.setText(datosSucursal.getSucursal().getTelefono());
        tfLatitud.setText(datosSucursal.getSucursal().getLatitud().toString());
        tfLongitud.setText(datosSucursal.getSucursal().getLongitud().toString());
        int numeroEmpresa = buscarIdEmpresa(datosSucursal.getSucursal().getEmpresa());
        cbEmpresa.getSelectionModel().select(numeroEmpresa);
        
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
    
    
    private void registrarSucursal(){
        
        datosSucursal = new DatosSucursal();
        
        Sucursal sucursal = new Sucursal();
        Persona encargado = new Persona();
        Direccion direccion = new Direccion();
        
        sucursal.setNombre(tfNombre.getText());
        sucursal.setTelefono(tfTelefono.getText());
        sucursal.setLatitud(Double.parseDouble(tfLatitud.getText()));
        sucursal.setLongitud(Double.parseDouble(tfLongitud.getText()));
        sucursal.setEmpresa(cbEmpresa.getSelectionModel().getSelectedIndex());
        
        encargado.setNombre(tfNombreEncargado.getText());
        encargado.setApellidoPaterno(tfApellidoPaterno.getText());
        encargado.setApellidoMaterno(tfApellidoMaterno.getText());
        
        direccion.setCalle(tfCalle.getText());
        direccion.setNumero(Integer.parseInt(tfNumero.getText()));
        direccion.setColonia(tfColonia.getText());
        direccion.setCodigoPostal(tfCodigoPostal.getText());
        direccion.setCiudad(cbCiudad.getSelectionModel().getSelectedIndex());
        
        datosSucursal.setSucursal(sucursal);
        datosSucursal.setPersona(encargado);
        datosSucursal.setDireccion(direccion);
        
        rellenarSucursal(datosSucursal);
    }
    
    private void rellenarSucursal(DatosSucursal datosSucursal){
        Mensaje respuesta = SucursalDAO.registrarSucursal(datosSucursal);
        if(!respuesta.getError()){
            Utilidades.mostrarAlertaSimple("Sucursal regustrada",respuesta.getMensaje(), Alert.AlertType.INFORMATION);
        }else{
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.WARNING);
        }
    }
    
    private void actualizarSucursal(DatosSucursal datosSucursal){
       
    }
    
       private void cargarInformacionEstados() {
        estados = FXCollections.observableArrayList();
        List<Estado> listaEstados = DireccionDAO.obtenerEstados();
        estados.addAll(listaEstados);
        cbEstado.setItems(estados);
        
    }
       
       private void cargarInformacionEmpresas(){
           empresas = FXCollections.observableArrayList();
           List<Empresa> listaEmpresas = EmpresaDAO.obtenerEmpresas();
           empresas.addAll(listaEmpresas);
           cbEmpresa.setItems(empresas);
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
    
    private int buscarIdEmpresa(int idEmpresa) {
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getIdEmpresa() == idEmpresa) {
                return i;
            }
        }

        return 0;
    }

    private void editarSucursal(DatosSucursal datosSucursal) {
        
        datosSucursal.getSucursal().setNombre(tfNombre.getText());
        datosSucursal.getSucursal().setTelefono(tfTelefono.getText());
        datosSucursal.getSucursal().setLatitud(Double.parseDouble(tfLatitud.getText()));
        datosSucursal.getSucursal().setLongitud(Double.parseDouble(tfLongitud.getText()));
        datosSucursal.getSucursal().setEmpresa(cbEmpresa.getSelectionModel().getSelectedIndex());
        
        datosSucursal.getPersona().setNombre(tfNombreEncargado.getText());
        datosSucursal.getPersona().setApellidoPaterno(tfApellidoPaterno.getText());
        datosSucursal.getPersona().setApellidoMaterno(tfApellidoMaterno.getText());
        
        datosSucursal.getDireccion().setCalle(tfCalle.getText());
        datosSucursal.getDireccion().setNumero(Integer.parseInt(tfNumero.getText()));
        datosSucursal.getDireccion().setColonia(tfColonia.getText());
        datosSucursal.getDireccion().setCodigoPostal(tfCodigoPostal.getText());
        datosSucursal.getDireccion().setCiudad(cbCiudad.getSelectionModel().getSelectedIndex());
    }
}

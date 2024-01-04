/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.escritorio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import smartcupon.modelo.dao.DireccionDAO;
import smartcupon.modelo.dao.EmpresaDAO;
import smartcupon.modelo.pojo.Ciudad;
import smartcupon.modelo.pojo.DatosEmpresa;
import smartcupon.modelo.pojo.Direccion;
import smartcupon.modelo.pojo.Empresa;
import smartcupon.modelo.pojo.Estado;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Persona;
import smartcupon.modelo.pojo.TipoPromocion;
import smartcupon.utils.Utilidades;
import static smartcupon.utils.Utilidades.seleccionarImagen;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class FXMLFormularioEmpresaController implements Initializable {

    DatosEmpresa datosEmpresa;
    Empresa empresa = null;
    Persona representante = null;
    Direccion direccion = null;
    
    
    File imagen = null;
    
    
    private ObservableList<String> estatus = null;
    private ObservableList<Ciudad> ciudades;
    private ObservableList<Estado> estados;
    
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfNombreComercial;
    @FXML
    private TextField tfRfc;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbNombreRepresentante;
    @FXML
    private Label lbNombreComercial;
    @FXML
    private Label lbRfc;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private Label lbApellidoPaterno;
    @FXML
    private TextField tfPaginaWeb;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfTelefono;
    @FXML
    private Label lbPaginaWeb;
    @FXML
    private Label lbEmail;
    @FXML
    private Label lbTelefono;
    @FXML
    private ImageView ivFoto;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private ComboBox<Ciudad> cbCiudad;
    @FXML
    private ComboBox<Estado> cbEstado;
    @FXML
    private Label lbCalle;
    @FXML
    private Label lbCodigoPostal;
    @FXML
    private Label lbNumero;
    @FXML
    private Label lbCuidad;
    @FXML
    private Label lbEstado;
    @FXML
    private Label lbLogo;
    @FXML
    private TextField tfNombreRepresentante;
    @FXML
    private Label lbApellidoMaterno;
    @FXML
    private ComboBox<String> cbEstatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         limpiarLabels();
        cargarInformacionEstados();
        configurarSeleccionEstado();
        cargarInformacionEstatus();
        configurarNumeros();
    }    

    @FXML
    private void btnGuardar(ActionEvent event) {
        limpiarLabels();
            if(datosEmpresa == null){
                datosEmpresa = new DatosEmpresa();
                empresa = new Empresa();
                direccion = new Direccion();
                representante = new Persona();
            }
            
            if(validarCamposVacios()){
                datosEmpresa = llenarDatosEmpresa();
                
                guardarDatos(datosEmpresa);
            }
                
           
    }

    @FXML
    private void btnBuscarImagen(MouseEvent event) {
        Window ventanaPadre = tfNombre.getScene().getWindow();

        imagen = seleccionarImagen(ventanaPadre);

        if (imagen != null) {
            mostrarEnImageview(imagen);
        }
    }
    
    
    public void iniciarlizarDatos(Integer idEmpresa){
        datosEmpresa = EmpresaDAO.obtenerDatosEmpresa(idEmpresa);
        
        empresa = datosEmpresa.getEmpresa();
        representante = datosEmpresa.getPersona();
        direccion = datosEmpresa.getDireccion();
        
        rellenarCampos();
        
        cbCiudad.setDisable(false);
        cbEstatus.setDisable(false);
    }
    
     private void rellenarCampos(){
        tfNombre.setText(datosEmpresa.getEmpresa().getNombre());
        tfNombreComercial.setText(datosEmpresa.getEmpresa().getNombreComercial());
        tfTelefono.setText(datosEmpresa.getEmpresa().getTelefono());
        tfPaginaWeb.setText(datosEmpresa.getEmpresa().getPaginaWeb());
        tfRfc.setText(datosEmpresa.getEmpresa().getRfc());
        tfEmail.setText(datosEmpresa.getEmpresa().getEmail());
        cbEstatus.getSelectionModel().select(
                datosEmpresa.getEmpresa().getIdEstatus() - 1
        );
        tfNombreRepresentante.setText(datosEmpresa.getPersona().getNombre());
        tfApellidoPaterno.setText(datosEmpresa.getPersona().getApellidoPaterno());
        tfApellidoMaterno.setText(datosEmpresa.getPersona().getApellidoMaterno());
        
        int numeroEstado = buscarIdEstado(datosEmpresa.getDireccion().getEstado());
        cbEstado.getSelectionModel().select(numeroEstado);
        int numeroCiudad = buscarIdCiudad(datosEmpresa.getDireccion().getCiudad());
        cbCiudad.getSelectionModel().select(numeroCiudad);
        tfCalle.setText(datosEmpresa.getDireccion().getCalle());
        tfNumero.setText(datosEmpresa.getDireccion().getNumero().toString());
        tfCodigoPostal.setText(datosEmpresa.getDireccion().getCodigoPostal());
        if(datosEmpresa.getEmpresa().getLogoBase64()!= null && datosEmpresa.getEmpresa().getLogoBase64().length() > 0){
            ivFoto.setImage(Utilidades.decodificarImagenBase64(datosEmpresa.getEmpresa().getLogoBase64()));
        }
    }
     
    private DatosEmpresa llenarDatosEmpresa(){
        empresa.setNombre(tfNombre.getText().trim());
        empresa.setNombreComercial(tfNombreComercial.getText().trim());
        empresa.setEmail(tfEmail.getText().trim());
        empresa.setPaginaWeb(tfPaginaWeb.getText().trim());
        empresa.setRfc(tfRfc.getText().trim());
        empresa.setTelefono(tfTelefono.getText().trim());
        empresa.setIdEstatus(cbEstatus.getSelectionModel().getSelectedIndex() +1);
   
        direccion.setCalle(tfCalle.getText().trim());
        direccion.setCiudad(cbCiudad.getValue().getIdCiudad());
        direccion.setEstado(cbEstado.getValue().getIdEstado());
        direccion.setCodigoPostal(tfCodigoPostal.getText().trim());
        direccion.setNumero(Integer.parseInt(tfNumero.getText().trim()));
        
        representante.setNombre(tfNombreRepresentante.getText().trim());
        representante.setApellidoPaterno(tfApellidoPaterno.getText().trim());
        representante.setApellidoMaterno(tfApellidoMaterno.getText().trim());
        
        datosEmpresa.setEmpresa(empresa);
        datosEmpresa.setDireccion(direccion);
        datosEmpresa.setPersona(representante);
        
        return datosEmpresa;
    }
    
    private void cargarInformacionEstatus() {
        List<String> todas = new ArrayList();

        todas.add("Activo");
        todas.add("Inactivo");

        estatus = FXCollections.observableArrayList();
        estatus.addAll(todas);

        cbEstatus.setItems(estatus);

        cbEstatus.getSelectionModel().selectFirst();
    }
    
    private void limpiarLabels(){
        lbNombre.setText("");
        lbNombreComercial.setText("");
        lbCalle.setText("");
        lbCodigoPostal.setText("");
        lbEmail.setText("");
        lbCuidad.setText("");
        lbEstado.setText("");
        lbLogo.setText("");
        lbNombreRepresentante.setText("");
        lbApellidoPaterno.setText("");
        lbApellidoMaterno.setText("");
        lbNumero.setText("");
        lbPaginaWeb.setText("");
        lbRfc.setText("");
        lbTelefono.setText("");
        lbCuidad.setText("");
        lbEstado.setText("");
    }
     
    private void limpiarCampos(){
        tfNombre.clear();
        tfNombreComercial.clear();
        tfEmail.clear();
        tfNumero.clear();
        tfPaginaWeb.clear();
        tfNombreRepresentante.clear();
        tfApellidoPaterno.clear();
        tfApellidoMaterno.clear();
        tfRfc.clear();
        tfTelefono.clear();
        tfCodigoPostal.clear();
        tfCalle.clear();
        cbCiudad.setValue(null);
        cbEstado.setValue(null);
    }
    
    private boolean validarCamposVacios(){
        if(tfNombre.getText().isEmpty()
        || tfCalle.getText().isEmpty()
        || tfCodigoPostal.getText().isEmpty()
        || tfEmail.getText().isEmpty()
        || tfNombreComercial.getText().isEmpty()
        || tfNumero.getText().isEmpty()
        || tfPaginaWeb.getText().isEmpty()
        || tfNombreRepresentante.getText().isEmpty()
        || tfApellidoPaterno.getText().isEmpty()
        || tfApellidoMaterno.getText().isEmpty()
        || tfRfc.getText().isEmpty()
        || tfTelefono.getText().isEmpty()
        || cbCiudad.getValue() == null
        || cbEstado.getValue() == null ){
         
         
         Utilidades.mostrarAlertaSimple("Error: ", "Campos vacios, introduce todos los datos", Alert.AlertType.WARNING);
     
         if(tfNombre.getText().isEmpty()){
             lbNombre.setText("Ingrese el nombre de la empresa");
         }
         if(tfNombreComercial.getText().isEmpty()){
             lbNombreComercial.setText("Ingrese el nombre comercial de la empresa");
         }
         if(tfCalle.getText().isEmpty()){
             lbCalle.setText("Ingrese la calle");
         }
         if(tfEmail.getText().isEmpty()){
             lbEmail.setText("Ingrese el email");
         }
         if(tfCodigoPostal.getText().isEmpty() || tfCodigoPostal.getText().length() != 5){
             lbCodigoPostal.setText("Ingrese el codigo postal valido");
         }
         if(tfNumero.getText().isEmpty()){
             lbNumero.setText("Ingrese el numero");
         }
         if(tfPaginaWeb.getText().isEmpty()){
             lbPaginaWeb.setText("Ingrese la pagina web");
         }
         if(tfRfc.getText().isEmpty()){
             lbRfc.setText("Ingrese el rfc");
         }
         if(tfTelefono.getText().isEmpty()){
             lbTelefono.setText("Ingrese el telefono");
         }
         if(tfNombreRepresentante.getText().isEmpty()){
             lbNombreRepresentante.setText("Ingrese el nombre");
         }
         
         if(tfApellidoPaterno.getText().isEmpty()){
             lbApellidoPaterno.setText("Ingrese el apellido");
         }
         
         if(tfApellidoMaterno.getText().isEmpty()){
             lbApellidoMaterno.setText("Ingrese el apellido");
         }
         if(cbCiudad.getValue() == null){
             lbCuidad.setText("Seleccione la ciudad");
         }
         if(cbEstado.getValue() == null){
             lbEstado.setText("Seleccione el estado");
         }
         return false;
     }
     return true;
    }
    
    private void configurarSeleccionEstado() {
        cbEstado.valueProperty().addListener(new ChangeListener<Estado>(){
            @Override
            public void changed(ObservableValue<? extends Estado> observable, Estado oldValue, Estado newValue) {
                cargarInformacionCiudades(newValue.getIdEstado());
            }
        });
    }
    
    private void cargarInformacionEstados() {
        estados = FXCollections.observableArrayList();
        List<Estado> listaEstados = DireccionDAO.obtenerEstados();
        estados.addAll(listaEstados);
        cbEstado.setItems(estados);
        
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
    
     private void configurarNumeros(){
        tfTelefono.setTextFormatter(Utilidades.configurarFiltroNumerosConLimite(10));
        tfNumero.setTextFormatter(Utilidades.configurarFiltroNumeros());
        tfCodigoPostal.setTextFormatter(Utilidades.configurarFiltroNumerosConLimite(5));
   }
     
     
     
    private void mostrarEnImageview(File arhivoImagen) {
        try {
            BufferedImage buffer = ImageIO.read(arhivoImagen);
            Image imagen = SwingFXUtils.toFXImage(buffer, null);
            ivFoto.setImage(imagen);
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Error al cargar",
                    "Error al intentar visualizar la imagen seleccionada",
                    Alert.AlertType.ERROR);
        }
    }
    
    private void guardarDatos(DatosEmpresa empresa){
        Mensaje msj = null;
        if(empresa.getEmpresa().getIdEmpresa() == null){
            msj = EmpresaDAO.registrarEmpresa(empresa, imagen);
            if(!msj.getError()){
                Utilidades.mostrarAlertaSimple("La empresa se registró con éxito.", msj.getMensaje(), Alert.AlertType.INFORMATION);
            }else{
                Utilidades.mostrarAlertaSimple("Error al registrar la empresa.", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        limpiarCampos();
        }else{
            msj = EmpresaDAO.editarEmpresa(empresa, imagen);
            if(!msj.getError()){
                Utilidades.mostrarAlertaSimple("La empresa se editó con éxito.", msj.getMensaje(), Alert.AlertType.INFORMATION);
            }else{
                Utilidades.mostrarAlertaSimple("Error al editar la empresa.", msj.getMensaje(), Alert.AlertType.ERROR);
            }
            rellenarCampos();
        }
    }
}

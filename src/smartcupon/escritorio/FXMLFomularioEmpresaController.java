/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.escritorio;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import javax.rmi.CORBA.Util;
import smartcupon.modelo.dao.DireccionDAO;
import smartcupon.modelo.dao.EmpresaDAO;
import smartcupon.modelo.pojo.Ciudad;
import smartcupon.modelo.pojo.DatosEmpresa;
import smartcupon.modelo.pojo.Direccion;
import smartcupon.modelo.pojo.Empresa;
import smartcupon.modelo.pojo.Estado;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Persona;
import smartcupon.utils.Utilidades;
import static smartcupon.utils.Utilidades.seleccionarImagen;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLFomularioEmpresaController implements Initializable {

    DatosEmpresa datosEmpresa;
    Empresa empresa = null;
    Persona representante = null;
    Direccion direccion = null;
    
    private ObservableList<Ciudad> ciudades;
    private ObservableList<Estado> estados;
    private ObservableList<Empresa> empresas;
    private File logo = null;
    
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
    private TextField tfRfc;
    @FXML
    private TextField tfRepresentante;
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
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbRepresentante;
    @FXML
    private Label lbNombreComercial;
    @FXML
    private Label lbRfc;
    @FXML
    private Label lbPaginaWeb;
    @FXML
    private Label lbEmail;
    @FXML
    private Label lbTelefono;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        limpiarLabels();
        this.cargarInformacionEstados();
        this.configurarSeleccionEstado();
        configurarNumeros();
    }    

    public void iniciarlizarInformacion(DatosEmpresa datosEmpresa){
        if (datosEmpresa!= null && datosEmpresa.getDireccion() != null && datosEmpresa.getPersona() != null) {
            this.datosEmpresa = datosEmpresa;
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
        
        tfRepresentante.setText(datosEmpresa.getPersona().getNombre()+ " " +datosEmpresa.getPersona().getApellidoPaterno() + " " + datosEmpresa.getPersona().getApellidoMaterno());
        
        int numeroEstado = buscarIdEstado(datosEmpresa.getDireccion().getIdDireccion());
        cbEstado.getSelectionModel().select(numeroEstado);
        int numeroCiudad = buscarIdCiudad(datosEmpresa.getDireccion().getCiudad());
        cbCiudad.getSelectionModel().select(numeroCiudad);
        tfCalle.setText(datosEmpresa.getDireccion().getCalle());
        tfNumero.setText(datosEmpresa.getDireccion().getNumero().toString());
        tfCodigoPostal.setText(datosEmpresa.getDireccion().getCodigoPostal());
    }
     
    private DatosEmpresa llenarDatosEmpresa(){
        empresa.setNombre(tfNombre.getText());
        empresa.setNombreComercial(tfNombreComercial.getText());
        empresa.setEmail(tfEmail.getText());
        empresa.setPaginaWeb(tfPaginaWeb.getText());
        empresa.setRfc(tfRfc.getText());
        empresa.setTelefono(tfTelefono.getText());
        direccion.setCiudad(cbCiudad.getValue().getIdCiudad());
        direccion.setEstado(cbEstado.getValue().getIdEstado());
        direccion.setCodigoPostal(tfCodigoPostal.getText());
        direccion.setNumero(Integer.parseInt(tfNumero.getText()));
        representante.setNombreTipoPersona(tfRepresentante.getText());
    
        datosEmpresa.setEmpresa(empresa);
        datosEmpresa.setDireccion(direccion);
        datosEmpresa.setPersona(representante);
        
        return datosEmpresa;
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
        tfRepresentante.clear();
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
        || tfRepresentante.getText().isEmpty()
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
         if(tfCodigoPostal.getText().isEmpty()){
             lbCodigoPostal.setText("Ingrese el codigo postal");
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
         if(tfRepresentante.getText().isEmpty()){
             lbRepresentante.setText("Ingrese el representante");
         }
         if(cbCiudad.getValue() == null){
             lbCuidad.setText("Seleccione la ciudad");
         }
         if(cbEstado.getValue() == null){
             lbEstado.setText("Seleccione el estado");
         }
         
         return true;
     }
     return false;
    }
    
    private void guardarDatos(Empresa empresa){
        Mensaje msj = null;
        if(empresa.getIdEmpresa() == null){
            msj = EmpresaDAO.registrarEmpresa(empresa, logo);
            if(!msj.getError()){
                Utilidades.mostrarAlertaSimple("La empresa se registró con éxito.", msj.getMensaje(), Alert.AlertType.INFORMATION);
            }else{
                Utilidades.mostrarAlertaSimple("Error al registrar la empresa.", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        limpiarCampos();
        }else{
            msj = EmpresaDAO.editarEmpresa(empresa, logo);
            if(!msj.getError()){
                Utilidades.mostrarAlertaSimple("La empresa se editó con éxito.", msj.getMensaje(), Alert.AlertType.INFORMATION);
            }else{
                Utilidades.mostrarAlertaSimple("Error al editar la empresa.", msj.getMensaje(), Alert.AlertType.ERROR);
            }
            rellenarCampos();
        }
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
    
    private File mostrarSeleccion(){
        FileChooser dialogoSeleccionImg = new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona una imagen");
        FileChooser.ExtensionFilter filtroArchivos = new FileChooser.ExtensionFilter("Archivos de imagen (*.png,*.jpg,*.jpeg)", "*.png","*.jpg","*.jpeg");
        dialogoSeleccionImg.getExtensionFilters().add(filtroArchivos);
        Stage escenario = (Stage) tfNombre.getScene().getWindow();
        return logo = dialogoSeleccionImg.showOpenDialog(escenario);
        
    }
    
    private void configurarNumeros(){
        tfTelefono.setTextFormatter(Utilidades.configurarFiltroNumerosConLimite(10));
        tfNombre.setTextFormatter(Utilidades.configurarFiltroNumeros());
        tfCodigoPostal.setTextFormatter(Utilidades.configurarFiltroNumerosConLimite(5));
   }
    
    private void mostrarLogo(File logo){
        try{
            BufferedImage buffer = ImageIO.read(logo);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            ivFoto.setImage(image);
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Error: ", "No se pudo cargar la imagen seleccionada", Alert.AlertType.ERROR);
        }
    }
    private void obtenerLogo(MouseEvent event){
        Window ventanaPadre = tfNombre.getScene().getWindow();
        logo = seleccionarImagen(ventanaPadre);

        if (logo != null) {
            mostrarLogo(logo);
        }
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
                guardarDatos(empresa);
            }
    }

    

}

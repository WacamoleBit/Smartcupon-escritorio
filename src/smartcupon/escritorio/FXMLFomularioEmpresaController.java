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
    Persona representante = null;
    
    private ObservableList<Ciudad> ciudades;
    private ObservableList<Estado> estados;
    private ObservableList<Empresa> empresas;
    private File logo;
    
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
        this.cargarInformacionEstados();
        this.configurarSeleccionEstado();
    }    

    public void iniciarlizarInformacion(DatosEmpresa datosEmpresa){
        if (datosEmpresa!= null && datosEmpresa.getDireccion() != null ) {
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
     
    private void limpiarCampos(){
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
             lbNombre.setText("Campo requerido");
         }
         if(tfNombreComercial.getText().isEmpty()){
             lbNombreComercial.setText("Campo requerido");
         }
         if(tfCalle.getText().isEmpty()){
             lbCalle.setText("Campo requerido");
         }
         if(tfEmail.getText().isEmpty()){
             lbEmail.setText("Campo requerido");
         }
         if(tfCodigoPostal.getText().isEmpty()){
             lbCodigoPostal.setText("Campo requerido");
         }
         if(tfNumero.getText().isEmpty()){
             lbNumero.setText("Campo requerido");
         }
         if(tfPaginaWeb.getText().isEmpty()){
             lbPaginaWeb.setText("Campo requerido");
         }
         if(tfRfc.getText().isEmpty()){
             lbRfc.setText("Campo requerido");
         }
         if(tfTelefono.getText().isEmpty()){
             lbNombre.setText("Campo requerido");
         }
         if(tfTelefono.getText().isEmpty()){
             lbNombre.setText("Campo requerido");
         }
         if(tfRepresentante.getText().isEmpty()){
             lbRepresentante.setText("Campo requerido");
         }
         if(cbCiudad.getValue() == null){
             lbCuidad.setText("Campo requerido");
         }
         if(cbEstado.getValue() == null){
             lbEstado.setText("Campo requerido");
         }
         
         return true;
     }
     return false;
    }
    
    private boolean validarTipoDato() {
        boolean datosValidos = true;
        String telefono = tfTelefono.getText();
        if (!telefono.matches("\\d{10}")) {
            lbTelefono.setText("Teléfono inválido");
            datosValidos = false;
        }
        String rfc = tfRfc.getText();
        if (!rfc.matches("[A-Z]{4}[0-9]{6}[A-Z0-9]{3}")) {
            lbRfc.setText("RFC inválido");
            datosValidos = false;
        }
        String email = tfEmail.getText();
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            lbEmail.setText("Email inválido");
            datosValidos = false;
        }


        return datosValidos;
    }    
    
    private void registrarEmpresa(){
        datosEmpresa = new DatosEmpresa();
        Empresa empresa = new Empresa();
        Direccion direccion = new Direccion();
        Persona persona = new Persona();
        
        Mensaje msj = EmpresaDAO.registrarEmpresa(datosEmpresa);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Empresa registrada con éxito. ", msj.getMensaje(), Alert.AlertType.INFORMATION);
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar la empresa, intenta de nuevo.", msj.getMensaje(), Alert.AlertType.ERROR);
        }
        
    }
    
    private void editarEmpresa(){
       Mensaje msj = EmpresaDAO.editarEmpresa(datosEmpresa);
        if (!msj.getError()) {
            Utilidades.mostrarAlertaSimple("Datos de la empresa editos con exito.", msj.getMensaje(), Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlertaSimple("Error al editar la empresa, intenta de nuevo por favor.", msj.getMensaje(), Alert.AlertType.INFORMATION);
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
    
    private void mostrarLogo(File logo){
        try{
            BufferedImage buffer = ImageIO.read(logo);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            ivFoto.setImage(image);
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Error: ", "No se pudo cargar la imagen seleccionada", Alert.AlertType.ERROR);
        }
    }
    
    private void obtenerLogo(int idEmpresa){
        Empresa logoEmpresa = EmpresaDAO.obtenerLogo(idEmpresa);
        if(logoEmpresa != null && logoEmpresa.getLogoBase64() != null && logoEmpresa.getLogoBase64().length() > 0){
                //Image imagen = Utilidades.decodificarImagenBase64(logoEmpresa.getLogoBase64());
                byte[] decodeImg = Base64.getDecoder().decode(logoEmpresa.getLogoBase64().replaceAll("\\n", ""));
                Image image = new Image(new ByteArrayInputStream(decodeImg));
                ivFoto.setImage(image);
        }
    }
    
    
     
    @FXML
    private void btnGuardar(ActionEvent event) {
        limpiarCampos();
        if(!validarCamposVacios() && validarTipoDato()){
            if(datosEmpresa != null){
                editarEmpresa();
            }else{
                registrarEmpresa();
            }
        }
    }

    @FXML
    private void btnSubirFoto(ActionEvent event) {
        if(datosEmpresa != null && logo != null){
            Mensaje msj = EmpresaDAO.subirLogoEmpresa(logo, datosEmpresa.getEmpresa().getIdEmpresa());
            if(!msj.getError()){
                Utilidades.mostrarAlertaSimple("Logo guardado correctamente.", msj.getMensaje(), Alert.AlertType.INFORMATION);
            }else{
                Utilidades.mostrarAlertaSimple("Error al guardar el logo", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void btnSeleccionarFoto(ActionEvent event) {
        logo = mostrarSeleccion();
        if(logo != null){
            mostrarLogo(logo);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.escritorio;

import com.sun.deploy.util.FXLoader;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smartcupon.modelo.dao.EmpresaDAO;
import smartcupon.modelo.dao.SucursalDAO;
import smartcupon.modelo.pojo.DatosEmpresa;
import smartcupon.modelo.pojo.Empresa;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLAdminEmpresasController implements Initializable {

    private DatosEmpresa datosEmpresa;
    
    private ObservableList<Empresa> empresas;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableView<Empresa> tvEmpresas;
    @FXML
    private TableColumn colNombreComercial;
    @FXML
    private TableColumn colPaginaWeb;
    @FXML
    private TableColumn colRfc;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private TextField tfBuscarEmpresa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacion();
        consultarEmpresas();
    }    

    private void cargarInformacion() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colNombreComercial.setCellValueFactory(new PropertyValueFactory("nombreComercial"));
        colRfc.setCellValueFactory(new PropertyValueFactory("rfc"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory("paginaWeb"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("nombreEstatus"));
    }
    
    public void consultarEmpresas(){
        List<Empresa> listaEmpresas = EmpresaDAO.obtenerEmpresas();
        if(listaEmpresas.size() < 0 ){
            Utilidades.mostrarAlertaSimple("Empresas", "Por el momento no hay empresas registradas", Alert.AlertType.INFORMATION);
        }
        empresas = FXCollections.observableArrayList(listaEmpresas);
        tvEmpresas.setItems(empresas);
    }
    
    private void consultarInformacionEmpresa(Integer idEmpresa){
        datosEmpresa = EmpresaDAO.obtenerDatosEmpresa(idEmpresa);
    }
    
    @FXML
    private void btnRegistrar(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioEmpresa.fxml"));
            Parent vista = loader.load();
            
            FXMLFormularioEmpresaController controlador = loader.getController();
            
            
            Stage stage = new Stage();
            Scene scene = new Scene(vista);
;           stage.setScene(scene);
            stage.setTitle("Formulario de empresa");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void btnEditar(ActionEvent event) {
        Empresa empresa = tvEmpresas.getSelectionModel().getSelectedItem();
        if (empresa!= null) {
            consultarInformacionEmpresa(empresa.getIdEmpresa());
           
            datosEmpresa.setEmpresa(empresa);
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioEmpresa.fxml"));
                Parent vista = loader.load();
                
                FXMLFormularioEmpresaController controlador = loader.getController();
                controlador.iniciarlizarDatos(empresa.getIdEmpresa());
                
                Stage stage = new Stage();
                Scene escenaFormularioEdicion = new Scene(vista);
                stage.setScene(escenaFormularioEdicion);
                stage.setTitle("Formulario de empresa");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Seleccion de empresa", "Para editar, debes seleccionar una empresa de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        Mensaje respuesta = null;
        Integer idEmpresa = (tvEmpresas.getSelectionModel().getSelectedItem() != null)
                ? tvEmpresas.getSelectionModel().getSelectedItem().getIdEmpresa() : null;

        if (idEmpresa != null) {
            boolean aceptar = Utilidades.mostrarAlertaConfirmacion(
                    "Eliminar empresa",
                    "¿Estás seguro que quieres eliminar esta empresa?");

            if (idEmpresa > 0 && aceptar) {
                DatosEmpresa datos = new DatosEmpresa();
                Empresa empresa = new Empresa();
                
                empresa.setIdEmpresa(idEmpresa);
                datos.setEmpresa(empresa);
                
                respuesta = EmpresaDAO.eliminarEmpresa(datos);

                if (!respuesta.getError()) {
                    Utilidades.mostrarAlertaSimple("Eliminacion exitosa",
                            respuesta.getMensaje(),
                            Alert.AlertType.INFORMATION);
                } else {
                    Utilidades.mostrarAlertaSimple("Error al eliminar",
                            respuesta.getMensaje(),
                            Alert.AlertType.INFORMATION);
                }
            }

            consultarEmpresas();
        } else {
            Utilidades.mostrarAlertaSimple("Seleccion de empresa",
                    "Para poder eliminar debes seleccionar una empresa de la tabla",
                    Alert.AlertType.WARNING);
        }
    }
}
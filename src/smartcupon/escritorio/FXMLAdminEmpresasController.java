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
import smartcupon.modelo.pojo.DatosEmpresa;
import smartcupon.modelo.pojo.Empresa;
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
        empresas = FXCollections.observableArrayList();
        configurarColumnas();
    }    

    @FXML
    private void btnRegistrar(ActionEvent event) {
    }

    @FXML
    private void btnEditar(ActionEvent event) {
        Empresa empresa = tvEmpresas.getSelectionModel().getSelectedItem();
        if (empresa!= null) {
            consultarInformacionEmpresa(empresa.getIdEmpresa());
            datosEmpresa.setEmpresa(empresa);
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFomularioEmpresa.fxml"));
                Parent vista = loader.load();

                FXMLFomularioEmpresaController controlador = loader.getController();
                controlador.iniciarlizarInformacion(datosEmpresa);
                
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
    }

    private void configurarColumnas() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colNombreComercial.setCellValueFactory(new PropertyValueFactory("nombreComercial"));
        colRfc.setCellValueFactory(new PropertyValueFactory("rfc"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory("paginaWeb"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("nombreEstatus"));
    }
    
    public void consultarEmpresas(){
        List<Empresa> listaEmpresas = EmpresaDAO.obtenerEmpresas();
        empresas.addAll(listaEmpresas);
        tvEmpresas.setItems(empresas);
    }
    
    private void consultarInformacionEmpresa(Integer idEmpresa){
        datosEmpresa = EmpresaDAO.obtenerDatosEmpresa(idEmpresa);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.escritorio;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smartcupon.modelo.dao.SucursalDAO;
import smartcupon.modelo.pojo.DatosSucursal;
import smartcupon.modelo.pojo.Sucursal;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLAdminSucursalesController implements Initializable {

    private DatosSucursal datosSucursal;
    
    private ObservableList<Sucursal> sucursales;
    
    @FXML
    private TableView<Sucursal> tvSucursales;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colLatitud;
    @FXML
    private TableColumn colLongitud;
    @FXML
    private TextField tfBuscarSucursal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sucursales = FXCollections.observableArrayList();
        cargarInformacion();
    }    
    
    private void cargarInformacion(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colLatitud.setCellValueFactory(new PropertyValueFactory("latitud"));
        colLongitud.setCellValueFactory(new PropertyValueFactory("longitud"));
    }
    
    public void consultarSucursales(){
        List<Sucursal> listaSucursales = SucursalDAO.obtenerSucursales();
        sucursales.addAll(listaSucursales);
        tvSucursales.setItems(sucursales);
    }

    @FXML
    private void btnRegistrar(ActionEvent event) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioSucursal.fxml"));
                Parent vista = loader.load();

                FXMLFormularioSucursalController controlador = loader.getController();
                controlador.inicializarInformacion(null);
                
                Stage stage = new Stage();
                Scene escenaFormularioEdicion = new Scene(vista);
                stage.setScene(escenaFormularioEdicion);
                stage.setTitle("Formulario de sucursal");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        
    }

    @FXML
    private void btnEditar(ActionEvent event) {
        Sucursal sucursal = tvSucursales.getSelectionModel().getSelectedItem();
        if(sucursal!=null){
            
            consultarInformacionSucursal(sucursal.getIdSucursal());
            System.out.println(sucursal.getIdSucursal());
            datosSucursal.setSucursal(sucursal);
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioSucursal.fxml"));
                Parent vista = loader.load();

                FXMLFormularioSucursalController controlador = loader.getController();
                controlador.inicializarInformacion(datosSucursal);
                
                Stage stage = new Stage();
                Scene escenaFormularioEdicion = new Scene(vista);
                stage.setScene(escenaFormularioEdicion);
                stage.setTitle("Formulario de sucursal");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
    }
    
    private void consultarInformacionSucursal(Integer idSucursal){
           datosSucursal = SucursalDAO.obtenerDatosSucursal(idSucursal);
    }
}

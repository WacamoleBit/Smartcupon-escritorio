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
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smartcupon.modelo.dao.SucursalDAO;
import smartcupon.modelo.pojo.FiltroBuscarPromocion;
import smartcupon.modelo.pojo.FiltroBuscarSucursal;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Sucursal;
import smartcupon.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLAdminSucursalesController implements Initializable {

    private Integer empresa = null;

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
    @FXML
    private CheckBox cbNombre;
    @FXML
    private CheckBox cbDireccion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sucursales = FXCollections.observableArrayList();
        cargarInformacion();
    }

    private void cargarInformacion() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colLatitud.setCellValueFactory(new PropertyValueFactory("latitud"));
        colLongitud.setCellValueFactory(new PropertyValueFactory("longitud"));
    }

    public void definirEmpresa(Integer empresa) {
        this.empresa = empresa;
        consultarSucursales();
    }

    @FXML
    private void btnRegistrar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioSucursal.fxml"));
            Parent vista = loader.load();

            FXMLFormularioSucursalController controlador = loader.getController();
            controlador.definirEmpresa(empresa);

            Stage stage = new Stage();
            Scene escenaFormularioEdicion = new Scene(vista);
            stage.setScene(escenaFormularioEdicion);
            stage.setTitle("Registrar sucursal");
            stage.sizeToScene();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            consultarSucursales();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void btnEditar(ActionEvent event) {
        Integer idSucursal = (tvSucursales.getSelectionModel().getSelectedItem() != null)
                ? tvSucursales.getSelectionModel().getSelectedItem().getIdSucursal() : null;

        if (idSucursal != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioSucursal.fxml"));
                Parent vista = loader.load();

                FXMLFormularioSucursalController controlador = loader.getController();
                controlador.definirEmpresa(empresa);
                controlador.inicializarDatos(idSucursal);                

                Stage stage = new Stage();
                Scene escenaFormularioEdicion = new Scene(vista);
                stage.setScene(escenaFormularioEdicion);
                stage.setTitle("Formulario de sucursal");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

                consultarSucursales();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Utilidades.mostrarAlertaSimple("Seleccion de sucursal",
                    "Para poder modificar debes seleccionar una sucursal de la tabla",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        Mensaje respuesta = null;
        Integer idSucursal = (tvSucursales.getSelectionModel().getSelectedItem() != null)
                ? tvSucursales.getSelectionModel().getSelectedItem().getIdSucursal() : null;

        if (idSucursal != null) {
            boolean aceptar = Utilidades.mostrarAlertaConfirmacion(
                    "Eliminar sucursal",
                    "¿Estás seguro que quieres eliminar esta sucursal?");

            if (idSucursal > 0 && aceptar) {
                respuesta = SucursalDAO.eliminarSucursal(idSucursal);

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

            consultarSucursales();
        } else {
            Utilidades.mostrarAlertaSimple("Seleccion de sucursal",
                    "Para poder eliminar debes seleccionar una sucursal de la tabla",
                    Alert.AlertType.WARNING);
        }
    }

    public void consultarSucursales() {
        if (empresa != null) {
            tvSucursales.setItems(null);
            List<Sucursal> listaSucursales = SucursalDAO.obtenerSucursalesPorIdEmpresa(empresa);
            sucursales = FXCollections.observableArrayList(listaSucursales);
            tvSucursales.setItems(sucursales);
        } else {
            tvSucursales.setItems(null);
            List<Sucursal> listaSucursales = SucursalDAO.obtenerSucursales();
            sucursales = FXCollections.observableArrayList(listaSucursales);
            tvSucursales.setItems(sucursales);
        }
    }

    @FXML
    private void btnBuscar(ActionEvent event) {
        FiltroBuscarSucursal filtro = new FiltroBuscarSucursal();

        if (cbNombre.isSelected() && !tfBuscarSucursal.getText().trim().isEmpty()) {
            filtro.setNombre(tfBuscarSucursal.getText());
        }

        if (cbDireccion.isSelected() && !tfBuscarSucursal.getText().trim().isEmpty()) {
            filtro.setDireccion(tfBuscarSucursal.getText());
        }

        if (filtro.getNombre() != null || filtro.getDireccion() != null) {
            List<Sucursal> listaSucursales = SucursalDAO.obtenerPorFiltro(filtro);
            sucursales = FXCollections.observableArrayList(listaSucursales);
            tvSucursales.setItems(sucursales);
        } else {
            consultarSucursales();
        }
    }
}

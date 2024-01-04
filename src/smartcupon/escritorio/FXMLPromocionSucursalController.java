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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smartcupon.modelo.dao.PromocionDAO;
import smartcupon.modelo.dao.SucursalDAO;
import smartcupon.modelo.pojo.FiltroBuscarPromocion;
import smartcupon.modelo.pojo.FiltroBuscarSucursal;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.PromocionSucursal;
import smartcupon.modelo.pojo.Sucursal;
import smartcupon.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLPromocionSucursalController implements Initializable {

    private ObservableList<Sucursal> sucursales = null;
    private Integer idPromocion = null;

    @FXML
    private TableView<Sucursal> tvSucursales;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colTelefono;
    @FXML
    private TableColumn<?, ?> colLatitud;
    @FXML
    private TableColumn<?, ?> colLongitud;
    @FXML
    private Label lbTituloLista;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabla();
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        Integer idSucursal = (tvSucursales.getSelectionModel() != null)
                ? tvSucursales.getSelectionModel().getSelectedItem().getIdSucursal()
                : null;

        if (idSucursal != null) {
            PromocionSucursal datos = new PromocionSucursal();
            datos.setIdPromocion(idPromocion);
            datos.setIdSucursal(idSucursal);
            Mensaje mensaje = PromocionDAO.registrarPromocionSucursal(datos);

            if (!mensaje.getError()) {
                Utilidades.mostrarAlertaSimple("Registro exitoso",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al editar",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            }
            
            cargarSucursalesSinPromocion();
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        Integer idSucursal = (tvSucursales.getSelectionModel() != null)
                ? tvSucursales.getSelectionModel().getSelectedItem().getIdSucursal()
                : null;

        if (idSucursal != null) {
            PromocionSucursal datos = new PromocionSucursal();
            datos.setIdPromocion(idPromocion);
            datos.setIdSucursal(idSucursal);
            Mensaje mensaje = PromocionDAO.eliminarPromocionSucursal(datos);

            if (!mensaje.getError()) {
                Utilidades.mostrarAlertaSimple("Registro exitoso",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al editar",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            }
            
            cargarSucursalesPorPromocion();
        }
    }

    public void fijarPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public void consultarSucursales() {
        tvSucursales.setItems(null);
        List<Sucursal> listaSucursales = SucursalDAO.obtenerSucursales();
        sucursales = FXCollections.observableArrayList(listaSucursales);
        tvSucursales.setItems(sucursales);
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colLatitud.setCellValueFactory(new PropertyValueFactory("latitud"));
        colLongitud.setCellValueFactory(new PropertyValueFactory("longitud"));
    }

    public void cargarSucursalesPorPromocion() {
        btnGuardar.setVisible(false);
        tvSucursales.setItems(null);
        List<Sucursal> listaSucursales = SucursalDAO.obtenerSucursalesPorPromocion(idPromocion);
        sucursales = FXCollections.observableArrayList(listaSucursales);
        tvSucursales.setItems(sucursales);
    }

    public void cargarSucursalesSinPromocion() {
        btnEliminar.setVisible(false);
        tvSucursales.setItems(null);
        List<Sucursal> listaSucursales = SucursalDAO.obtenerSucursalesSinPromocion(idPromocion);
        sucursales = FXCollections.observableArrayList(listaSucursales);
        tvSucursales.setItems(sucursales);
    }

}

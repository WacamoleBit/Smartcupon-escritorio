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
import smartcupon.modelo.dao.PromocionDAO;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabla();
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
    }

    private void fijarPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;

        cargarSucursalesPorPromocion();

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

    private void cargarSucursalesPorPromocion() {
        if (idPromocion != null) {
            tvSucursales.setItems(null);
            List<Sucursal> listaSucursales = SucursalDAO.obtenerSucursalesPorPromocion(idPromocion);
            sucursales = FXCollections.observableArrayList(listaSucursales);
            tvSucursales.setItems(sucursales);
        } else {
            tvSucursales.setItems(null);
            List<Sucursal> listaSucursales = SucursalDAO.obtenerSucursales();
            sucursales = FXCollections.observableArrayList(listaSucursales);
            tvSucursales.setItems(sucursales);
        }
    }

}

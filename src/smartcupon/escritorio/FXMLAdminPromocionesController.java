/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.escritorio;

import java.io.IOException;
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
import smartcupon.modelo.dao.PromocionDAO;
import smartcupon.modelo.pojo.Promocion;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLAdminPromocionesController implements Initializable {

    private ObservableList<Promocion> promociones;
    
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colDescripcion;
    @FXML
    private TableColumn<?, ?> colFechaInicio;
    @FXML
    private TableColumn<?, ?> colFechaTermino;
    @FXML
    private TableColumn<?, ?> colRestricciones;
    @FXML
    private TableColumn<?, ?> colTipo;
    @FXML
    private TableColumn<?, ?> colPorcentajeDescuento;
    @FXML
    private TableColumn<?, ?> colCategoria;
    @FXML
    private TableColumn<?, ?> colCuponesDisponibles;
    @FXML
    private TableColumn<?, ?> colCodigo;
    @FXML
    private TableColumn<?, ?> colEstatus;
    @FXML
    private TableColumn<?, ?> ColEmpresa;
    @FXML
    private TextField tfBuscarPromocion;
    @FXML
    private TableView<Promocion> tvPromociones;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnasTabla();
        mostrarInformacionPromociones();
    }    

    @FXML
    private void btnRegistrar(ActionEvent event) {
        try {
            FXMLLoader vistaLoader = new FXMLLoader(getClass().getResource("FXMLFormularioPromocion.fxml"));
            Parent vista = vistaLoader.load();

            FXMLFormularioPromocionController controlador = vistaLoader.getController();

            Stage stage = new Stage();
            Scene escenaFormularioPromocion = new Scene(vista);
            stage.setScene(escenaFormularioPromocion);
            stage.setTitle("Registrar Promoción");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            mostrarInformacionPromociones();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnEditar(ActionEvent event) {
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
    }

    private void configurarColumnasTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        colFechaTermino.setCellValueFactory(new PropertyValueFactory("fechaTermino"));
        colRestricciones.setCellValueFactory(new PropertyValueFactory("restricciones"));
        colTipo.setCellValueFactory(new PropertyValueFactory("tipoPromocionNombre"));
        colPorcentajeDescuento.setCellValueFactory(new PropertyValueFactory("porcentajeDescuento"));
        colCategoria.setCellValueFactory(new PropertyValueFactory("categoriaNombre"));
        colCuponesDisponibles.setCellValueFactory(new PropertyValueFactory("cuponesDisponibles"));
        colCodigo.setCellValueFactory(new PropertyValueFactory("codigoPromocion"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatusNombre"));
        ColEmpresa.setCellValueFactory(new PropertyValueFactory("empresaNombre"));
    }

    private void mostrarInformacionPromociones() {
        tvPromociones.setItems(null);
        
        List<Promocion> respuesta = PromocionDAO.obtenerPromociones();
        
        promociones = FXCollections.observableArrayList(respuesta);
        
        tvPromociones.setItems(promociones);
    }
    
}

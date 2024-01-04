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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smartcupon.modelo.dao.CuponDAO;
import smartcupon.modelo.dao.PromocionDAO;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Promocion;
import smartcupon.modelo.pojo.Usuario;
import smartcupon.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class FXMLAdmiCuponesController implements Initializable {

    private Integer idEmpresa;
    private Promocion promocion;
    private ObservableList<Promocion> promociones;
    
    @FXML
    private TextField tfBuscar;
    @FXML
    private TableView<Promocion> tvCupones;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCodigo;
    @FXML
    private TableColumn colCuponesDisponibles;
    @FXML
    private TableColumn colFechaVigencia;
    @FXML
    private TableColumn colEstatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacion();
        obtenerPromociones();
        tvCupones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, codigo) -> {
        if (codigo != null) {
            tfBuscar.setText(codigo.getCodigoPromocion());
        }
    });
    }    

    @FXML
    private void btnCanjearCupon(ActionEvent event) {
        Mensaje mensaje = null;      
        promocion = new Promocion();
        promocion.setCodigoPromocion(tfBuscar.getText().trim().toString());
  
        boolean aceptar = Utilidades.mostrarAlertaConfirmacion(
                    "Canjear cupon",
                    "¿Estás seguro de que quieres canjear este cupon?");

            if (!promocion.getCodigoPromocion().isEmpty() && aceptar) {
                mensaje = CuponDAO.canjearCupon(promocion);

                if (!mensaje.getError()) {
                    
                    Utilidades.mostrarAlertaSimple("Canjeo éxitoso",
                            mensaje.getMensaje(),
                            Alert.AlertType.INFORMATION);
                    obtenerPromociones();
                } else {
                    Utilidades.mostrarAlertaSimple("Error al canjear el cupon",
                            mensaje.getMensaje(),
                            Alert.AlertType.INFORMATION);
                }
            }
         else{
            Utilidades.mostrarAlertaSimple("Codigo cupon",
                    "Para poder canjear debes ingresar el codigo del cupon",
                    Alert.AlertType.WARNING);
        }
        }
    
    public void obtenerEmpresa(Integer idEmpresa){
        this.idEmpresa = idEmpresa;
        obtenerPromociones();
    }
    
    public void obtenerPromociones(){
        tvCupones.setItems(null);
        List<Promocion> listaPromociones = CuponDAO.obtenerPromociones(idEmpresa);
        if(listaPromociones.isEmpty()){
            Utilidades.mostrarAlertaSimple("Cupones", "Por el momento no hay cupones disponibles", Alert.AlertType.INFORMATION);
        }
        promociones = FXCollections.observableArrayList(listaPromociones);
        tvCupones.setItems(promociones);
    }
    
    private void cargarInformacion(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCodigo.setCellValueFactory(new PropertyValueFactory("codigoPromocion"));
        colCuponesDisponibles.setCellValueFactory(new PropertyValueFactory("cuponesDisponibles"));
        colFechaVigencia.setCellValueFactory(new PropertyValueFactory("fechaTermino"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatusNombre"));
    }
    
   
}

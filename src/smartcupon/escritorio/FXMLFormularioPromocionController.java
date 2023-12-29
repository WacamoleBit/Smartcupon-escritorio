/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.escritorio;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import smartcupon.modelo.dao.EmpresaDAO;
import smartcupon.modelo.dao.PromocionDAO;
import smartcupon.modelo.pojo.Categoria;
import smartcupon.modelo.pojo.Empresa;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Promocion;
import smartcupon.modelo.pojo.TipoPromocion;
import smartcupon.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLFormularioPromocionController implements Initializable {

    private final String ESTILOERROR = "-fx-border-color: red;"
            + "-fx-border-radius: 30;"
            + "-fx-background-color: white;"
            + "-fx-background-radius: 30;";

    private final String ESTILODEFAULT = "-fx-background-color: white;"
            + "-fx-background-radius: 30;";

    private final String ESTILOCUADRADOERROR = "-fx-border-color: red;"
            + "-fx-background-color: white;";

    private final String ESTILOCUADRADODEFAULT = "-fx-background-color: white;";

    private ObservableList<Empresa> empresas = null;
    private ObservableList<String> estatus = null;
    private ObservableList<TipoPromocion> tiposPromocion = null;
    private ObservableList<Categoria> categorias = null;

    private Promocion promocion = null;

    @FXML
    private TextField tfNombre;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private TextArea tfDescripcion;
    @FXML
    private Label lbErrorDescripcion;
    @FXML
    private TextArea tfRestricciones;
    @FXML
    private Label lbErrorRestricciones;
    @FXML
    private ComboBox<TipoPromocion> cbTipoPromocion;
    @FXML
    private Label lbErrorTipoPromocion;
    @FXML
    private TextField tfCostoDescuento;
    @FXML
    private Label lbErrorCostoDescuento;
    @FXML
    private TextField tfMaximoCupones;
    @FXML
    private Label lbErrorMaximoCupones;
    @FXML
    private TextField tfCodigoPromocion;
    @FXML
    private Label lbErrorCodigo;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private Label lbErrorFechaInicio;
    @FXML
    private DatePicker dpFechaTermino;
    @FXML
    private Label lbErrorFechaFin;
    @FXML
    private Label lbErrorCategoria;
    @FXML
    private ComboBox<String> cbEstatus;
    @FXML
    private Label lbErrorEstatus;
    @FXML
    private ComboBox<Categoria> cbCategoria;
    @FXML
    private ComboBox<Empresa> cbEmpresa;
    @FXML
    private Label lbErrorEmpresa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ocultarLabelsError();
        cargarInformacionEmpresas();
        cargarInformacionEstatus();
        cargarInformacionTipoPromocion();
        cargarInformacionCategorias();
        configurarCamposNumericos();
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        ocultarLabelsError();

        if (promocion == null) {
            promocion = new Promocion();
        }

        if (validarDatos()) {
            promocion.setNombre(tfNombre.getText());
            promocion.setDescripcion(tfDescripcion.getText());
            promocion.setFechaInicio(dpFechaInicio.getValue().toString());
            promocion.setFechaTermino(dpFechaTermino.getValue().toString());
            promocion.setRestricciones(tfRestricciones.getText());
            promocion.setTipoPromocion(
                    cbTipoPromocion.getValue().getIdTipoPromocion()
            );
            promocion.setPorcentajeDescuento(
                    Integer.parseInt(tfCostoDescuento.getText())
            );
            promocion.setCategoria(
                    cbCategoria.getValue().getIdCategoria()
            );
            promocion.setMaximoCupones(
                    Integer.parseInt(tfMaximoCupones.getText())
            );
            promocion.setCodigoPromocion(tfCodigoPromocion.getText());
            promocion.setEstatus(cbEstatus.getSelectionModel().getSelectedIndex());
            promocion.setEmpresa(
                    cbCategoria.getValue().getIdCategoria()
            );

            guardarDatos(promocion);

        } else {
            Utilidades.mostrarAlertaSimple("Campos vacios",
                    "Llene los campos antes de guardar",
                    Alert.AlertType.ERROR);
        }
    }

    private void ocultarLabelsError() {
        lbErrorNombre.setText(null);
        lbErrorDescripcion.setText(null);
        lbErrorRestricciones.setText(null);
        lbErrorTipoPromocion.setText(null);
        lbErrorCostoDescuento.setText(null);
        lbErrorMaximoCupones.setText(null);
        lbErrorCodigo.setText(null);
        lbErrorFechaInicio.setText(null);
        lbErrorFechaFin.setText(null);
        lbErrorEmpresa.setText(null);
        lbErrorEstatus.setText(null);
        lbErrorCategoria.setText(null);
    }

    private void cargarInformacionEmpresas() {
        List<Empresa> todas = EmpresaDAO.obtenerEmpresas();

        empresas = FXCollections.observableArrayList();
        empresas.addAll(todas);

        cbEmpresa.setItems(empresas);
    }

    private void cargarInformacionEstatus() {
        List<String> todas = new ArrayList();

        todas.add("Activo");
        todas.add("Inactivo");

        estatus = FXCollections.observableArrayList();
        estatus.addAll(todas);

        cbEstatus.setItems(estatus);

        cbEstatus.getSelectionModel().selectFirst();
    }

    private void cargarInformacionTipoPromocion() {
        List<TipoPromocion> todas = PromocionDAO.obtenerTiposPromocion();

        tiposPromocion = FXCollections.observableArrayList();
        tiposPromocion.addAll(todas);

        cbTipoPromocion.setItems(tiposPromocion);
    }

    private void cargarInformacionCategorias() {
        List<Categoria> todas = PromocionDAO.obtenerCategorias();

        categorias = FXCollections.observableArrayList();
        categorias.setAll(todas);

        cbCategoria.setItems(categorias);
    }

    private boolean validarDatos() {
        boolean valido = true;

        if (tfNombre.getText().trim().isEmpty()) {
            valido = false;
            tfNombre.setStyle(ESTILOERROR);
            lbErrorNombre.setText("Ingrese nombre");
        } else {
            tfNombre.setStyle(ESTILODEFAULT);
        }

        if (tfDescripcion.getText().trim().isEmpty()) {
            valido = false;
            tfDescripcion.setStyle(ESTILOCUADRADOERROR);
            lbErrorDescripcion.setText("Ingrese descripción");
        } else {
            tfDescripcion.setStyle(ESTILOCUADRADODEFAULT);
        }

        if (tfRestricciones.getText().trim().isEmpty()) {
            valido = false;
            tfRestricciones.setStyle(ESTILOCUADRADOERROR);
            lbErrorRestricciones.setText("Ingrese restricciones");
        } else {
            tfRestricciones.setStyle(ESTILOCUADRADODEFAULT);
        }

        if (cbTipoPromocion.getValue() == null) {
            valido = false;
            cbTipoPromocion.setStyle(ESTILOERROR);
            lbErrorTipoPromocion.setText("Seleccione tipo de promoción");
        } else {
            cbTipoPromocion.setStyle(ESTILODEFAULT);
        }

        if (tfCostoDescuento.getText().trim().isEmpty()) {
            valido = false;
            tfCostoDescuento.setStyle(ESTILOERROR);
            lbErrorCostoDescuento.setText("Ingrese costo final o descuento");
        } else {
            tfCostoDescuento.setStyle(ESTILODEFAULT);
        }

        if (tfMaximoCupones.getText().trim().isEmpty()) {
            valido = false;
            tfMaximoCupones.setStyle(ESTILOERROR);
            lbErrorMaximoCupones.setText("Ingrese cupones máximos");
        } else {
            tfMaximoCupones.setStyle(ESTILODEFAULT);
        }

        if (tfCodigoPromocion.getText().trim().isEmpty()) {
            valido = false;
            tfCodigoPromocion.setStyle(ESTILOERROR);
            lbErrorCodigo.setText("Ingrese código de promoción");
        } else {
            tfCodigoPromocion.setStyle(ESTILODEFAULT);
        }

        if (dpFechaInicio.getValue() == null) {
            valido = false;
            dpFechaInicio.setStyle(ESTILOCUADRADOERROR);
            lbErrorFechaInicio.setText("Seleccione fecha de inicio");
        } else {
            dpFechaInicio.setStyle(ESTILOCUADRADODEFAULT);
        }

        if (dpFechaTermino.getValue() == null) {
            valido = false;
            dpFechaTermino.setStyle(ESTILOCUADRADOERROR);
            lbErrorFechaFin.setText("Seleccione fecha de termino");
        } else {
            dpFechaTermino.setStyle(ESTILOCUADRADODEFAULT);
        }

        if (cbCategoria.getValue() == null) {
            valido = false;
            cbCategoria.setStyle(ESTILOERROR);
            lbErrorCategoria.setText("Seleccione categoria");
        } else {
            cbCategoria.setStyle(ESTILODEFAULT);
        }

        if (cbEmpresa.getValue() == null) {
            valido = false;
            cbEmpresa.setStyle(ESTILOERROR);
            lbErrorEmpresa.setText("Seleccione una empresa");
        } else {
            cbEmpresa.setStyle(ESTILODEFAULT);
        }

        return valido;
    }

    private void configurarCamposNumericos() {
        tfMaximoCupones.setTextFormatter(Utilidades.configurarFiltroNumeros());
        tfCostoDescuento.setTextFormatter(Utilidades.configurarFiltroNumeros());
    }

    private void guardarDatos(Promocion promocion) {
        Mensaje mensaje = null;

        if (promocion.getIdPromocion() == null) {
            mensaje = PromocionDAO.registrarPromocion(promocion);

            if (!mensaje.getError()) {
                Utilidades.mostrarAlertaSimple("Registro exitoso",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al registrar",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            }
        }

        limpiarCampos();
    }

    private void limpiarCampos() {
        tfNombre.setText("");
        tfDescripcion.setText("");
        tfRestricciones.setText("");
        cbTipoPromocion.setValue(null);
        tfCostoDescuento.setText("");
        tfMaximoCupones.setText("");
        tfCodigoPromocion.setText("");
        dpFechaInicio.setValue(null);
        dpFechaTermino.setValue(null);
        cbEmpresa.setValue(null);
        cbEstatus.setValue(ESTILOERROR);
        cbCategoria.setValue(null);
    }

}

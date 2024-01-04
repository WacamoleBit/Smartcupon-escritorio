/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.escritorio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import smartcupon.modelo.dao.EmpresaDAO;
import smartcupon.modelo.dao.PromocionDAO;
import smartcupon.modelo.pojo.Categoria;
import smartcupon.modelo.pojo.Empresa;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Promocion;
import smartcupon.modelo.pojo.Sucursal;
import smartcupon.modelo.pojo.TipoPromocion;
import smartcupon.utils.Utilidades;
import static smartcupon.utils.Utilidades.seleccionarImagen;

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
    private ObservableList<Sucursal> sucursales = null;

    private Promocion promocion = null;
    private File imagen = null;
    private Integer empresa = null;

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
    @FXML
    private ImageView ivPromocion;
    @FXML
    private ComboBox<Sucursal> cbSucursales;
    @FXML
    private Button btnEliminarSucursales;

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
        configurarDatePickers();
    }

    public void inicializarDatos(Integer idPromocion) {
        this.promocion = PromocionDAO.obtenerPorId(idPromocion);
        cargarInformacionPromocion();
        if (promocion.getIdPromocion() != null) {
            cargarInformacionSucursales();
        }
        cbEstatus.setDisable(false);        
    }

    public void definirEmpresa(Integer empresa) {
        this.empresa = empresa;
        cargarInformacionEmpresas();
        configurarBotonesSucursal();
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        ocultarLabelsError();

        if (promocion == null) {
            promocion = new Promocion();
        } else {
            promocion.setEstatus(
                    cbEstatus.getSelectionModel().getSelectedIndex() + 1
            );
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
            promocion.setEmpresa(
                    cbEmpresa.getValue().getIdEmpresa()
            );

            guardarDatos(promocion);

        } else {
            Utilidades.mostrarAlertaSimple("Campos vacios",
                    "Llene o corrija los campos antes de guardar",
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
        if (empresa != null) {
            Empresa empresa = EmpresaDAO.obtenerDatosEmpresa(this.empresa).getEmpresa();
            List<Empresa> todas = new ArrayList<>();
            todas.add(empresa);
            empresas = FXCollections.observableArrayList();
            empresas.addAll(todas);

            cbEmpresa.setItems(empresas);
        } else {
            List<Empresa> todas = EmpresaDAO.obtenerEmpresas();

            empresas = FXCollections.observableArrayList();
            empresas.addAll(todas);

            cbEmpresa.setItems(empresas);
        }
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

    private void cargarInformacionSucursales() {
        List<Sucursal> listaSucursales = PromocionDAO.obtenerSucursalesPorPromocion(promocion.getIdPromocion());

        sucursales = FXCollections.observableArrayList();
        sucursales.setAll(listaSucursales);
        cbSucursales.setItems(sucursales);

        cbSucursales.getSelectionModel().selectFirst();
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

        if (!tfCodigoPromocion.getText().trim().isEmpty()
                && tfCodigoPromocion.getText().length() != 8) {
            valido = false;
            tfCodigoPromocion.setStyle(ESTILOERROR);
            lbErrorCodigo.setText("El máximo son 8 caracteres");
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

        if (dpFechaTermino.getValue() != null && dpFechaInicio.getValue() != null) {
            LocalDate fechaInicio = dpFechaInicio.getValue();
            LocalDate fechaTermino = dpFechaTermino.getValue();

            int comparacion = fechaInicio.compareTo(fechaTermino);

            if (comparacion > 0) {
                dpFechaTermino.setStyle(ESTILOCUADRADOERROR);
                dpFechaInicio.setStyle(ESTILOCUADRADOERROR);
                lbErrorFechaFin.setText("Corrige la fecha de termino");
                lbErrorFechaInicio.setText("Corrige la fecha de inicio");
            } else {
                dpFechaTermino.setStyle(ESTILOCUADRADODEFAULT);
                dpFechaInicio.setStyle(ESTILOCUADRADODEFAULT);
            }
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
            mensaje = PromocionDAO.registrarPromocion(promocion, imagen);

            if (!mensaje.getError()) {
                Utilidades.mostrarAlertaSimple("Registro exitoso",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al registrar",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            }

            limpiarCampos();
        } else {
            mensaje = PromocionDAO.editarPromocion(promocion, imagen);

            if (!mensaje.getError()) {
                Utilidades.mostrarAlertaSimple("Edición exitosa",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al editar",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            }

            cargarInformacionPromocion();
        }
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
        cbEstatus.getSelectionModel().selectFirst();
        cbCategoria.setValue(null);
    }

    private void cargarInformacionPromocion() {
        tfNombre.setText(promocion.getNombre());
        tfDescripcion.setText(promocion.getDescripcion());
        tfRestricciones.setText(promocion.getRestricciones());
        cbTipoPromocion.getSelectionModel().select(
                buscarIdTipoPromocion(promocion.getTipoPromocion())
        );
        tfCostoDescuento.setText(promocion.getPorcentajeDescuento().toString());
        tfMaximoCupones.setText(promocion.getMaximoCupones().toString());
        tfCodigoPromocion.setText(promocion.getCodigoPromocion());
        dpFechaInicio.setValue(LocalDate.parse(promocion.getFechaInicio()));
        dpFechaTermino.setValue(LocalDate.parse(promocion.getFechaTermino()));
        cbCategoria.getSelectionModel().select(
                buscarIdCategoria(promocion.getCategoria())
        );
        cbEstatus.getSelectionModel().select(
                promocion.getEstatus() - 1
        );
        cbEmpresa.getSelectionModel().select(
                buscarIdEmpresa(promocion.getEmpresa())
        );

        if (promocion.getImagenBase64() != null && promocion.getImagenBase64().length() > 0) {
            ivPromocion.setImage(Utilidades.decodificarImagenBase64(promocion.getImagenBase64()));
        }
    }

    public int buscarIdTipoPromocion(int idTipoPromocion) {
        for (int i = 0; i < tiposPromocion.size(); i++) {
            if (tiposPromocion.get(i).getIdTipoPromocion() == idTipoPromocion) {
                return i;
            }
        }

        return 0;
    }

    public int buscarIdCategoria(int idCategoria) {
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getIdCategoria() == idCategoria) {
                return i;
            }
        }

        return 0;
    }

    public int buscarIdEmpresa(int idEmpresa) {
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getIdEmpresa() == idEmpresa) {
                return i;
            }
        }

        return 0;
    }

    private void configurarDatePickers() {
        dpFechaInicio.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

        dpFechaTermino.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                LocalDate fechaInicio = dpFechaInicio.getValue();

                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }

                if (fechaInicio != null && date.isBefore(fechaInicio)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

    }

    @FXML
    private void btnBuscarImagen(MouseEvent event) {
        Window ventanaPadre = tfNombre.getScene().getWindow();

        imagen = seleccionarImagen(ventanaPadre);

        if (imagen != null) {
            mostrarEnImageview(imagen);
        }
    }

    private void mostrarEnImageview(File arhivoImagen) {
        try {
            BufferedImage buffer = ImageIO.read(arhivoImagen);
            Image imagen = SwingFXUtils.toFXImage(buffer, null);
            ivPromocion.setImage(imagen);
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Error al cargar",
                    "Error al intentar visualizar la imagen seleccionada",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnRegistrarSucursales(ActionEvent event) {
    }

    @FXML
    private void btnEliminarSucursales(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPromocionSucursal.fxml"));
            Parent vista = loader.load();

            FXMLPromocionSucursalController controlador = loader.getController();

            Stage stage = new Stage();
            Scene escenaFormularioEdicion = new Scene(vista);
            stage.setScene(escenaFormularioEdicion);
            stage.setTitle("Eliminar de sucursal");
            stage.sizeToScene();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarBotonesSucursal() {
        if (promocion == null) {
            btnEliminarSucursales.setVisible(false);
        } else {
            btnEliminarSucursales.setVisible(true);
        }
    }
}

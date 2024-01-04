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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import smartcupon.modelo.dao.DireccionDAO;
import smartcupon.modelo.dao.EmpresaDAO;
import smartcupon.modelo.dao.SucursalDAO;
import smartcupon.modelo.pojo.Ciudad;
import smartcupon.modelo.pojo.DatosSucursal;
import smartcupon.modelo.pojo.Direccion;
import smartcupon.modelo.pojo.Empresa;
import smartcupon.modelo.pojo.Estado;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Persona;
import smartcupon.modelo.pojo.Sucursal;
import smartcupon.modelo.pojo.Usuario;
import smartcupon.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLFormularioSucursalController implements Initializable {

    private DatosSucursal datosSucursal = null;
    private Sucursal sucursal = null;
    private Persona encargado = null;
    private Direccion direccion = null;
    private Integer empresa = null;

    private ObservableList<Estado> estados;
    private ObservableList<Ciudad> ciudades;
    private ObservableList<Empresa> empresas;

    @FXML
    private TextField tfNombreSucursal;
    @FXML
    private TextField tfNombreEncargado;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfLatitud;
    @FXML
    private TextField tfLongitud;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private TextField tfColonia;
    @FXML
    private ComboBox<Estado> cbEstado;
    @FXML
    private ComboBox<Ciudad> cbCiudad;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private ComboBox<Empresa> cbEmpresa;
    @FXML
    private Label lbErrorNombreSucursal;
    @FXML
    private Label lbErrorTelefono;
    @FXML
    private Label lbErrorLatitud;
    @FXML
    private Label lbErrorLongitud;
    @FXML
    private Label lbErrorNombreEncargado;
    @FXML
    private Label lbErrorNombreApellidoPaterno;
    @FXML
    private Label lbErrorNombreApellidoMaterno;
    @FXML
    private Label lbErrorCalle;
    @FXML
    private Label lbErrorNumero;
    @FXML
    private Label lbErrorCodigoPostal;
    @FXML
    private Label lbErrorColonia;
    @FXML
    private Label lbErrorEstado;
    @FXML
    private Label lbErrorCiudad;
    @FXML
    private Label lbErrorEmpresa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ocultarLabelsError();
        this.cargarInformacionEstados();
        this.configurarSeleccionEstado();
        configurarCamposNumericos();
    }

    public void inicializarDatos(Integer idSucursal) {
        //TODO implementar mostrar datos para editar
        datosSucursal = SucursalDAO.obtenerPorId(idSucursal);
        sucursal = datosSucursal.getSucursal();
        direccion = datosSucursal.getDireccion();
        encargado = datosSucursal.getPersona();

        rellenarCampos();

        cbCiudad.setDisable(false);
    }

    public void definirEmpresa(Integer empresa) {
        this.empresa = empresa;
        this.cargarInformacionEmpresas();
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        ocultarLabelsError();

        if (datosSucursal == null) {
            datosSucursal = new DatosSucursal();
            sucursal = new Sucursal();
            direccion = new Direccion();
            encargado = new Persona();
        }

        if (validarDatos()) {
            datosSucursal = llenarDatosSucursal();

            guardarDatos(datosSucursal);
        }
    }

    private void rellenarCampos() {
        tfNombreSucursal.setText(datosSucursal.getSucursal().getNombre());
        tfTelefono.setText(datosSucursal.getSucursal().getTelefono());
        tfLatitud.setText(datosSucursal.getSucursal().getLatitud().toString());
        tfLongitud.setText(datosSucursal.getSucursal().getLongitud().toString());
        int numeroEmpresa = buscarIdEmpresa(datosSucursal.getSucursal().getEmpresa());
        cbEmpresa.getSelectionModel().select(numeroEmpresa);

        tfNombreEncargado.setText(datosSucursal.getPersona().getNombre());
        tfApellidoMaterno.setText(datosSucursal.getPersona().getApellidoMaterno());
        tfApellidoPaterno.setText(datosSucursal.getPersona().getApellidoPaterno());

        tfCalle.setText(datosSucursal.getDireccion().getCalle());
        tfCodigoPostal.setText(datosSucursal.getDireccion().getCodigoPostal());
        tfColonia.setText(datosSucursal.getDireccion().getColonia());
        tfNumero.setText(datosSucursal.getDireccion().getNumero().toString());
        int numeroEstado = buscarIdEstado(datosSucursal.getDireccion().getIdDireccion());
        cbEstado.getSelectionModel().select(numeroEstado);
        int numeroCiudad = buscarIdCiudad(datosSucursal.getDireccion().getCiudad());
        cbCiudad.getSelectionModel().select(numeroCiudad);

    }

    private DatosSucursal llenarDatosSucursal() {
        sucursal.setNombre(tfNombreSucursal.getText());
        sucursal.setTelefono(tfTelefono.getText());
        sucursal.setLatitud(Double.parseDouble(tfLatitud.getText()));
        sucursal.setLongitud(Double.parseDouble(tfLongitud.getText()));
        sucursal.setEmpresa(
                cbEmpresa.getValue().getIdEmpresa()
        );

        encargado.setNombre(tfNombreEncargado.getText());
        encargado.setApellidoPaterno(tfApellidoPaterno.getText());
        encargado.setApellidoMaterno(tfApellidoMaterno.getText());

        direccion.setCalle(tfCalle.getText());
        direccion.setNumero(Integer.parseInt(tfNumero.getText()));
        direccion.setColonia(tfColonia.getText());
        direccion.setCodigoPostal(tfCodigoPostal.getText());
        direccion.setCiudad(
                cbCiudad.getValue().getIdCiudad()
        );

        datosSucursal.setSucursal(sucursal);
        datosSucursal.setPersona(encargado);
        datosSucursal.setDireccion(direccion);

        return datosSucursal;
    }

    private void guardarDatos(DatosSucursal datosSucursal) {
        Mensaje respuesta = null;

        if (datosSucursal.getSucursal().getIdSucursal() == null) {
            respuesta = SucursalDAO.registrarSucursal(datosSucursal);

            if (!respuesta.getError()) {
                Utilidades.mostrarAlertaSimple("Registro exitoso",
                        respuesta.getMensaje(),
                        Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al registrar",
                        respuesta.getMensaje(),
                        Alert.AlertType.INFORMATION);
            }

            limpiarCampos();
        } else {
            respuesta = SucursalDAO.editarSucursal(datosSucursal);

            if (!respuesta.getError()) {
                Utilidades.mostrarAlertaSimple("Edición exitoso",
                        respuesta.getMensaje(),
                        Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al editar",
                        respuesta.getMensaje(),
                        Alert.AlertType.INFORMATION);
            }

            llenarDatosSucursal();
        }
    }

    private void limpiarCampos() {
        tfNombreSucursal.clear();
        tfTelefono.clear();
        tfLatitud.clear();
        tfLongitud.clear();
        tfNombreEncargado.clear();
        tfApellidoPaterno.clear();
        tfApellidoMaterno.clear();
        tfCalle.clear();
        tfNumero.clear();
        tfCodigoPostal.clear();
        tfColonia.clear();
        cbEmpresa.setValue(null);
        cbEstado.setValue(null);
        cbCiudad.setValue(null);
    }

    private void cargarInformacionEstados() {
        estados = FXCollections.observableArrayList();
        List<Estado> listaEstados = DireccionDAO.obtenerEstados();
        estados.addAll(listaEstados);
        cbEstado.setItems(estados);

    }

    private void cargarInformacionEmpresas() {
        if (empresa != null) {
            empresas = FXCollections.observableArrayList();
            Empresa empresa = EmpresaDAO.obtenerDatosEmpresa(this.empresa).getEmpresa();
            List<Empresa> listaEmpresas = new ArrayList<>();
            listaEmpresas.add(empresa);
            empresas.addAll(listaEmpresas);
            cbEmpresa.setItems(empresas);
        } else {
            empresas = FXCollections.observableArrayList();
            List<Empresa> listaEmpresas = EmpresaDAO.obtenerEmpresas();
            empresas.addAll(listaEmpresas);
            cbEmpresa.setItems(empresas);
        }

    }

    private void configurarSeleccionEstado() {
        cbEstado.valueProperty().addListener(new ChangeListener<Estado>() {
            @Override
            public void changed(ObservableValue<? extends Estado> observable, Estado oldValue, Estado newValue) {
                if (newValue != null) {
                    cbCiudad.setDisable(false);
                    cargarInformacionCiudades(newValue.getIdEstado());
                }
            }
        });
    }

    private void cargarInformacionCiudades(Integer idEstado) {
        ciudades = FXCollections.observableArrayList();
        List<Ciudad> listaCiudades = DireccionDAO.obtenerCiudades(idEstado);
        ciudades.addAll(listaCiudades);
        cbCiudad.setItems(ciudades);
    }

    private int buscarIdEstado(Integer idEstado) {
        for (int i = 0; i < estados.size(); i++) {
            if (estados.get(i).getIdEstado() == idEstado) {
                return i;
            }
        }

        return 0;
    }

    private int buscarIdCiudad(Integer idCiudad) {
        for (int i = 0; i < ciudades.size(); i++) {
            if (ciudades.get(i).getIdCiudad() == idCiudad) {
                return i;
            }
        }
        return 0;
    }

    private int buscarIdEmpresa(int idEmpresa) {
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getIdEmpresa() == idEmpresa) {
                return i;
            }
        }

        return 0;
    }

    private boolean validarDatos() {
        boolean valido = true;

        if (tfNombreSucursal.getText().trim().isEmpty()) {
            valido = false;
            lbErrorNombreSucursal.setText("Ingrese nombre de sucursal");
        }

        if (tfTelefono.getText().trim().isEmpty()) {
            valido = false;
            lbErrorTelefono.setText("Ingrese número de teléfono");
        }

        if (tfLatitud.getText().trim().isEmpty()) {
            valido = false;
            lbErrorLatitud.setText("Ingrese latitud");
        }

        if (tfLongitud.getText().trim().isEmpty()) {
            valido = false;
            lbErrorLongitud.setText("Ingrese longitud");
        }

        if (tfNombreEncargado.getText().trim().isEmpty()) {
            valido = false;
            lbErrorNombreEncargado.setText("Ingrese nombre de encargado");
        }

        if (tfApellidoPaterno.getText().trim().isEmpty()) {
            valido = false;
            lbErrorNombreApellidoPaterno.setText("Ingrese apellido paterno");
        }

        if (tfApellidoMaterno.getText().trim().isEmpty()) {
            valido = false;
            lbErrorNombreApellidoMaterno.setText("Ingrese apellido materno");
        }

        if (tfCalle.getText().trim().isEmpty()) {
            valido = false;
            lbErrorCalle.setText("Ingrese calle");
        }

        if (tfNumero.getText().trim().isEmpty()) {
            valido = false;
            lbErrorNumero.setText("Ingrese número");
        }

        if (tfCodigoPostal.getText().trim().isEmpty()) {
            valido = false;
            lbErrorCodigoPostal.setText("Ingrese código postal");
        }

        if (!tfCodigoPostal.getText().trim().isEmpty()
                && tfCodigoPostal.getText().length() != 5) {
            valido = false;
            lbErrorCodigoPostal.setText("El máximo son 5 numeros");
        }

        if (tfColonia.getText().trim().isEmpty()) {
            valido = false;
            lbErrorColonia.setText("Ingrese colonia");
        }

        if (cbEmpresa.getValue() == null) {
            valido = false;
            lbErrorEmpresa.setText("Seleccione empresa");
        }

        if (cbEstado.getValue() == null) {
            valido = false;
            lbErrorEstado.setText("Seleccione estado");
        }

        if (cbCiudad.getValue() == null) {
            valido = false;
            lbErrorCiudad.setText("Seleccione ciudad");
        }

        return valido;
    }

    private void ocultarLabelsError() {
        lbErrorNombreSucursal.setText(null);
        lbErrorTelefono.setText(null);
        lbErrorLatitud.setText(null);
        lbErrorLongitud.setText(null);
        lbErrorNombreEncargado.setText(null);
        lbErrorNombreApellidoPaterno.setText(null);
        lbErrorNombreApellidoMaterno.setText(null);
        lbErrorCalle.setText(null);
        lbErrorNumero.setText(null);
        lbErrorCodigoPostal.setText(null);
        lbErrorColonia.setText(null);
        lbErrorEmpresa.setText(null);
        lbErrorEstado.setText(null);
        lbErrorCiudad.setText(null);
    }

    private void configurarCamposNumericos() {
        tfTelefono.setTextFormatter(Utilidades.configurarFiltroNumerosConLimite(10));
        tfLatitud.setTextFormatter(Utilidades.configurarFiltroNumerosDecimales());
        tfLongitud.setTextFormatter(Utilidades.configurarFiltroNumerosDecimales());
        tfNumero.setTextFormatter(Utilidades.configurarFiltroNumeros());
        tfCodigoPostal.setTextFormatter(Utilidades.configurarFiltroNumerosConLimite(5));
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.escritorio;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import smartcupon.modelo.dao.EmpresaDAO;
import smartcupon.modelo.dao.RolDAO;
import smartcupon.modelo.dao.UsuarioDAO;
import smartcupon.modelo.pojo.Empresa;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Rol;
import smartcupon.modelo.pojo.Usuario;
import smartcupon.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLFormularioUsuarioController implements Initializable {

    private final String ESTILOERROR = "-fx-border-color: red;"
            + "-fx-border-radius: 30;"
            + "-fx-background-color: white;"
            + "-fx-background-radius: 30;";

    private final String ESTILODEFAULT = "-fx-background-color: white;"
            + "-fx-background-radius: 30;";

    private Usuario usuario;
    private ObservableList<Rol> roles = null;
    private ObservableList<Empresa> empresas = null;

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCurp;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPassword;
    @FXML
    private ComboBox<Rol> cbRol;
    @FXML
    private ComboBox<Empresa> cbEmpresaAsociada;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorApellidoPaterno;
    @FXML
    private Label lbErrorApellidoMaterno;
    @FXML
    private Label lbErrorCURP;
    @FXML
    private Label lbErrorUsername;
    @FXML
    private Label lbErrorEmail;
    @FXML
    private Label lbErrorRol;
    @FXML
    private Label lbErrorPassword;
    @FXML
    private Label lbErrorEmpresa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionRoles();
        cargarInformacionEmpresas();
        configurarSeleccionRol();
        ocultarLabelsError();
    }

    public void iniciarVariables(int idUsuario) {
        this.usuario = UsuarioDAO.obtenerPorId(idUsuario);
        cargarInformacionUsuario();
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        if (usuario == null) {
            usuario = new Usuario();
        }

        if (validarDatos()) {
            usuario.setNombre(tfNombre.getText());
            usuario.setApellidoPaterno(tfApellidoPaterno.getText());
            usuario.setApellidoMaterno(tfApellidoMaterno.getText());
            usuario.setCurp(tfCurp.getText());
            usuario.setUsername(tfUsername.getText());
            usuario.setEmail(tfEmail.getText());
            usuario.setPassword(tfPassword.getText());
            usuario.setRol(cbRol.getValue().getIdRol());
            usuario.setEmpresa(
                    (cbRol.getValue().getIdRol() == 1) ? cbEmpresaAsociada.getValue().getIdEmpresa() : null
            );

            guardarDatos(usuario);
        } else {
            Utilidades.mostrarAlertaSimple("Campso vacios",
                    "Llene o corrija los campos antes de guardar",
                    Alert.AlertType.ERROR);
        }
    }

    private void cargarInformacionUsuario() {
        tfNombre.setText(usuario.getNombre());
        tfApellidoPaterno.setText(usuario.getApellidoPaterno());
        tfApellidoMaterno.setText(usuario.getApellidoMaterno());
        tfCurp.setText(usuario.getCurp());
        tfUsername.setText(usuario.getUsername());
        tfEmail.setText(usuario.getEmail());
        tfPassword.setText(usuario.getPassword());

        cbRol.getSelectionModel().select(
                buscarIdRol(
                        usuario.getRol())
        );

        if (usuario.getEmpresa() != null) {
            cbEmpresaAsociada.getSelectionModel().select(
                    buscarIdEmpresa(
                            usuario.getEmpresa())
            );
        }
    }

    private void cargarInformacionRoles() {
        List<Rol> todos = RolDAO.obtenerTodos();

        roles = FXCollections.observableArrayList();
        roles.addAll(todos);

        cbRol.setItems(roles);

        cbEmpresaAsociada.setDisable(true);
    }

    private void cargarInformacionEmpresas() {
        List<Empresa> todas = EmpresaDAO.obtenerEmpresas();

        empresas = FXCollections.observableArrayList();
        empresas.addAll(todas);

        cbEmpresaAsociada.setItems(empresas);
    }

    private int buscarIdRol(int idRol) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getIdRol() == idRol) {
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

    private void configurarSeleccionRol() {
        cbRol.valueProperty().addListener(new ChangeListener<Rol>() {
            @Override
            public void changed(ObservableValue<? extends Rol> observable, Rol oldValue, Rol newValue) {
                if (newValue != null && newValue.getIdRol() == 2) {
                    cbEmpresaAsociada.setDisable(true);
                } else {
                    cbEmpresaAsociada.setDisable(false);
                }
            }
        });
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

        if (tfApellidoPaterno.getText().trim().isEmpty()) {
            valido = false;
            tfApellidoPaterno.setStyle(ESTILOERROR);
            lbErrorApellidoPaterno.setText("Ingrese apellido paterno");
        } else {
            tfApellidoPaterno.setStyle(ESTILODEFAULT);
        }

        if (tfApellidoMaterno.getText().trim().isEmpty()) {
            valido = false;
            tfApellidoMaterno.setStyle(ESTILOERROR);
            lbErrorApellidoMaterno.setText("Ingrese apellido materno");
        } else {
            tfApellidoMaterno.setStyle(ESTILODEFAULT);
        }

        if (!tfCurp.getText().trim().isEmpty()
                && !validarCURP(tfCurp.getText())) {
            valido = false;
            tfCurp.setStyle(ESTILOERROR);
            lbErrorCURP.setText("Ingrese CURP valida");
        } else {
            tfCurp.setStyle(ESTILODEFAULT);
        }

        if (tfCurp.getText().trim().isEmpty()) {
            valido = false;
            tfCurp.setStyle(ESTILOERROR);
            lbErrorCURP.setText("Ingrese CURP");
        } else {
            tfCurp.setStyle(ESTILODEFAULT);
        }

        if (tfUsername.getText().trim().isEmpty()) {
            valido = false;
            tfUsername.setStyle(ESTILOERROR);
            lbErrorUsername.setText("Ingrese username");
        } else {
            tfUsername.setStyle(ESTILODEFAULT);
        }

        if (!tfEmail.getText().trim().isEmpty()
                && !Utilidades.validarEmail(tfEmail.getText())) {
            valido = false;
            tfEmail.setStyle(ESTILOERROR);
            lbErrorEmail.setText("Ingrese email valido");
        } else {
            tfEmail.setStyle(ESTILODEFAULT);
        }

        if (tfEmail.getText().trim().isEmpty()) {
            valido = false;
            tfEmail.setStyle(ESTILOERROR);
            lbErrorEmail.setText("Ingrese email");
        } else {
            tfEmail.setStyle(ESTILODEFAULT);
        }

        if (tfPassword.getText().trim().isEmpty()) {
            valido = false;
            tfPassword.setStyle(ESTILOERROR);
            lbErrorPassword.setText("Ingrese contraseña");
        } else {
            tfPassword.setStyle(ESTILODEFAULT);
        }

        if (cbRol.getValue() == null) {
            valido = false;
            cbRol.setStyle(ESTILOERROR);
            lbErrorRol.setText("Seleccione rol");
        } else {
            cbRol.setStyle(ESTILODEFAULT);
        }

        if (cbRol.getValue() != null
                && cbRol.getSelectionModel().getSelectedItem().getIdRol() == 1
                && cbEmpresaAsociada.getValue() == null) {
            valido = false;
            cbEmpresaAsociada.setStyle(ESTILOERROR);
            lbErrorEmpresa.setText("Seleccione empresa");
        } else {
            cbEmpresaAsociada.setStyle(ESTILODEFAULT);
        }

        return valido;
    }

    private void guardarDatos(Usuario usuario) {
        Mensaje mensaje = null;

        if (usuario.getIdUsuario() == null) {
            mensaje = UsuarioDAO.registrarUsuario(usuario);

            if (!mensaje.getError()) {
                Utilidades.mostrarAlertaSimple("Registro exitoso",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al registrar",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            }

            limpiarDatos();
        } else {
            mensaje = UsuarioDAO.editarUsuario(usuario);

            if (!mensaje.getError()) {
                Utilidades.mostrarAlertaSimple("Registro exitoso",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al editar",
                        mensaje.getMensaje(),
                        Alert.AlertType.INFORMATION);
            }
        }

        cargarInformacionUsuario();
    }

    private void limpiarDatos() {
        tfNombre.setText("");
        tfApellidoPaterno.setText("");
        tfApellidoMaterno.setText("");
        tfCurp.setText("");
        tfUsername.setText("");
        tfEmail.setText("");
        tfPassword.setText("");
        cbEmpresaAsociada.setValue(null);
        cbRol.setValue(null);
    }

    private void ocultarLabelsError() {
        lbErrorNombre.setText(null);
        lbErrorApellidoPaterno.setText(null);
        lbErrorApellidoMaterno.setText(null);
        lbErrorCURP.setText(null);
        lbErrorUsername.setText(null);
        lbErrorEmail.setText(null);
        lbErrorPassword.setText(null);
        lbErrorEmpresa.setText(null);
        lbErrorRol.setText(null);
    }

    private boolean validarCURP(String curp) {
        // Expresión regular para validar CURP
        String expresion = "^[A-Z]{4}[0-9]{6}[HM]{1}[A-Z]{5}[0-9]{2}$";

        Pattern patron = Pattern.compile(expresion);
        Matcher matcher = patron.matcher(curp);

        return matcher.matches();
    }
}

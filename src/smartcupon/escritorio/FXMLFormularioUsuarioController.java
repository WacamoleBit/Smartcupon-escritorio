/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.escritorio;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import smartcupon.modelo.dao.EmpresaDAO;
import smartcupon.modelo.dao.RolDAO;
import smartcupon.modelo.dao.UsuarioDAO;
import smartcupon.modelo.pojo.Empresa;
import smartcupon.modelo.pojo.Rol;
import smartcupon.modelo.pojo.Usuario;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLFormularioUsuarioController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionRoles();
        cargarInformacionEmpresas();
    }

    public void iniciarVariables(int idUsuario) {
        this.usuario = UsuarioDAO.obtenerPorId(idUsuario);
        cargarInformacionUsuario();
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
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
    }

    private void cargarInformacionEmpresas() {
        List<Empresa> todas = EmpresaDAO.obtenerEmpresas();

        empresas = FXCollections.observableArrayList();
        empresas.addAll(todas);

        cbEmpresaAsociada.setItems(empresas);;
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
}

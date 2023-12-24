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
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import smartcupon.modelo.dao.UsuarioDAO;
import smartcupon.modelo.pojo.Usuario;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLAdminUsuariosController implements Initializable {

    private Usuario usuario;
    private ObservableList<Usuario> usuarios;

    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colApellidoPaterno;
    @FXML
    private TableColumn<?, ?> colApellidoMaterno;
    @FXML
    private TableColumn<?, ?> colCurp;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colUsername;
    @FXML
    private TableColumn<?, ?> colPassword;
    @FXML
    private TableColumn<?, ?> colRol;
    @FXML
    private TableColumn<?, ?> colEmpresa;
    @FXML
    private TextField tfBuscarUsuario;
    @FXML
    private TableView<Usuario> tvUsuarios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnasTabla();
        mostrarInformacionUsuarios();
    }

    @FXML
    private void btnRegistrar(ActionEvent event) {
    }

    @FXML
    private void btnEditar(ActionEvent event) {
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
    }

    private void configurarColumnasTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colCurp.setCellValueFactory(new PropertyValueFactory("curp"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colUsername.setCellValueFactory(new PropertyValueFactory("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory("password"));
        colRol.setCellValueFactory(new PropertyValueFactory("nombreRol"));
        colEmpresa.setCellValueFactory(new PropertyValueFactory("nombreEmpresa"));
    }

    public void inicializarInformacion(Usuario usuario) {
        this.usuario = usuario;
        
    }

    private void mostrarInformacionUsuarios() {
        List<Usuario> respuesta = UsuarioDAO.obtenerTodos();

        usuarios = FXCollections.observableArrayList(respuesta);

        tvUsuarios.setItems(usuarios);
    }
}

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smartcupon.modelo.dao.UsuarioDAO;
import smartcupon.modelo.pojo.FiltroBuscarUsuario;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Usuario;
import smartcupon.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLAdminUsuariosController implements Initializable {

    private Usuario usuario;
    private ObservableList<Usuario> usuarios;
    @FXML
    private TableView<Usuario> tvUsuarios;
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
    private TableColumn<?, ?> colRol;
    @FXML
    private TableColumn<?, ?> colEmpresa;
    @FXML
    private TextField tfBuscarUsuario;
    @FXML
    private CheckBox cbPorNombre;
    @FXML
    private CheckBox cbPorUsername;
    @FXML
    private CheckBox cbPorRol;

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
        try {
            FXMLLoader vistaLoader = new FXMLLoader(getClass().getResource("FXMLFormularioUsuario.fxml"));
            Parent vista = vistaLoader.load();

            FXMLFormularioUsuarioController controlador = vistaLoader.getController();

            Stage stage = new Stage();
            Scene escenaAdminUsuarios = new Scene(vista);
            stage.setScene(escenaAdminUsuarios);
            stage.setTitle("Registrar Usuario");
            stage.setResizable(false);
            stage.sizeToScene();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            mostrarInformacionUsuarios();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnEditar(ActionEvent event) {
        Integer idUsuario = (tvUsuarios.getSelectionModel().getSelectedItem() != null)
                ? tvUsuarios.getSelectionModel().getSelectedItem().getIdUsuario() : null;

        if (idUsuario != null) {
            try {

                FXMLLoader vistaLoader = new FXMLLoader(getClass().getResource("FXMLFormularioUsuario.fxml"));
                Parent vista = vistaLoader.load();

                FXMLFormularioUsuarioController controlador = vistaLoader.getController();
                controlador.iniciarVariables(idUsuario);

                Stage stage = new Stage();
                Scene escenaAdminUsuarios = new Scene(vista);
                stage.setScene(escenaAdminUsuarios);
                stage.setTitle("Editar Usuario");
                stage.setResizable(false);
                stage.sizeToScene();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

                mostrarInformacionUsuarios();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            Utilidades.mostrarAlertaSimple("Seleccion de usuario",
                    "Para poder modificar debes seleccionar un usuario de la tabla",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        Mensaje mensaje = null;
        Integer idUsuario = (tvUsuarios.getSelectionModel().getSelectedItem() != null)
                ? tvUsuarios.getSelectionModel().getSelectedItem().getIdUsuario() : null;

        if (idUsuario != null) {
            boolean aceptar = Utilidades.mostrarAlertaConfirmacion(
                    "Eliminar usuario",
                    "¿Estás seguro que quieres eliminar este usuario?");

            if (idUsuario > 0 && aceptar) {
                mensaje = UsuarioDAO.eliminarUsuario(idUsuario);

                if (!mensaje.getError()) {
                    Utilidades.mostrarAlertaSimple("Eliminacion exitosa",
                            mensaje.getMensaje(),
                            Alert.AlertType.INFORMATION);
                } else {
                    Utilidades.mostrarAlertaSimple("Error al eliminar",
                            mensaje.getMensaje(),
                            Alert.AlertType.INFORMATION);
                }
            }
        } else {
            Utilidades.mostrarAlertaSimple("Seleccion de promoción",
                    "Para poder eliminar debes seleccionar un usuario de la tabla",
                    Alert.AlertType.WARNING);
        }

        mostrarInformacionUsuarios();
    }

    @FXML
    private void btnBuscar(ActionEvent event) {
        boolean checkboxActivo = validarCheckBox();

        if (!tfBuscarUsuario.getText().trim().isEmpty() && checkboxActivo) {
            FiltroBuscarUsuario filtro = new FiltroBuscarUsuario();

            filtro.setCadenaBusqueda(tfBuscarUsuario.getText());
            filtro.setPorNombre(cbPorNombre.isSelected());
            filtro.setPorRol(cbPorRol.isSelected());
            filtro.setPorUsername(cbPorUsername.isSelected());

            List<Usuario> lista = UsuarioDAO.buscarPorFiltro(filtro);

            usuarios = FXCollections.observableArrayList(lista);

            tvUsuarios.setItems(usuarios);
        } else {
            mostrarInformacionUsuarios();
        }
    }

    private void configurarColumnasTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colCurp.setCellValueFactory(new PropertyValueFactory("curp"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colUsername.setCellValueFactory(new PropertyValueFactory("username"));
        colRol.setCellValueFactory(new PropertyValueFactory("nombreRol"));
        colEmpresa.setCellValueFactory(new PropertyValueFactory("nombreEmpresa"));
    }

    private void mostrarInformacionUsuarios() {
        tvUsuarios.setItems(null);

        List<Usuario> respuesta = UsuarioDAO.obtenerTodos();

        usuarios = FXCollections.observableArrayList(respuesta);

        tvUsuarios.setItems(usuarios);
    }

    private boolean validarCheckBox() {
        return cbPorNombre.isSelected() || cbPorRol.isSelected() || cbPorUsername.isSelected();
    }

}

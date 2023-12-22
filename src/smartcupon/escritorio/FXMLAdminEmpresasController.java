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
import smartcupon.modelo.dao.EmpresaDAO;
import smartcupon.modelo.pojo.Empresa;

/**
 * FXML Controller class
 *
 * @author jegal
 */
public class FXMLAdminEmpresasController implements Initializable {

    
    private ObservableList<Empresa> empresas;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableView<Empresa> tvEmpresas;
    @FXML
    private TableColumn colNombreComercial;
    @FXML
    private TableColumn colPaginaWeb;
    @FXML
    private TableColumn colRfc;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private TextField tfBuscarEmpresa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        empresas = FXCollections.observableArrayList();
        configurarColumnas();
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

    private void configurarColumnas() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colNombreComercial.setCellValueFactory(new PropertyValueFactory("nombreComercial"));
        colRfc.setCellValueFactory(new PropertyValueFactory("rfc"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
    }
    
    public void consultarEmpresas(){
        List<Empresa> listaEmpresas = EmpresaDAO.obtenerEstados();
        empresas.addAll(listaEmpresas);
        tvEmpresas.setItems(empresas);
    }
}

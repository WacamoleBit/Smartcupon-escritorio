/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.utils;

import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author jegal
 */
public class Utilidades {
    public static void mostrarAlertaSimple(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    public static boolean mostrarAlertaConfirmacion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        // Configurar botones
        ButtonType botonAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(botonAceptar, botonCancelar);

        // Mostrar la ventana y esperar la respuesta del usuario
        Optional<ButtonType> resultado = alerta.showAndWait();
        
        return resultado.orElse(botonCancelar) == botonAceptar;
    }
    
    public static TextFormatter<String> configurarFiltroNumeros() {
        // Se crea el nuevo comportamiento, que se activa cuando se ingresa texto en
        // el componente asignado.
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            
            //Obtiene la cadena de texto que se intenta ingresar
            String cadena = change.getControlNewText();
            
            // Verifica si la cadena contiene solo dígitos
            if (cadena.matches("\\d*")) { // \\d* significa: "cero o mas difitos"
                
                // Si se cumple la condifión, se actaliza el cambio.
                return change;
            } else {
                
                //Si no se cumple, la cadena regresa null y no se actualiza la cadena
                return null;
            }
        };
        
        return new TextFormatter<>(filtro);
    }
    
    public static boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}

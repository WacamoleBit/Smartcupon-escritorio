/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Base64;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Window;

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
            if (cadena.matches("\\d*")) { // \\d* significa: "cero o mas digitos"

                // Si se cumple la condifión, se actaliza el cambio.
                return change;
            } else {

                //Si no se cumple, la cadena regresa null y no se actualiza la cadena
                return null;
            }
        };

        return new TextFormatter<>(filtro);
    }

    public static TextFormatter<String> configurarFiltroNumerosDecimales() {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String cadena = change.getControlNewText();

            // Verifica si la cadena contiene solo dígitos
            if (cadena.matches("\\d*\\.?\\d*")) { // \\d* significa: "cero o mas digitos , \\.? significa: "cero o un punto"
                return change;
            } else {
                return null;
            }
        };

        return new TextFormatter<>(filtro);
    }

    public static TextFormatter<String> configurarFiltroNumerosConLimite(int limite) {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String cadena = change.getControlNewText();
            if (cadena.matches("\\d{0," + limite + "}")) { // \\d{0, 10} significa: "cero o 10 digitos"
                return change;
            } else {
                return null;
            }
        };

        return new TextFormatter<>(filtro);
    }
    
    public static TextFormatter<String> configurarFiltroRFC() {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String nuevaCadena = change.getControlNewText();
            if (nuevaCadena.matches("[A-Z0-9]{0,13}")) {
                return change;
            } else {
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

    public static File seleccionarImagen(Window ventanaPadre) {
        FileChooser dialogoSeleccionImagen = new FileChooser();

        dialogoSeleccionImagen.setTitle("Selecciona una imagen");
        FileChooser.ExtensionFilter filtroArchivos = new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpeg", "*.jpg");

        dialogoSeleccionImagen.getExtensionFilters().add(filtroArchivos);

        File foto = dialogoSeleccionImagen.showOpenDialog(ventanaPadre);

        return foto;
    }

    public static Image decodificarImagenBase64(String imagenBase64) {
        byte[] decodeImage = Base64.getDecoder().decode(imagenBase64.replaceAll("\\n", ""));

        Image imagen = new Image(new ByteArrayInputStream(decodeImage));
        return imagen;
    }
    
    public static boolean validarRFC(String rfc) {
        // Patrón regex para validar RFC
        String patronRFC = "^[A-Z]{4}[0-9]{6}[A-Z0-9]{3}$";

        // Compilar el patrón
        return rfc.matches(patronRFC);
    }
    
    public static boolean validarCalle(String calle) {
        String patronCalle = "^[\\p{L}0-9\\s]+$";

        Pattern pattern = Pattern.compile(patronCalle);

        return pattern.matcher(calle).matches();
    }
    
    public static boolean validarNumerico(String cadena) {
        String patronNumerico = "^[0-9]+$";

        Pattern pattern = Pattern.compile(patronNumerico);

        return pattern.matcher(cadena).matches();
    }
    
    public static boolean validarNombre(String nombre) {
        String patronNombre = "^[\\p{L}]+( [\\p{L}]+)*$";

        Pattern pattern = Pattern.compile(patronNombre);

        return pattern.matcher(nombre).matches();
    }
    
    public static boolean validarPaginaWeb(String paginaWeb) {
        String patronPaginaWeb = "^www\\.[a-zA-Z0-9-]+\\.com$";

        Pattern pattern = Pattern.compile(patronPaginaWeb);

        return pattern.matcher(paginaWeb).matches();
    }
}

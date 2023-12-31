/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import smartcupon.modelo.pojo.CodigoHTTP;
import smartcupon.utils.Constantes;

/**
 *
 * @author jegal
 */
public class ConexionHTTP {

    public static CodigoHTTP peticionGET(String url) {
        CodigoHTTP respuesta = new CodigoHTTP();
        try {
            URL urlServicio = new URL(url);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlServicio.openConnection();

            conexionHTTP.setRequestMethod("GET");

            int codigoRespuesta = conexionHTTP.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);

            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(deserializar(conexionHTTP.getInputStream()));
            } else {
                respuesta.setContenido("CODE ERROR: " + codigoRespuesta);
            }

        } catch (MalformedURLException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("Error :" + ex.getMessage());
        } catch (IOException ioe) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("Error :" + ioe.getMessage());
        }

        return respuesta;
    }

    public static CodigoHTTP peticionPOST(String url, String json) {
        CodigoHTTP respuesta = new CodigoHTTP();

        try {
            URL urlServicio = new URL(url);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlServicio.openConnection();

            conexionHTTP.setRequestMethod("POST");
            conexionHTTP.setRequestProperty("Content-Type", "application/json");
            conexionHTTP.setDoOutput(true);

            //Escribir datos en el cuerpo de la peticion
            OutputStream outputStream = conexionHTTP.getOutputStream();
            outputStream.write(json.getBytes());
            outputStream.flush();
            outputStream.close();
            //Termina la escritura

            int codigoRespuesta = conexionHTTP.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);

            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(deserializar(conexionHTTP.getInputStream()));
            }
        } catch (MalformedURLException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("ERROR: " + ex.getMessage());
        } catch (IOException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("ERROR: " + ex.getMessage());
        }

        return respuesta;
    }

    public static CodigoHTTP peticionPUT(String url, String json) {
        CodigoHTTP respuesta = new CodigoHTTP();

        try {
            URL urlServicio = new URL(url);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlServicio.openConnection();

            conexionHTTP.setRequestMethod("PUT");
            conexionHTTP.setRequestProperty("Content-Type", "application/json");
            conexionHTTP.setDoOutput(true);

            //Escribir datos en el cuerpo de la peticion
            OutputStream outputStream = conexionHTTP.getOutputStream();
            outputStream.write(json.getBytes());
            outputStream.flush();
            outputStream.close();
            //Termina la escritura

            int codigoRespuesta = conexionHTTP.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);

            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(deserializar(conexionHTTP.getInputStream()));
            }
        } catch (MalformedURLException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("ERROR: " + ex.getMessage());
        } catch (IOException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("ERROR: " + ex.getMessage());
        }

        return respuesta;
    }

    public static CodigoHTTP peticionDELETE(String url) {
        CodigoHTTP respuesta = new CodigoHTTP();

        try {
            URL urlServicio = new URL(url);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlServicio.openConnection();

            conexionHTTP.setRequestMethod("DELETE");
            conexionHTTP.setRequestProperty("Content-Type", "application/json");
            conexionHTTP.setDoOutput(true);

            int codigoRespuesta = conexionHTTP.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);

            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(deserializar(conexionHTTP.getInputStream()));
            }
        } catch (MalformedURLException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("ERROR: " + ex.getMessage());
        } catch (IOException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("ERROR: " + ex.getMessage());
        }

        return respuesta;
    }

    private static String deserializar(InputStream contenido) throws IOException {
        InputStreamReader reader = new InputStreamReader(contenido);
        BufferedReader buffer = new BufferedReader(reader);

        String cadenaEntrada;
        StringBuffer cadenaBuffer = new StringBuffer();

        while ((cadenaEntrada = buffer.readLine()) != null) {
            cadenaBuffer.append(cadenaEntrada);
        }

        buffer.close();

        return cadenaBuffer.toString();
    }
    
    public static CodigoHTTP peticionPUTImagen(String url, byte[] imagen){
        CodigoHTTP respuesta = new CodigoHTTP();
       try{
           URL urlServicio = new URL(url);
           HttpURLConnection conexionHTTP = (HttpURLConnection) urlServicio.openConnection();
           conexionHTTP.setRequestMethod("PUT");
           conexionHTTP.setDoOutput(true);
           //Escribir datos en el cuerpo de la peticion
           OutputStream os = conexionHTTP.getOutputStream();
           os.write(imagen);
           os.flush();
           os.close();
           //Termina la escritura
           int codigoRespuesta = conexionHTTP.getResponseCode();
           respuesta.setCodigoRespuesta(codigoRespuesta);
           if(codigoRespuesta == HttpURLConnection.HTTP_OK){
               respuesta.setContenido(convertirContenido(conexionHTTP.getInputStream()));
           }else{
               respuesta.setContenido("CODE ERROR:"+codigoRespuesta);
           }
       }catch(MalformedURLException ex){
           respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
           respuesta.setContenido("Error:"+ex.getMessage());
       }catch(IOException iox){
           respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
           respuesta.setContenido("Error:"+iox.getMessage());
       }
       return respuesta;
    }  
        
       private static String convertirContenido(InputStream contenido) throws IOException{
            InputStreamReader inputLectura = new InputStreamReader(contenido);
            BufferedReader buffer = new BufferedReader(inputLectura);
            String cadenaEntrada;
            StringBuffer cadenaBuffer = new StringBuffer();
            while((cadenaEntrada = buffer.readLine()) != null){
                cadenaBuffer.append(cadenaEntrada);
            }
            buffer.close();
            return cadenaBuffer.toString();
       }

    public static CodigoHTTP peticionDELETE(String url, String json) {
        CodigoHTTP respuesta = new CodigoHTTP();

        try {
            URL urlServicio = new URL(url);
            HttpURLConnection conexionHTTP = (HttpURLConnection) urlServicio.openConnection();

            conexionHTTP.setRequestMethod("DELETE");
            conexionHTTP.setRequestProperty("Content-Type", "application/json");
            conexionHTTP.setDoOutput(true);

            OutputStream outputStream = conexionHTTP.getOutputStream();
            outputStream.write(json.getBytes());
            outputStream.flush();
            outputStream.close();
            
            int codigoRespuesta = conexionHTTP.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);

            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(deserializar(conexionHTTP.getInputStream()));
            }
        } catch (MalformedURLException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("ERROR: " + ex.getMessage());
        } catch (IOException ex) {
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("ERROR: " + ex.getMessage());
        }

        return respuesta;
    }
}

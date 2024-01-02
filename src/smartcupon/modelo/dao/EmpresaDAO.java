/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import smartcupon.modelo.ConexionHTTP;
import smartcupon.modelo.pojo.CodigoHTTP;
import smartcupon.modelo.pojo.DatosEmpresa;
import smartcupon.modelo.pojo.Empresa;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.utils.Constantes;

/**
 *
 * @author Dell
 */
public class EmpresaDAO {
    
    public static List<Empresa> obtenerEmpresas() {
        List<Empresa> empresas = new ArrayList<>();
        String url = Constantes.URL_WS + "empresas/obtenerEmpresas";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaEstados = new TypeToken<List<Empresa>>(){}.getType();
            empresas = gson.fromJson(respuesta.getContenido(), tipoListaEstados);
        }

        return empresas;
    }
    
    public static DatosEmpresa obtenerDatosEmpresa(Integer idEmpresa){
        DatosEmpresa datosEmpresa = new DatosEmpresa();
        String url = Constantes.URL_WS + "empresas/obtenerInformacionEmpresa/" + idEmpresa;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            datosEmpresa = gson.fromJson(respuesta.getContenido(), DatosEmpresa.class);
        }
        
        return datosEmpresa;
    }
    
    public static Mensaje registrarEmpresa(DatosEmpresa datosEmpresa){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "empresas/registrarEmpresa";
        Gson gson = new Gson();
        String json = gson.toJson(datosEmpresa);
        System.out.println(json);
        CodigoHTTP respuesta = ConexionHTTP.peticionPOST(url, json);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            msj.setMensaje("Error en la peticion para registrar la empresa");
        }
        return msj;
    }
    
    public static Mensaje editarEmpresa(DatosEmpresa datosEmpresa){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "empresas/editarEmpresa";
        Gson gson = new Gson();
        String json = gson.toJson(datosEmpresa);
        System.out.println(json);
        CodigoHTTP respuesta = ConexionHTTP.peticionPUT(url, json);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            msj.setMensaje("Error en la peticion para registrar la empresa");
        }
        return msj;
    }
    
    public static Mensaje subirLogoEmpresa(File logo, int idEmpresa){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "empresas/registrarLogo" + idEmpresa;
        try{
            byte[] imagen = Files.readAllBytes(logo.toPath());
            CodigoHTTP respuesta = ConexionHTTP.peticionPUTImagen(url, imagen);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
                msj.setError(true);
                msj.setMensaje("Hubo un error al intentar subir la imagen, por favor intenta de nuevo");
            }
        }catch(IOException e){
            msj.setError(true);
            msj.setMensaje("El archivo seleccionado no puede ser almacenado");
        }
        return msj;
    }
    
    public static Empresa obtenerLogo(int idEmpresa){
        Empresa empresa = null;
        String url = Constantes.URL_WS + "empresas/obtenerLogo/" + idEmpresa;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            empresa = gson.fromJson(respuesta.getContenido(), Empresa.class);
        }
        return empresa;
    }
}

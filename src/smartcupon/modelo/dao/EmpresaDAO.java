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
    
    public static Mensaje registrarEmpresa(DatosEmpresa empresa, File logo){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "empresas/registrarEmpresa";
        try{
            if(logo != null){
                byte[] logoEmpresa = Files.readAllBytes(logo.toPath());
                empresa.getEmpresa().setLogo(logoEmpresa);
            }
            
            Gson gson = new Gson();
            String parametros = gson.toJson(empresa);
            CodigoHTTP respuesta = ConexionHTTP.peticionPOST(url, parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setMensaje("Error en la peticion para regitrar la empresa.");
            }
        }catch(IOException e){
            msj.setError(true);
            msj.setMensaje("El archivo seleccionado no puede ser enviado para su almacenamiento");
        }
        return msj;
    }
    
    public static Mensaje editarEmpresa(DatosEmpresa empresa, File logo){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "empresas/editarEmpresa";
        try{
            if(logo != null){
                byte[] logoEmpresa = Files.readAllBytes(logo.toPath());
                empresa.getEmpresa().setLogo(logoEmpresa);
            }else{
                empresa.getEmpresa().setLogo(null);
            }
            //empresa.getEmpresa().setNombreEstatus(null);
            
            Gson gson = new Gson();
            String parametros = gson.toJson(empresa);
            
            CodigoHTTP respuesta = ConexionHTTP.peticionPUT(url, parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setMensaje("Error en la peticion para editar la empresa.");
            }
        }catch(IOException e){
            msj.setError(true);
            msj.setMensaje("El archivo seleccionado no puede ser enviado para su almacenamiento");
        }
        return msj;
    }
    
    public static Mensaje eliminarEmpresa(DatosEmpresa empresa){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "empresas/eliminarEmpresa";
        Gson gson = new Gson();
        String json = gson.toJson(empresa);
        CodigoHTTP respuesta = ConexionHTTP.peticionDELETE(url, json);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Error en la petición para eliminar la empresa");
        }
        return msj;
    }
    
}

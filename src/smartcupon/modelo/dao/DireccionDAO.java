/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.modelo.dao;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import smartcupon.modelo.ConexionHTTP;
import smartcupon.modelo.pojo.Ciudad;
import smartcupon.modelo.pojo.CodigoHTTP;
import smartcupon.modelo.pojo.Estado;
import smartcupon.utils.Constantes;

/**
 *
 * @author Dell
 */
public class DireccionDAO {
    
    public static List<Estado> obtenerEstados(){
        List<Estado> estados = new ArrayList<>();
        String url = Constantes.URL_WS + "direcciones/obtenerEstados";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaEstados = new TypeToken<List<Estado>>(){}.getType();
            estados = gson.fromJson(respuesta.getContenido(), tipoListaEstados);
        }
        
        
        return estados;
    }
    
    public static List<Ciudad> obtenerCiudades(Integer idEstado){
        List<Ciudad> ciudades = new ArrayList<>();
        String url = Constantes.URL_WS + "direcciones/obtenerCiudades/" + idEstado;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaCiudades = new TypeToken<List<Ciudad>>(){}.getType();
            ciudades = gson.fromJson(respuesta.getContenido(), tipoListaCiudades);
        }
        
        return ciudades;
    }
}

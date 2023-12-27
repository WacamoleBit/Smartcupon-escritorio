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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import smartcupon.modelo.ConexionHTTP;
import smartcupon.modelo.pojo.CodigoHTTP;
import smartcupon.modelo.pojo.DatosSucursal;
import smartcupon.modelo.pojo.Empresa;
import smartcupon.modelo.pojo.Sucursal;
import smartcupon.utils.Constantes;

/**
 *
 * @author Dell
 */
public class SucursalDAO {
    
    public static List<Sucursal> obtenerSucursales(){
        List<Sucursal> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS + "sucursales/obtenerSucursales";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaEstados = new TypeToken<List<Sucursal>>(){}.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaEstados);
        }
        
        return sucursales;
    } 
    
    public static DatosSucursal obtenerDatosSucursal(Integer idSucursal){
        DatosSucursal datosSucursal = new DatosSucursal();
        String url = Constantes.URL_WS + "sucursales/obtenerInformacionSucursal/" + idSucursal;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            datosSucursal = gson.fromJson(respuesta.getContenido(), DatosSucursal.class);
    }
    
        return datosSucursal;
    }
    
}

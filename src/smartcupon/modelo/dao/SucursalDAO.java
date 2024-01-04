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
import smartcupon.modelo.pojo.FiltroBuscarSucursal;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Sucursal;
import smartcupon.utils.Constantes;

/**
 *
 * @author Dell
 */
public class SucursalDAO {

    public static List<Sucursal> obtenerSucursales() {
        List<Sucursal> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS + "sucursales/obtenerSucursales";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaEstados = new TypeToken<List<Sucursal>>() {
            }.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaEstados);
        }

        return sucursales;
    }
    
    public static List<Sucursal> obtenerSucursalesPorIdEmpresa(Integer idEmpresa) {
        List<Sucursal> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS + "sucursales/obtenerSucursalesPorIdEmpresa/" + idEmpresa;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaEstados = new TypeToken<List<Sucursal>>() {
            }.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaEstados);
        }

        return sucursales;
    }

    public static DatosSucursal obtenerPorId(Integer idSucursal) {
        DatosSucursal datosSucursal = new DatosSucursal();
        String url = Constantes.URL_WS + "sucursales/obtenerPorId/" + idSucursal;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            datosSucursal = gson.fromJson(respuesta.getContenido(), DatosSucursal.class);
        }

        return datosSucursal;
    }

    public static Mensaje registrarSucursal(DatosSucursal datosSucursal) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS + "sucursales/registrarSucursal";

        Gson gson = new Gson();
        String json = gson.toJson(datosSucursal);

        CodigoHTTP respuesta = ConexionHTTP.peticionPOST(url, json);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setMensaje("Error en la peticion para registrar la sucursal");
        }

        return mensaje;
    }

    public static Mensaje editarSucursal(DatosSucursal datosSucursal) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS + "sucursales/editarSucursal";

        Gson gson = new Gson();
        String json = gson.toJson(datosSucursal);

        CodigoHTTP respuesta = ConexionHTTP.peticionPUT(url, json);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setMensaje("Error en la petición para registrar la sucursal");
        }

        return mensaje;
    }

    public static Mensaje eliminarSucursal(Integer idSucursal) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS + "sucursales/eliminarSucursal/" + idSucursal;

        Gson gson = new Gson();

        CodigoHTTP respuesta = ConexionHTTP.peticionDELETE(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error en la petición para eliminar la sucursal");
        }

        return mensaje;
    }

    public static List<Sucursal> obtenerPorFiltro(FiltroBuscarSucursal filtro) {
        List<Sucursal> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS + "sucursales/buscarPorFiltro?";

        if (filtro.getNombre() != null) {
            url += "nombre=" + filtro.getNombre();
        }

        if (filtro.getDireccion() != null) {
            url += "direccion=" + filtro.getDireccion();
        }
        
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaSucursal = new TypeToken<List<Sucursal>>() {
            }.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaSucursal);
        }

        return sucursales;
    }

}

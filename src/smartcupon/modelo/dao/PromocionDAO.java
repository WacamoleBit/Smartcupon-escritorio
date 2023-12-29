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
import smartcupon.modelo.pojo.Categoria;
import smartcupon.modelo.pojo.CodigoHTTP;
import smartcupon.modelo.pojo.DatosPromocion;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Promocion;
import smartcupon.modelo.pojo.TipoPromocion;
import smartcupon.utils.Constantes;

/**
 *
 * @author Manuel Hernandez
 */
public class PromocionDAO {

    public static List<Promocion> obtenerPromociones() {
        List<Promocion> promociones = new ArrayList();
        String url = Constantes.URL_WS + "promociones/obtenerPromociones";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type arraylistPromociones = new TypeToken<ArrayList<Promocion>>() {
            }.getType();
            promociones = gson.fromJson(respuesta.getContenido(), arraylistPromociones);
        }

        return promociones;
    }

    public static Mensaje registrarPromocion(Promocion promocion) {
        Mensaje mensaje = new Mensaje();
        DatosPromocion datos = new DatosPromocion();
        datos.setPromocion(promocion);
        String url = Constantes.URL_WS + "promociones/registrarPromocion";

        Gson gson = new Gson();
        String parametros = gson.toJson(datos);

        CodigoHTTP respuesta = ConexionHTTP.peticionPOST(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error en la petición para crear el usuario");
        }

        return mensaje;
    }
    
    public static Mensaje eliminarPromocion(Integer idPromocion) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS + "promociones/eliminarPromocion/" + idPromocion;

        Gson gson = new Gson();

        CodigoHTTP respuesta = ConexionHTTP.peticionDELETE(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error en la petición para eliminar la promoción");
        }

        return mensaje;
    }

    public static List<TipoPromocion> obtenerTiposPromocion() {
        List<TipoPromocion> tiposPromocion = new ArrayList();
        String url = Constantes.URL_WS + "promociones/obtenerTiposPromocion";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type arraylistTiposPromocion = new TypeToken<ArrayList<TipoPromocion>>() {
            }.getType();
            tiposPromocion = gson.fromJson(respuesta.getContenido(), arraylistTiposPromocion);
        }

        return tiposPromocion;
    }

    public static List<Categoria> obtenerCategorias() {
        List<Categoria> categorias = new ArrayList();
        String url = Constantes.URL_WS + "promociones/obtenerCategorias";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type arraylistCategorias = new TypeToken<ArrayList<Categoria>>() {
            }.getType();
            categorias = gson.fromJson(respuesta.getContenido(), arraylistCategorias);
        }

        return categorias;
    }
}

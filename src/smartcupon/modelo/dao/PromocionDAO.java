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
import smartcupon.modelo.pojo.Categoria;
import smartcupon.modelo.pojo.CodigoHTTP;
import smartcupon.modelo.pojo.DatosPromocion;
import smartcupon.modelo.pojo.FiltroBuscarPromocion;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Promocion;
import smartcupon.modelo.pojo.PromocionSucursal;
import smartcupon.modelo.pojo.Sucursal;
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
    
    public static List<Promocion> obtenerPromocionesDisponiblesPorEmpresa(Integer idEmpresa) {
        List<Promocion> promociones = new ArrayList();
        String url = Constantes.URL_WS + "promociones/obtenerPromocionesDisponiblesPorEmpresa/" + idEmpresa;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type arraylistPromociones = new TypeToken<ArrayList<Promocion>>() {
            }.getType();
            promociones = gson.fromJson(respuesta.getContenido(), arraylistPromociones);
        }

        return promociones;
    }
    
    public static List<Sucursal> obtenerSucursalesPorPromocion(Integer idPromocion) {
        List<Sucursal> sucursales = new ArrayList();
        String url = Constantes.URL_WS + "promociones/obtenerSucursalesPorPromocion/" + idPromocion;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type arraylistSucursales = new TypeToken<ArrayList<Sucursal>>() {
            }.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), arraylistSucursales);
        }

        return sucursales;
    }
    
    
    public static List<Sucursal> obtenerSucursalesSinPromocion(Integer idPromocion) {
        List<Sucursal> sucursales = new ArrayList();
        String url = Constantes.URL_WS + "promociones/obtenerSucursalesSinPromocion/" + idPromocion;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type arraylistSucursales = new TypeToken<ArrayList<Sucursal>>() {
            }.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), arraylistSucursales);
        }

        return sucursales;
    }
    
    public static Mensaje eliminarPromocionSucursal(PromocionSucursal promocionSucursal){
        Mensaje mensaje= new Mensaje();
        String url = Constantes.URL_WS + "promociones/obtenerSucursalesPorPromocion/";
        
        
        Gson gson = new Gson();
        String json = gson.toJson(promocionSucursal);
        CodigoHTTP respuesta = ConexionHTTP.peticionDELETE(url, json);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error en la petición para eliminar la sucursal");
        }

        return mensaje;
    }
    
    public static Mensaje registrarPromocionSucursal(PromocionSucursal promocionSucursal){
    
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS + "promociones/registroPromocionSucursal/";
                
        Gson gson = new Gson();
        String json = gson.toJson(promocionSucursal);
        CodigoHTTP respuesta = ConexionHTTP.peticionPOST(url, json);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error en la petición para eliminar la sucursal");
        }

        return mensaje;
    }

    public static Mensaje registrarPromocion(Promocion promocion, File archivoImagen) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS + "promociones/registrarPromocion";

        try {
            if (archivoImagen != null) {
                byte[] imagen = Files.readAllBytes(archivoImagen.toPath());

                promocion.setImagen(imagen);
            }

            DatosPromocion datos = new DatosPromocion();
            datos.setPromocion(promocion);

            Gson gson = new Gson();
            String parametros = gson.toJson(datos);

            CodigoHTTP respuesta = ConexionHTTP.peticionPOST(url, parametros);

            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            } else {
                mensaje.setError(true);
                mensaje.setMensaje("Error en la petición para crear el usuario");
            }
        } catch (IOException ex) {
            mensaje.setError(true);
            mensaje.setMensaje("El archivo seleccionado no puede ser enviado para su almacenamiento");
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

    public static Mensaje editarPromocion(Promocion promocion, File archivoImagen) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS + "promociones/editarPromocion";

        try {
            if (archivoImagen != null) {
                byte[] imagen = Files.readAllBytes(archivoImagen.toPath());

                promocion.setImagen(imagen);
            } else {
                promocion.setImagen(null);
            }

            DatosPromocion datos = new DatosPromocion();
            datos.setPromocion(promocion);

            Gson gson = new Gson();
            String parametros = gson.toJson(datos);

            CodigoHTTP respuesta = ConexionHTTP.peticionPUT(url, parametros);

            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            } else {
                mensaje.setError(true);
                mensaje.setMensaje("Error en la petición para crear el usuario");
            }
        } catch (IOException ex) {
            mensaje.setError(true);
            mensaje.setMensaje("El archivo seleccionado no puede ser enviado para su almacenamiento");
        }

        return mensaje;
    }

    public static Promocion obtenerPorId(Integer idPromocion) {
        Promocion promocion = new Promocion();
        String url = Constantes.URL_WS + "promociones/obtenerPorId/" + idPromocion;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();

            promocion = gson.fromJson(respuesta.getContenido(), Promocion.class);
        }

        return promocion;
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

    public static List<Promocion> buscarPorFiltro(FiltroBuscarPromocion filtro) {
        List<Promocion> promociones = new ArrayList();
        String url = Constantes.URL_WS + "promociones/buscarPorFiltro?";
        if (filtro.getNombre() != null) {
            url += "nombre=" + filtro.getNombre();
        }
        if (filtro.getFecha() != null && filtro.getPorFechaInicio() != null) {
            url += "fecha=" + filtro.getFecha();
            url += "porFechaInicio=" + filtro.getPorFechaInicio();
        }
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type arraylistPromociones = new TypeToken<ArrayList<Promocion>>() {
            }.getType();
            promociones = gson.fromJson(respuesta.getContenido(), arraylistPromociones);
        }

        return promociones;
    }
}

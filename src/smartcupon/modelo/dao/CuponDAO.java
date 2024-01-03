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
import smartcupon.modelo.pojo.CodigoHTTP;
import smartcupon.modelo.pojo.Mensaje;
import smartcupon.modelo.pojo.Promocion;
import smartcupon.utils.Constantes;

/**
 *
 * @author Dell
 */
public class CuponDAO {

    public static List<Promocion> obtenerPromociones(Integer idEmpresa) {
        List<Promocion> promociones = new ArrayList();
        String url = Constantes.URL_WS + "promociones/obtenerPromocionesDisponibles";
        if(idEmpresa!= null && idEmpresa>0){
            url = Constantes.URL_WS + "promociones/obtenerPromocionesDisponiblesPorEmpresa/" + idEmpresa;
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

    public static Mensaje canjearCupon(Promocion promocion) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS + "cupones/canjearCupon";

        Gson gson = new Gson();
        String parametros = gson.toJson(promocion);

        CodigoHTTP respuesta = ConexionHTTP.peticionPUT(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error en la petición para canjear el cupon");
        }

        return mensaje;
    }
}

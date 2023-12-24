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
import smartcupon.modelo.pojo.Rol;
import smartcupon.utils.Constantes;

/**
 *
 * @author Manuel Hernandez
 */
public class RolDAO {

    public static List<Rol> obtenerTodos() {
        List<Rol> roles = new ArrayList<>();
        String url = Constantes.URL_WS + "roles/obtenerRoles";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type arraylistRoles = new TypeToken<ArrayList<Rol>>() {}.getType();
            roles = gson.fromJson(respuesta.getContenido(), arraylistRoles);
        }

        return roles;
    }

}

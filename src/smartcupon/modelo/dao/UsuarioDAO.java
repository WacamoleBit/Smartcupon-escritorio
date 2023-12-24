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
import smartcupon.modelo.pojo.Usuario;
import smartcupon.utils.Constantes;

/**
 *
 * @author Manuel Hernandez
 */
public class UsuarioDAO {

    public static List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String url = Constantes.URL_WS + "usuarios/obtenerTodos";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type arraylistUsuario = new TypeToken<ArrayList<Usuario>>(){}.getType();
            usuarios = gson.fromJson(respuesta.getContenido(), arraylistUsuario);
        }
        
        return usuarios;
    }
    
    public static Usuario obtenerPorId(int idUsuario) {
        Usuario usuario = new Usuario();
        String url = Constantes.URL_WS + "usuarios/obtenerPorId/" + idUsuario;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            
            usuario = gson.fromJson(respuesta.getContenido(), Usuario.class);
        }
        
        return usuario;
    }
}

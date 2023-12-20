/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.modelo.dao;

import com.google.gson.Gson;
import java.net.HttpURLConnection;
import smartcupon.modelo.ConexionHTTP;
import smartcupon.modelo.pojo.CodigoHTTP;
import smartcupon.modelo.pojo.RespuestaInicioSesion;
import smartcupon.modelo.pojo.Usuario;
import smartcupon.utils.Constantes;

/**
 *
 * @author jegal
 */
public class InicioSesionDAO {

    public static RespuestaInicioSesion validarSesion(String username, String password) {
        RespuestaInicioSesion respuestaWS = new RespuestaInicioSesion();
        Gson gson = new Gson();
        Usuario usuario = new Usuario();
        
        usuario.setUsername(username);
        usuario.setPassword(password);
        
        String url = Constantes.URL_WS + "autenticacion/inicioSesionUsuario";
        String parametros = gson.toJson(usuario);
        
        CodigoHTTP respuestaConexion = ConexionHTTP.peticionPOST(url, parametros);

        if (respuestaConexion.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuestaWS = gson.fromJson(respuestaConexion.getContenido(), RespuestaInicioSesion.class);
        } else {
            respuestaWS.setError(true);
            respuestaWS.setContenido("Error: Hubo un error al realiar la peticion, por vafor intentelo mas tarde");
        }

        return respuestaWS;
    }
}

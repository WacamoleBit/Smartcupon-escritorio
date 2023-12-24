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
import smartcupon.modelo.pojo.DatosEmpresa;
import smartcupon.modelo.pojo.Empresa;
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
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.modelo.pojo;

/**
 *
 * @author jegal
 */
public class RespuestaInicioSesion {
    
    private String error;
    private String contenido;
    private Usuario usuarioSesion;

    public RespuestaInicioSesion() {
    }

    public RespuestaInicioSesion(String error, String contenido, Usuario usuarioSesion) {
        this.error = error;
        this.contenido = contenido;
        this.usuarioSesion = usuarioSesion;
    }

    public String getError() {
        return error;
    }

    public String getContenido() {
        return contenido;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }
    
}

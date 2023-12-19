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

    private Boolean error;
    private String contenido;
    private Usuario usuario;

    public RespuestaInicioSesion() {
    }

    public RespuestaInicioSesion(Boolean error, String contenido, Usuario usuarioSesion) {
        this.error = error;
        this.contenido = contenido;
        this.usuario = usuarioSesion;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuarioSesion) {
        this.usuario = usuarioSesion;
    }

}

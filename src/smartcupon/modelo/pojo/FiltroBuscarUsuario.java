/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.modelo.pojo;

/**
 *
 * @author Manuel Hernandez
 */
public class FiltroBuscarUsuario {

    private String cadenaBusqueda;
    private boolean porNombre;
    private boolean porUsername;
    private boolean porRol;

    public FiltroBuscarUsuario() {
    }

    public FiltroBuscarUsuario(String cadenaBusqueda, boolean porNombre, boolean porUsername, boolean porRol) {
        this.cadenaBusqueda = cadenaBusqueda;
        this.porNombre = porNombre;
        this.porUsername = porUsername;
        this.porRol = porRol;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public boolean isPorNombre() {
        return porNombre;
    }

    public void setPorNombre(boolean porNombre) {
        this.porNombre = porNombre;
    }

    public boolean isPorUsername() {
        return porUsername;
    }

    public void setPorUsername(boolean porUsername) {
        this.porUsername = porUsername;
    }

    public boolean isPorRol() {
        return porRol;
    }

    public void setPorRol(boolean porRol) {
        this.porRol = porRol;
    }

}

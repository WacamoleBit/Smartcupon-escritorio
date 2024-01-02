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
public class FiltroBuscarPromocion {

    private String fecha;
    private Boolean porFechaInicio;
    private String nombre;

    public FiltroBuscarPromocion() {
    }

    public FiltroBuscarPromocion(String fecha, Boolean porFechaInicio, String nombre) {
        this.fecha = fecha;
        this.porFechaInicio = porFechaInicio;
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Boolean getPorFechaInicio() {
        return porFechaInicio;
    }

    public void setPorFechaInicio(Boolean porFechaInicio) {
        this.porFechaInicio = porFechaInicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}

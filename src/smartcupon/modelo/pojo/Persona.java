/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcupon.modelo.pojo;

/**
 *
 * @author Dell
 */
public class Persona {
    private Integer idPersona;
    private String nombre;
    private String apellidoPäterno;
    private String apellidoMaterno;
    private Integer tipoPersona;
    private String nombreTipoPersona;

    public Persona() {
    }

    public Persona(Integer idPersona, String nombre, String apellidoPäterno, String apellidoMaterno, Integer tipoPersona, String nombreTipoPersona) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellidoPäterno = apellidoPäterno;
        this.apellidoMaterno = apellidoMaterno;
        this.tipoPersona = tipoPersona;
        this.nombreTipoPersona = nombreTipoPersona;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPäterno() {
        return apellidoPäterno;
    }

    public void setApellidoPäterno(String apellidoPäterno) {
        this.apellidoPäterno = apellidoPäterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Integer getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(Integer tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getNombreTipoPersona() {
        return nombreTipoPersona;
    }

    public void setNombreTipoPersona(String nombreTipoPersona) {
        this.nombreTipoPersona = nombreTipoPersona;
    }
    
    
}

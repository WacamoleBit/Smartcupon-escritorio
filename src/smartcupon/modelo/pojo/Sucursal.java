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
public class Sucursal {

    private Integer idSucursal;
    private String nombre;
    private Integer direccion;
    private String telefono;
    private Double latitud;
    private Double longitud;
    private Integer encargado;
    private Integer empresa;

    public Sucursal() {
    }

    public Sucursal(Integer idSucursal, String nombre, Integer direccion, String telefono, Double latitud, Double longitud, Integer encargado, Integer empresa) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.encargado = encargado;
        this.empresa = empresa;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDireccion() {
        return direccion;
    }

    public void setDireccion(Integer direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Integer getEncargado() {
        return encargado;
    }

    public void setEncargado(Integer encargado) {
        this.encargado = encargado;
    }

    public Integer getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Integer empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return nombre;
    }

}

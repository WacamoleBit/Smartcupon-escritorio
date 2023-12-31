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
public class DatosSucursal {

    private Sucursal sucursal;
    private Persona persona;
    private Direccion direccion;
    private Integer filasAfectadas;
    private String error;

    public DatosSucursal() {
    }

    public DatosSucursal(Sucursal sucursal, Persona persona, Direccion direccion, Integer filasAfectadas, String error) {
        this.sucursal = sucursal;
        this.persona = persona;
        this.direccion = direccion;
        this.filasAfectadas = filasAfectadas;
        this.error = error;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Integer getFilasAfectadas() {
        return filasAfectadas;
    }

    public void setFilasAfectadas(Integer filasAfectadas) {
        this.filasAfectadas = filasAfectadas;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}

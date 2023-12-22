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
public class DatosEmpresa {
    
    private Empresa empresa;
    private Persona persona;
    private Direccion direccion;
    private Integer filasAfectadas;
    private String error;

    public DatosEmpresa() {
    }

    public DatosEmpresa(Empresa empresa, Persona persona, Direccion direccion, Integer filasAfectadas, String error) {
        this.empresa = empresa;
        this.persona = persona;
        this.direccion = direccion;
        this.filasAfectadas = filasAfectadas;
        this.error = error;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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

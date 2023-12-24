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
public class Empresa {
    private Integer idEmpresa;
    private String nombre;
    private String nombreComercial;
    private byte [] logo;
    private Integer representante;
    private String email;
    private Integer direccion;
    private String telefono;
    private String paginaWeb;
    private String rfc;
    private Integer idEstatus;
    private String nombreEstatus;
    private String logoBase64;

    public Empresa() {
    }

    public Empresa(Integer idEmpresa, String nombre, String nombreComercial, byte[] logo, Integer representante, String email, Integer direccion, String telefono, String paginaWeb, String rfc, Integer idEstatus, String nombreEstatus) {
        this.idEmpresa = idEmpresa;
        this.nombre = nombre;
        this.nombreComercial = nombreComercial;
        this.logo = logo;
        this.representante = representante;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
        this.rfc = rfc;
        this.idEstatus = idEstatus;
        this.nombreEstatus = nombreEstatus;
    }    

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public Integer getRepresentante() {
        return representante;
    }

    public void setRepresentante(Integer representante) {
        this.representante = representante;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getLogoBase64() {
        return logoBase64;
    }

    public void setLogoBase64(String logoBase64) {
        this.logoBase64 = logoBase64;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getNombreEstatus() {
        return nombreEstatus;
    }

    public void setNombreEstatus(String nombreEstatus) {
        this.nombreEstatus = nombreEstatus;
    }
}

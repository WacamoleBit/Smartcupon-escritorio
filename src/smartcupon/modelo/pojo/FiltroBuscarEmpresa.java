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
public class FiltroBuscarEmpresa {

    private String cadena;
    private Boolean porNombre;
    private Boolean porRFC;
    private Boolean porRepresentante;

    public FiltroBuscarEmpresa() {
    }

    public FiltroBuscarEmpresa(String cadena, Boolean porNombre, Boolean porRFC, Boolean porRepresentante) {
        this.cadena = cadena;
        this.porNombre = porNombre;
        this.porRFC = porRFC;
        this.porRepresentante = porRepresentante;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public Boolean getPorNombre() {
        return porNombre;
    }

    public void setPorNombre(Boolean porNombre) {
        this.porNombre = porNombre;
    }

    public Boolean getPorRFC() {
        return porRFC;
    }

    public void setPorRFC(Boolean porRFC) {
        this.porRFC = porRFC;
    }

    public Boolean getPorRepresentante() {
        return porRepresentante;
    }

    public void setPorRepresentante(Boolean porRepresentante) {
        this.porRepresentante = porRepresentante;
    }

}

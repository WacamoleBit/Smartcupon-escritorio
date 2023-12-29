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
public class DatosPromocion {

    private Promocion promocion;
    private Integer filasAfectadas;
    private String error;

    public DatosPromocion() {
    }

    public DatosPromocion(Promocion promocion, Integer filasAfectadas, String error) {
        this.promocion = promocion;
        this.filasAfectadas = filasAfectadas;
        this.error = error;
    }

    public Promocion getPromocion() {
        return promocion;
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
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

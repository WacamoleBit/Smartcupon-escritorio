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
public class PromocionSucursal {
        private Integer idSucursal;
    private Integer idPromocion;

    public PromocionSucursal() {
    }

    public PromocionSucursal(Integer idSucursal, Integer idPromocion) {
        this.idSucursal = idSucursal;
        this.idPromocion = idPromocion;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }
    
}

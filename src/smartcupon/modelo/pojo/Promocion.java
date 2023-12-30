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
public class Promocion {

    private Integer idPromocion;
    private String nombre;
    private String descripcion;
    private byte[] imagen;
    private String imagenBase64;
    private String fechaInicio;
    private String fechaTermino;
    private String restricciones;
    private Integer tipoPromocion;
    private String tipoPromocionNombre;
    private Integer porcentajeDescuento;
    private Integer categoria;
    private String categoriaNombre;
    private Integer cuponesDisponibles;
    private Integer maximoCupones;
    private String codigoPromocion;
    private Integer estatus;
    private String estatusNombre;
    private Integer empresa;
    private String empresaNombre;

    public Promocion() {
    }

    public Promocion(Integer idPromocion, String nombre, String descripcion, byte[] imagen, String imagenBase64, String fechaInicio, String fechaTermino, String restricciones, Integer tipoPromocion, String tipoPromocionNombre, Integer porcentajeDescuento, Integer categoria, String categoriaNombre, Integer cuponesDisponibles, Integer maximoCupones, String codigoPromocion, Integer estatus, String estatusNombre, Integer empresa, String empresaNombre) {
        this.idPromocion = idPromocion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.imagenBase64 = imagenBase64;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.restricciones = restricciones;
        this.tipoPromocion = tipoPromocion;
        this.tipoPromocionNombre = tipoPromocionNombre;
        this.porcentajeDescuento = porcentajeDescuento;
        this.categoria = categoria;
        this.categoriaNombre = categoriaNombre;
        this.cuponesDisponibles = cuponesDisponibles;
        this.maximoCupones = maximoCupones;
        this.codigoPromocion = codigoPromocion;
        this.estatus = estatus;
        this.estatusNombre = estatusNombre;
        this.empresa = empresa;
        this.empresaNombre = empresaNombre;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public String getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(String restricciones) {
        this.restricciones = restricciones;
    }

    public Integer getTipoPromocion() {
        return tipoPromocion;
    }

    public void setTipoPromocion(Integer tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }

    public String getTipoPromocionNombre() {
        return tipoPromocionNombre;
    }

    public void setTipoPromocionNombre(String tipoPromocionNombre) {
        this.tipoPromocionNombre = tipoPromocionNombre;
    }

    public Integer getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Integer porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public Integer getCuponesDisponibles() {
        return cuponesDisponibles;
    }

    public void setCuponesDisponibles(Integer cuponesDisponibles) {
        this.cuponesDisponibles = cuponesDisponibles;
    }

    public Integer getMaximoCupones() {
        return maximoCupones;
    }

    public void setMaximoCupones(Integer maximoCupones) {
        this.maximoCupones = maximoCupones;
    }

    public String getCodigoPromocion() {
        return codigoPromocion;
    }

    public void setCodigoPromocion(String codigoPromocion) {
        this.codigoPromocion = codigoPromocion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getEstatusNombre() {
        return estatusNombre;
    }

    public void setEstatusNombre(String estatusNombre) {
        this.estatusNombre = estatusNombre;
    }

    public Integer getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Integer empresa) {
        this.empresa = empresa;
    }

    public String getEmpresaNombre() {
        return empresaNombre;
    }

    public void setEmpresaNombre(String empresaNombre) {
        this.empresaNombre = empresaNombre;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

/**
 *
 * @author Jesus
 */
public class ProductoBean {
    private int id;
    private String codigo;
    private String desc;
    private int existencias;
    private float precio;
    private String foto;
    private TipoproductoBean obj_TipoproductoBean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public TipoproductoBean getObj_TipoproductoBean() {
        return obj_TipoproductoBean;
    }

    public void setObj_TipoproductoBean(TipoproductoBean obj_TipoproductoBean) {
        this.obj_TipoproductoBean = obj_TipoproductoBean;
    }
}

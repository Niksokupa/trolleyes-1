/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

import com.google.gson.annotations.Expose;
import net.daw.bean.genericBeanInterface.GenericBeanImplementation;
import net.daw.bean.publicBeanInterface.BeanInterface;


/**
 *
 * @author a044525499y
 */
public class CarritoBean extends GenericBeanImplementation implements BeanInterface {
    @Expose
    private ProductoBean obj_producto;
    @Expose
    private int cantidad;

    public ProductoBean getObj_producto() {
        return obj_producto;
    }

    public void setObj_producto(ProductoBean obj_producto) {
        this.obj_producto = obj_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

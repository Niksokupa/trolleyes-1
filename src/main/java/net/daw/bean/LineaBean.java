/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.daw.dao.FacturaDao;
import net.daw.dao.ProductoDao;

/**
 *
 * @author Jesus
 */
public class LineaBean {

    private int id;
    private int cantidad;
    private ProductoBean obj_producto;
    private FacturaBean obj_factura;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ProductoBean getObj_producto() {
        return obj_producto;
    }

    public void setObj_producto(ProductoBean obj_producto) {
        this.obj_producto = obj_producto;
    }

    public FacturaBean getObj_factura() {
        return obj_factura;
    }

    public void setObj_factura(FacturaBean obj_factura) {
        this.obj_factura = obj_factura;
    }

    public LineaBean fill(ResultSet oResultSet, Connection oConnection, Integer expandProducto, Integer expandFactura) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setCantidad(oResultSet.getInt("cantidad"));
        if (expandProducto > 0) {
            ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
            this.setObj_producto(oProductoDao.get(oResultSet.getInt("id_producto"), expandProducto));
        }
        if (expandFactura > 0) {
            FacturaDao oFacturaDao = new FacturaDao(oConnection, "factura");
            this.setObj_factura(oFacturaDao.get(oResultSet.getInt("id_factura"), expandFactura));
        }

        return this;
    }

    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "cantidad,";
        strColumns += "id_producto,";
        strColumns += "id_factura";
        return strColumns;
    }

    public String getValues() {
        String strColumns = "";
        strColumns += "null,";
        strColumns += cantidad + ",";
        strColumns += obj_producto.getId() + ",";
        strColumns += obj_factura.getId();
        return strColumns;
    }

    public String getPairs() {
        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "cantidad=" + cantidad + ",";
        strPairs += "id_producto=" + obj_producto.getId() + ",";
        strPairs += "id_factura=" + obj_factura.getId();
        strPairs += " WHERE id=" + id;
        return strPairs;
    }
}

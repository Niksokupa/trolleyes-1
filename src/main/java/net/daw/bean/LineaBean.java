/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.daw.bean.genericBeanInterface.GenericBeanImplementation;
import net.daw.bean.publicBeanInterface.BeanInterface;
import net.daw.dao.FacturaDao;
import net.daw.dao.ProductoDao;
import net.daw.dao.publicDaoInterface.DaoInterface;
import net.daw.factory.DaoFactory;

/**
 *
 * @author Ramón
 */
public class LineaBean extends GenericBeanImplementation implements BeanInterface {

    @Expose
    private int cantidad;
    @Expose
    private int id_producto;
    @Expose
    private int id_factura;
    @Expose
    private ProductoBean obj_producto;
    @Expose
    private FacturaBean obj_factura;

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
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

    @Override
    public LineaBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setCantidad(oResultSet.getInt("cantidad"));
        if (expand > 0) {
            DaoInterface oProductoDao = DaoFactory.getDao(oConnection, "producto");
            this.setObj_producto((ProductoBean) oProductoDao.get(oResultSet.getInt("id_producto"), expand));
            DaoInterface oFacturaDao = DaoFactory.getDao(oConnection, "factura");
            this.setObj_factura((FacturaBean) oFacturaDao.get(oResultSet.getInt("id_factura"), expand));
            expand--;
        }
        return this;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "cantidad,";
        strColumns += "id_producto,";
        strColumns += "id_factura";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += "null,";
        strColumns += cantidad + ",";
        strColumns += id_producto + ",";
        strColumns += id_factura;
        return strColumns;
    }

    @Override
    public String getPairs() {
        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "cantidad=" + cantidad + ",";
        strPairs += "id_producto=" + id_producto + ",";
        strPairs += "id_factura=" + id_factura;
        strPairs += " WHERE id=" + id;
        return strPairs;
    }
}

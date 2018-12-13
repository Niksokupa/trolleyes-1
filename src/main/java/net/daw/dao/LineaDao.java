/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import net.daw.bean.FacturaBean;
import net.daw.bean.LineaBean;
import net.daw.bean.ProductoBean;
import net.daw.bean.TipousuarioBean;
import net.daw.dao.genericDaoImplementation.GenericDaoImplementation;
import net.daw.dao.publicDaoInterface.DaoInterface;
import net.daw.helper.SqlBuilder;

/**
 *
 * @author Jesus
 */
public class LineaDao extends GenericDaoImplementation implements DaoInterface {

    public LineaDao(Connection oConnection, String ob) {
        super(oConnection, ob);
    }

    public int getcountspecific(int id) throws Exception {
        String strSQL = "SELECT COUNT(id) FROM " + ob + " WHERE id_factura=?";
        int res = 0;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                res = oResultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao getcountspecific de " + ob, e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return res;
    }

    public ArrayList<LineaBean> getpagespecific(int iRpp, int iPage, HashMap<String, String> hmOrder, int id, Integer expand) throws Exception {
        boolean vacio = false;
        String strSQL = "SELECT * FROM " + ob + " WHERE id_factura=?";
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<LineaBean> alLineaBean;
        if (iRpp > 0 && iRpp < 100000 && iPage > 0 && iPage < 100000000) {
            strSQL += " LIMIT " + (iPage - 1) * iRpp + ", " + iRpp;
            ResultSet oResultSet = null;
            PreparedStatement oPreparedStatement = null;
            try {
                oPreparedStatement = oConnection.prepareStatement(strSQL);
                oPreparedStatement.setInt(1, id);
                oResultSet = oPreparedStatement.executeQuery();
                alLineaBean = new ArrayList<LineaBean>();
                while (oResultSet.next()) {
                    LineaBean oLineaBean = new LineaBean();
                    oLineaBean.fill(oResultSet, oConnection, expand);
                    alLineaBean.add(oLineaBean);
                    vacio = true;
                }
                /*
                 *Si no tiene facturas me devuelve al menos el pojo de usuario
                 *porque quiero mostrar sus datos igualmente. 
                 */
                if (!vacio) {
                    alLineaBean = new ArrayList<LineaBean>();
                    LineaBean oLineaBean = new LineaBean();
                    oLineaBean.setCantidad(0);
                    oLineaBean.setObj_producto(null);
                    FacturaDao oFacturaDao = new FacturaDao(oConnection, "factura");
                    oLineaBean.setObj_factura((FacturaBean) oFacturaDao.get(id, 1));
                    alLineaBean.add(oLineaBean);
                }

            } catch (SQLException e) {
                throw new Exception("Error en Dao getpage de " + ob, e);
            } finally {
                if (oResultSet != null) {
                    oResultSet.close();
                }
                if (oPreparedStatement != null) {
                    oPreparedStatement.close();
                }
            }
        } else {
            throw new Exception("Error en Dao getpage de " + ob);
        }
        return alLineaBean;

    }

}

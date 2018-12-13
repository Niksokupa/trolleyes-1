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
import net.daw.bean.UsuarioBean;
import net.daw.dao.genericDaoImplementation.GenericDaoImplementation;
import net.daw.dao.publicDaoInterface.DaoInterface;

/**
 *
 * @author Ramón
 */
public class UsuarioDao extends GenericDaoImplementation implements DaoInterface{

    public UsuarioDao(Connection oConnection, String ob) {
        super(oConnection, ob);
    }

    public UsuarioBean login(String strUserName, String strPassword) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE login=? AND pass=?";
        UsuarioBean oUsuarioBean;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setString(1, strUserName);
            oPreparedStatement.setString(2, strPassword);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                oUsuarioBean = new UsuarioBean();
                oUsuarioBean.fill(oResultSet, oConnection, 1);
            } else {
                oUsuarioBean = null;
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao get de " + ob, e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return oUsuarioBean;
    }

    public void updatePass(String lastPass, String newPass, UsuarioBean usuarioSession) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE id=" + usuarioSession.getId();
        UsuarioBean oUsuarioBean = null;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oResultSet = oPreparedStatement.executeQuery();
            while (oResultSet.next()) {
                oUsuarioBean = new UsuarioBean();
                oUsuarioBean.fill(oResultSet, oConnection, 1);
            }

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        if (!lastPass.equals(oUsuarioBean.getPass())) {
            throw new Exception("La contraseña antigua no coincide.");
        } else if (newPass.equals(usuarioSession.getPass())) {
            throw new Exception("Pass nueva igual a pass antigua");
        } else {
            usuarioSession.setPass(newPass);
            update(usuarioSession);
        }
    }

}

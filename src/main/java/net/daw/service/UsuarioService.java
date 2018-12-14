/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import net.daw.bean.ReplyBean;
import net.daw.bean.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.UsuarioDao;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.EncodingHelper;
import net.daw.helper.ParameterCook;
import net.daw.service.genericServiceImplementation.GenericServiceImplementation;
import net.daw.service.publicServiceInterface.ServiceInterface;

/**
 *
 * @author Ramón
 */
public class UsuarioService extends GenericServiceImplementation implements ServiceInterface {

    public UsuarioService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }

    public ReplyBean fill() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer number = Integer.parseInt(oRequest.getParameter("number"));
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            UsuarioBean oUsuarioBean = new UsuarioBean();
            for (int i = 1; i <= number; i++) {
                oUsuarioBean.setDni("765934875A");
                oUsuarioBean.setNombre("Rigoberto");
                oUsuarioBean.setApe1("Pérez");
                oUsuarioBean.setApe2("Gómez");
                oUsuarioBean.setLogin("ripego");
                oUsuarioBean.setPass("hola");
                oUsuarioBean.setId_tipoUsuario(2);
                oUsuarioBean = (UsuarioBean) oUsuarioDao.create(oUsuarioBean);
            }
            oReplyBean = new ReplyBean(200, oGson.toJson(number));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean login() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        String strLogin = oRequest.getParameter("user");
        String strPassword = oRequest.getParameter("pass");

        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            UsuarioBean oUsuarioBean = oUsuarioDao.login(strLogin, strPassword);
            if (oUsuarioBean != null) {
                oRequest.getSession().setAttribute("user", oUsuarioBean);
                Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
            } else {
                //throw new Exception("ERROR Bad Authentication: Service level: get page: " + ob + " object");
                oReplyBean = new ReplyBean(401, "Bad Authentication");
            }
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: login method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean logout() throws Exception {
        oRequest.getSession().invalidate();
        return new ReplyBean(200, EncodingHelper.quotate("OK"));
    }

    public ReplyBean check() throws Exception {
        ReplyBean oReplyBean;
        UsuarioBean oUsuarioBean;
        oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");

        if (oUsuarioBean != null) {
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
        } else {
            oReplyBean = new ReplyBean(401, "No active session");
        }
        return oReplyBean;
    }

    public ReplyBean updatePass() throws Exception {
        Gson oGson = new Gson();
        ReplyBean oReplyBean = null;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        UsuarioBean oUsuarioBeanSession;
        oUsuarioBeanSession = (UsuarioBean) oRequest.getSession().getAttribute("user");
        String lastPass = oRequest.getParameter("lastpass");
        String newPass = oRequest.getParameter("newpass");
        if (oUsuarioBeanSession != null) {
            try {
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, "usuario");
                oUsuarioDao.updatePass(lastPass, newPass, oUsuarioBeanSession);
            } catch (Exception e) {
                oReplyBean = new ReplyBean(500, e.getMessage());
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(200, oGson.toJson("Pass updated"));
        }
        return oReplyBean;
    }

}

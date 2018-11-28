/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import com.google.gson.Gson;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.daw.bean.CarritoBean;
import net.daw.bean.ProductoBean;
import net.daw.bean.ReplyBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.ProductoDao;
import net.daw.factory.ConnectionFactory;

/**
 *
 * @author Ramón
 */

/*
El porque de implementar Serializable en la clase CarritoService 
http://chuwiki.chuidiang.org/index.php?title=Serializaci%C3%B3n_de_objetos_en_java
 */
public class CarritoService implements Serializable {

    HttpServletRequest oRequest;
    String ob = null;

    public CarritoService(HttpServletRequest oRequest) {
        super();
        this.oRequest = oRequest;
        ob = oRequest.getParameter("ob");
    }

    public ReplyBean add() throws Exception {
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        Gson oGson = new Gson();
        ArrayList<CarritoBean> alCarritoBeans = new ArrayList<CarritoBean>();
        CarritoBean oCarritoBean = new CarritoBean();
        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();

            Integer idProducto = Integer.parseInt(oRequest.getParameter("productos"));
            Integer cantidad = Integer.parseInt(oRequest.getParameter("cantidad"));

            ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
            ProductoBean oProductoBean = oProductoDao.get(idProducto, 1);

            //Esto no hace falta hacerlo pero esta muy bien porque asi se ve lo que contiene el objeto sesion y
            //que cosas puedo sacar de el
            HttpSession session = oRequest.getSession();
            //Aqui es donde el implements de la clase serializable hace su trabajo (o eso creo)
            ArrayList<CarritoBean> productosGuardados = (ArrayList<CarritoBean>) session.getAttribute("productos");

            //No puedo hacer una iteracion de un objeto en null, porque salta excepcion
            if (productosGuardados != null) {
                for (CarritoBean o : productosGuardados) {
                    if (oProductoBean.getId() == o.getObj_producto().getId()) {
                        cantidad++;
                    } else {
                        alCarritoBeans.add(o);
                    }
                }
            }
            oCarritoBean.setCantidad(cantidad);
            oCarritoBean.setObj_producto(oProductoBean);
            

            alCarritoBeans.add(oCarritoBean);

            oRequest.getSession().setAttribute("productos", alCarritoBeans);
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: add method: " + ob + " object: " + ex);
        } finally {
            oConnectionPool.disposeConnection();
        }

        return new ReplyBean(200, oGson.toJson(oRequest.getSession()));

    }

    public ReplyBean empty() {
        Gson oGson = new Gson();
        oRequest.getSession().setAttribute("productos", null);
        return new ReplyBean(200, oGson.toJson(oRequest.getSession()));
    }

    public ReplyBean show() throws Exception {
        Gson oGson = new Gson();
        return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("productos")));
    }

    public ReplyBean reduce() {
        Gson oGson = new Gson();
        Integer id = Integer.parseInt(oRequest.getParameter("id"));
        ArrayList<CarritoBean> alCarritoBeans = new ArrayList<CarritoBean>();
        ArrayList<CarritoBean> productosGuardados = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("productos");

        //No puedo hacer una iteracion de un objeto en null, porque salta excepcion
        if (productosGuardados != null) {
            for (CarritoBean o : productosGuardados) {
                if(o.getObj_producto().getId() != id){
                 alCarritoBeans.add(o);   
                }else{
                    int cantidad = o.getCantidad();
                    cantidad--;
                    o.setCantidad(cantidad);
                    alCarritoBeans.add(o);
                }
            }
        }
        oRequest.getSession().setAttribute("productos", alCarritoBeans);
        return new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("productos")));
    }

    public ReplyBean buy() {
        return null;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.factory;

import com.google.gson.Gson;
import net.daw.bean.FacturaBean;
import net.daw.bean.LineaBean;
import net.daw.bean.ProductoBean;
import net.daw.bean.TipoproductoBean;
import net.daw.bean.TipousuarioBean;
import net.daw.bean.UsuarioBean;
import net.daw.bean.publicBeanInterface.BeanInterface;

/**
 *
 * @author Ramón
 */
public class BeanFactory {

    public static BeanInterface getBean(String ob) {
        BeanInterface oBean = null;
        switch (ob) {
            case "usuario":
                oBean = new UsuarioBean();
                break;
            case "tipousuario":
                oBean = new TipousuarioBean();
                break;
            case "tipoproducto":
                oBean = new TipoproductoBean();
                break;
            case "producto":
                oBean = new ProductoBean();
                break;
            case "factura":
                oBean = new FacturaBean();
                break;
            case "linea":
                oBean = new LineaBean();
                break;
        }
        return oBean;
    }
    
    public static BeanInterface getBeanFromJson(String ob, Gson oGson, String strJsonFromClient) {
        BeanInterface oBean = null;
        switch (ob) {
            case "usuario":
                oBean = oGson.fromJson(strJsonFromClient, UsuarioBean.class);
                break;
            case "tipousuario":
                oBean = oGson.fromJson(strJsonFromClient, TipousuarioBean.class);
                break;
            case "tipoproducto":
                oBean = oGson.fromJson(strJsonFromClient, TipoproductoBean.class);
                break;
            case "producto":
                oBean = oGson.fromJson(strJsonFromClient, ProductoBean.class);
                break;
            case "factura":
                oBean = oGson.fromJson(strJsonFromClient, FacturaBean.class);
                break;
            case "linea":
                oBean = oGson.fromJson(strJsonFromClient, LineaBean.class);
                break;
        }
        return oBean;
    }
}
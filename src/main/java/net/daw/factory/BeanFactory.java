/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.factory;

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
}

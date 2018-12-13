/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.factory;

import java.sql.Connection;
import net.daw.dao.FacturaDao;
import net.daw.dao.LineaDao;
import net.daw.dao.ProductoDao;
import net.daw.dao.TipoproductoDao;
import net.daw.dao.TipousuarioDao;
import net.daw.dao.UsuarioDao;
import net.daw.dao.publicDaoInterface.DaoInterface;

/**
 *
 * @author Ramón
 */
public class DaoFactory {

    public static DaoInterface getDao(Connection oConnection, String ob) {
        DaoInterface oDao = null;
        switch (ob) {
            case "usuario":
                oDao = new UsuarioDao(oConnection, ob);
                break;
            case "tipousuario":
                oDao = new TipousuarioDao(oConnection, ob);
                break;
            case "tipoproducto":
                oDao = new TipoproductoDao(oConnection, ob);
                break;
            case "producto":
                oDao = new ProductoDao(oConnection, ob);
                break;
            case "factura":
                oDao = new FacturaDao(oConnection, ob);
                break;
            case "linea":
                oDao = new LineaDao(oConnection, ob);
                break;
        }
        return oDao;
    }
}

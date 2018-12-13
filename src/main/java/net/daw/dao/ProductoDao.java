/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.dao;

import java.sql.Connection;
import net.daw.dao.genericDaoImplementation.GenericDaoImplementation;
import net.daw.dao.publicDaoInterface.DaoInterface;

/**
 *
 * @author Ramón
 */
public class ProductoDao extends GenericDaoImplementation implements DaoInterface {

    public ProductoDao(Connection oConnection, String ob) {
        super(oConnection, ob);
    }

}
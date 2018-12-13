package net.daw.dao;

import java.sql.Connection;
import net.daw.dao.genericDaoImplementation.GenericDaoImplementation;
import net.daw.dao.publicDaoInterface.DaoInterface;

/**
 *
 * @author Ramón
 */

public class TipoproductoDao extends GenericDaoImplementation implements DaoInterface {

	public TipoproductoDao(Connection oConnection, String ob) {
		super(oConnection, ob);
	}
}

package net.daw.dao;

import java.sql.Connection;
import net.daw.dao.genericDaoImplementation.GenericDaoImplementation;
import net.daw.dao.publicDaoInterface.DaoInterface;

/**
 *
 * @author Ramón
 */

public class TipousuarioDao extends GenericDaoImplementation implements DaoInterface {

	public TipousuarioDao(Connection oConnection, String ob) {
		super(oConnection, ob);
	}
}

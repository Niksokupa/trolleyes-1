package net.daw.service;

import javax.servlet.http.HttpServletRequest;
import net.daw.service.genericServiceImplementation.GenericServiceImplementation;
import net.daw.service.publicServiceInterface.ServiceInterface;

public class TipousuarioService extends GenericServiceImplementation implements ServiceInterface {

    public TipousuarioService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }
}

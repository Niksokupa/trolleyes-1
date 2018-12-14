package net.daw.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.ReplyBean;
import net.daw.bean.ProductoBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.ProductoDao;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.EncodingHelper;
import net.daw.service.genericServiceImplementation.GenericServiceImplementation;
import net.daw.service.publicServiceInterface.ServiceInterface;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ProductoService extends GenericServiceImplementation implements ServiceInterface {

    public ProductoService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }

    public ReplyBean fill() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        ArrayList<ProductoBean> productos = new ArrayList<>();
        RellenarService oRellenarService = new RellenarService();
        try {
            Integer number = Integer.parseInt(oRequest.getParameter("number"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            ProductoDao oProductoDao = new ProductoDao(oConnection, ob);
            productos = oRellenarService.RellenarProducto(number);
            for (ProductoBean producto : productos) {
                oProductoDao.create(producto);
            }
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson("Productos creados: " + number));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        }
        return oReplyBean;
    }

    public ReplyBean addimage() throws Exception {

        String name = "";
        ReplyBean oReplyBean;
        Gson oGson = new Gson();

        HashMap<String, String> hash = new HashMap<>();

        if (ServletFileUpload.isMultipartContent(oRequest)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(oRequest);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        name = new File(item.getName()).getName();
                        item.write(new File(".//..//webapps//ROOT//imagenes//" + name));
                    } else {
                        hash.put(item.getFieldName(), item.getString());
                    }
                }
                oReplyBean = new ReplyBean(200, oGson.toJson("File upload: " + name));
            } catch (Exception ex) {
                oReplyBean = new ReplyBean(500, oGson.toJson("Error while uploading file: " + name));
            }
        } else {
            oReplyBean = new ReplyBean(500, oGson.toJson("Can't read image"));
        }

        return oReplyBean;
    }
}

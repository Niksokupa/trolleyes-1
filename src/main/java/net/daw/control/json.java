package net.daw.control;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.daw.bean.ReplyBean;
import net.daw.constant.ConfigurationConstants;
import net.daw.constant.ConfigurationConstants.EnvironmentConstans;
import net.daw.factory.ServiceFactory;
import net.daw.helper.JsonHelper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class json
 */
public class json extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public json() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        response.setContentType("application/json;charset=UTF-8");

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, x-requested-with, Content-Type");

        String strJson = "";
        JsonHelper json = new JsonHelper();

//        Prueba subida imágenes             //
        String name = "";
        HashMap<String, String> hash = new HashMap<>();

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        name = new File(item.getName()).getName();
                        item.write(new File(".//..//webapps//images//" + name));

                    } else {
                        hash.put(item.getFieldName(), item.getString());
                    }
                }
            } catch (Exception ex) {
                PrintWriter out = response.getWriter();
                out.println(ex.getMessage());
                ex.printStackTrace(out);
            }
        }

        //////////////////////////////////////////////////////////////////////
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            strJson = "{\"status\":500,\"msg\":\"jdbc driver not found\"}";
        }
        try {
            ReplyBean oReplyBean = ServiceFactory.executeService(request);
            strJson = json.strJson(oReplyBean.getStatus(), oReplyBean.getJson());
            Gson oGson = new Gson();
            oGson.toJson(strJson);
        } catch (Exception e) {
            response.setStatus(500);
            strJson = json.strJson(500, "Server Error");
            if (ConfigurationConstants.environment == EnvironmentConstans.Debug) {
                PrintWriter out = response.getWriter();
                out.println(e.getMessage());
                e.printStackTrace(out);
            }
        }
        response.getWriter().append(strJson).close();
    }
}

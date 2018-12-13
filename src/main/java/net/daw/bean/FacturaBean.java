/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import net.daw.bean.genericBeanInterface.GenericBeanImplementation;
import net.daw.bean.publicBeanInterface.BeanInterface;
import net.daw.dao.LineaDao;
import net.daw.dao.UsuarioDao;
import net.daw.dao.publicDaoInterface.DaoInterface;
import net.daw.factory.DaoFactory;
import net.daw.helper.EncodingHelper;

/**
 *
 * @author Ramón
 */
public class FacturaBean extends GenericBeanImplementation implements BeanInterface{


    @Expose
    private Date fecha;
    @Expose
    private double iva;
    @Expose
    private UsuarioBean obj_usuario;
    @Expose
    private int id_usuario;
    @Expose
    private int numLineas;

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    public int getNumLineas() {
        return numLineas;
    }

    public void setNumLineas(int numLineas) {
        this.numLineas = numLineas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public UsuarioBean getObj_usuario() {
        return obj_usuario;
    }

    public void setObj_usuario(UsuarioBean obj_usuario) {
        this.obj_usuario = obj_usuario;
    }

    @Override
    public FacturaBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws SQLException, Exception{
        this.setId(oResultSet.getInt("id"));
        Timestamp timestamp = oResultSet.getTimestamp("fecha");
        Date date = new java.util.Date(timestamp.getTime());
        this.setFecha(date);
        this.setIva(oResultSet.getDouble("iva"));
        LineaDao oLineaDao = new LineaDao(oConnection, "linea");
        this.setNumLineas(oLineaDao.getcountspecific(this.getId()));
        if(expand > 0){
            DaoInterface oUsuarioDao = DaoFactory.getDao(oConnection, "usuario");
            this.setObj_usuario((UsuarioBean) oUsuarioDao.get(oResultSet.getInt("id_usuario"), expand));
        }
        return this;
    }
    
    @Override
    public String getColumns(){
        String strColumns = "";
        strColumns += "id,";
        strColumns += "fecha,";
        strColumns += "iva,";
        strColumns += "id_usuario";
        return strColumns;
    }
    
    @Override
    public String getValues(){
        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant instant = fecha.toInstant();

        //Converting the Date to LocalDate
        LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();

        
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(localDateTime.toString()) + ",";
        strColumns += iva + ",";
        strColumns += id_usuario;
        return strColumns;
    }
    
    @Override
    public String getPairs() {

        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant instant = fecha.toInstant();

        //Converting the Date to LocalDate
        LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();


        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "fecha=" + EncodingHelper.quotate(localDateTime.toString()) + ",";
        strPairs += "iva=" + iva + ",";
        strPairs += "id_usuario=" + id_usuario;
        strPairs += " WHERE id=" + id;
        return strPairs;
    }
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import net.daw.dao.LineaDao;
import net.daw.dao.UsuarioDao;
import net.daw.helper.EncodingHelper;

/**
 *
 * @author Jesus
 */
public class FacturaBean {

    private int id;
    private Date fecha;
    private double iva;
    private UsuarioBean obj_usuario;
    private int id_usuario;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public FacturaBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws SQLException, Exception{
        this.setId(oResultSet.getInt("id"));
        this.setFecha(oResultSet.getDate("fecha"));
        this.setIva(oResultSet.getDouble("iva"));
        LineaDao oLineaDao = new LineaDao(oConnection, "linea");
        this.setNumLineas(oLineaDao.getcountspecific(this.getId()));
        if(expand > 0){
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, "usuario");
            this.setObj_usuario(oUsuarioDao.get(oResultSet.getInt("id_usuario"), expand));
        }
        return this;
    }
    
    public String getColumns(){
        String strColumns = "";
        strColumns += "id,";
        strColumns += "fecha,";
        strColumns += "iva,";
        strColumns += "id_usuario";
        return strColumns;
    }
    
    public String getValues(){
        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant instant = fecha.toInstant();

        //Converting the Date to LocalDate
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();

        
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(localDate.toString()) + ",";
        strColumns += iva + ",";
        strColumns += id_usuario;
        return strColumns;
    }
    
    public String getPairs() {

        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant instant = fecha.toInstant();

        //Converting the Date to LocalDate
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();


        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "fecha=" + EncodingHelper.quotate(localDate.toString()) + ",";
        strPairs += "iva=" + iva + ",";
        strPairs += "id_usuario=" + id_usuario;
        strPairs += " WHERE id=" + id;
        return strPairs;
    }
    

}

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
import net.daw.bean.genericBeanInterface.GenericBeanImplementation;
import net.daw.bean.publicBeanInterface.BeanInterface;
import net.daw.helper.EncodingHelper;

/**
 *
 * @author Ramón
 */
public class TipoproductoBean extends GenericBeanImplementation implements BeanInterface{
    
    @Expose
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public TipoproductoBean fill(ResultSet oResultSet, Connection connection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDesc(oResultSet.getString("desc"));
        return this;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "tipoproducto.desc";
        return strColumns;
    }
    
    @Override
    public String getValues(){
        String strColumns="";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(desc);
        return strColumns;
    }
    
    @Override
    public String getPairs(){
        String strPairs = "";
        strPairs += "id=" + id +",";
        strPairs += "tipoproducto.desc='" + desc + "'";
        strPairs += " WHERE id=" + id;
        return strPairs;
    }

}

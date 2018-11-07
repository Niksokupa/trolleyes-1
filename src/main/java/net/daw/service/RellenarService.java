/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.service;

import java.util.ArrayList;
import net.daw.bean.ProductoBean;
import net.daw.bean.TipoproductoBean;
import net.daw.bean.TipousuarioBean;
import net.daw.bean.UsuarioBean;

/**
 *
 * @author jesus
 */
public class RellenarService {

    public ArrayList<ProductoBean> RellenarProducto(int numero) {
        String[] codigo = {"56TT", "GTE4", "K8J6", "JKK1", "TTP9"};
        String[] desc = {"Piano", "Flauta", "Guitarra", "Tuba", "Gaita"};
        String[] desc2 = {"Viento", "Mecanico", "Cuerda", "Metal", "Madera"};
        String foto = "Foto";
        int[] id_tipoProducto = {1, 2, 3, 4, 5};
        int[] existencias = {1, 2, 3, 4, 5};

        //Para tener mayor control de el maximo de objetos que tengo en los arrays
        int maxDatos = 5;

        ArrayList<ProductoBean> resultado = new ArrayList<>();
        ProductoBean resultadoProducto;
        TipoproductoBean oTipoproductoBean;
        for (int i = 0; i < numero; i++) {
            resultadoProducto = new ProductoBean();
            oTipoproductoBean = new TipoproductoBean();

            resultadoProducto.setCodigo(codigo[randomMath(maxDatos)]);
            resultadoProducto.setDesc(desc[randomMath(maxDatos)] + " " + desc2[randomMath(maxDatos)]);
            resultadoProducto.setPrecio((float) (((int) (Math.random() * 10000)) * 0.01));
            resultadoProducto.setFoto(foto);
            oTipoproductoBean.setId(id_tipoProducto[randomMath(maxDatos)]);
            resultadoProducto.setObj_TipoproductoBean(oTipoproductoBean);
            resultadoProducto.setExistencias(existencias[randomMath(maxDatos)]);
            resultado.add(resultadoProducto);
        }
        return resultado;
    }

    public ArrayList<UsuarioBean> RellenarUsuario(int numero) {
        String[] dni = {"24345181A", "94192431W", "66477883V", "16812040Y", "07251643L", "13297629H", "11944747L", "19786169M", "80319858V", "85994825X"};
        String[] nombre = {"Lucas", "Oriol", "Marta", "Teresa", "Rafael"};
        String[] ape1 = {"Reyes", "Ruiz", "Navarro", "Segura", "Sola"};
        String[] ape2 = {"Velasco", "Guerrero", "Vega", "Lozano", "Carrasco"};
        String[] login = {"Phobicroma", "Sandenbeck", "Sarcompum", "SnoXcaptain", "Spanexpr", "Sulterea", "TipsDoggChrome", "TrumpBrilliant", "Wateronsund"};
        String[] pass = {"1111", "2222", "3333", "4444", "5555"};
        int[] id_tipoUsuario = {1, 2, 3, 4, 5};

        ArrayList<UsuarioBean> resultado = new ArrayList<>();
        UsuarioBean resultadoUsuarioBean;
        TipousuarioBean oTipousuarioBean;
        for (int i = 1; i < numero; i++) {
            resultadoUsuarioBean = new UsuarioBean();
            oTipousuarioBean = new TipousuarioBean();

            resultadoUsuarioBean.setDni(dni[randomMath(dni.length)]);
            resultadoUsuarioBean.setNombre(nombre[randomMath(nombre.length)]);
            resultadoUsuarioBean.setApe1(ape1[randomMath(ape1.length)]);
            resultadoUsuarioBean.setApe2(ape2[randomMath(ape2.length)]);
            resultadoUsuarioBean.setLogin(login[randomMath(login.length)]);
            resultadoUsuarioBean.setPass(pass[randomMath(pass.length)]);
            oTipousuarioBean.setId(id_tipoUsuario[randomMath(id_tipoUsuario.length)]);
            resultadoUsuarioBean.setObj_tipoUsuario(oTipousuarioBean);
            resultado.add(resultadoUsuarioBean);
        }
        return resultado;
    }

    private int randomMath(int number) {
        return (int) (Math.random() * number);
    }

}

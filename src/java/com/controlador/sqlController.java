/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controlador;

import com.conexion.sqlConnection;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

/**
 *
 * @author Arturo González
 */
public class sqlController {

    private sqlConnection linkDB = null;
    private Connection con = null;

    //////////////////////////////////////////////////////////////////////
    public void setConexion(sqlConnection conex) {
        linkDB = conex;
        con = linkDB.getConnection();
        System.out.println("[CONEXION RECIBIDA]");
    }

    ///////////////////////////////////////////////////////////////////////
    public boolean isConected() {
        boolean result = false;
        if ((linkDB != null) && (linkDB.isConnect())) {
            result = true;
        }
        return result;
    }

    public void cerrarConexion() {

        if (this.isConected()) {
            linkDB.cerrarConexion();
        }
    }

    public ResultSet CargarSql(String sql, String bd, String host, String user, String pass) {

        ResultSet rs = null;
        Connection con = null;
        sqlConnection sqlCon = new sqlConnection();

        try {

            sqlCon.conectarMySQL(bd, host, user, pass);
            con = (Connection) sqlCon.getConnection();

            Statement st = (Statement) con.createStatement();
            // System.out.println("Se ha realizado con exito la conexion a MySQL");
            //el resultSet es el encargado de traer los datos de la consulta
            rs = st.executeQuery(sql);

        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Error ex) {
            System.out.println(ex);
        }
        return rs;
    }

    public Result CargarSql2(String sql, String bd, String host, String user, String pass) {

        ResultSet rs = null;
        Result result = null;
        Connection con = null;
        sqlConnection sqlCon = new sqlConnection();

        try {

            sqlCon.conectarMySQL(bd, host, user, pass);
            //   System.out.println("**" + bd);

            con = (Connection) sqlCon.getConnection();

            Statement st = (Statement) con.createStatement();
            //  System.out.println("Se ha realizado con exito la conexion a MySQL");
            //el resultSet es el encargado de traer los datos de la consulta
            //System.out.println(sql);
            rs = st.executeQuery(sql);
            result = ResultSupport.toResult(rs);

        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (Error ex) {
            System.out.println(ex);
        }
        return result;
    }

    public boolean UpdateSql(String sql, String bd, String host, String user, String pass) {

        boolean aux = true;
        String id = "";
        Connection con = null;
        sqlConnection sqlCon = new sqlConnection();

        try {
            sqlCon.conectarMySQL(bd, host, user, pass);
            con = (Connection) sqlCon.getConnection();

            Statement st = (Statement) con.createStatement();
            // System.out.println("Se ha realizado con exito la conexion a MySQL");

            st.executeUpdate(sql);

        } catch (SQLException ex) {
            aux = false;
            System.out.println(ex);
        } catch (Error ex) {
            System.out.println(ex);
        } finally {
            try {
                con.close();
                //System.out.println("sqlConnection Cerrada con Exito...");
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return aux;
    }
}

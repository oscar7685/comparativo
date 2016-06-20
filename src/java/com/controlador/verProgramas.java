/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.sql.Result;

/**
 *
 * @author acreditacion
 */
public class verProgramas extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        try {

            String anio = request.getParameter("anio");
            String periodo = request.getParameter("semestre");

            int anioX = Integer.parseInt(anio);
            int periodoX = Integer.parseInt(periodo);
            int periodoAnteriorX;
            int anioAnteriorX;
            if (periodoX == 2) {
                periodoAnteriorX = 1;
                anioAnteriorX = anioX;
            } else {
                periodoAnteriorX = 2;
                anioAnteriorX = anioX - 1;
            }
            String fechaSup = "";
            String fechaInf = "";
            if (periodoX == 2) {
                fechaSup = anio + "-12-31";
                fechaInf = anio + "-01-01";
            } else {
                fechaSup = anio + "-06-30";
                fechaInf = anioAnteriorX + "-07-01";
            }

            sqlController conSql = new sqlController();
            Result rs1 = conSql.CargarSql2("SELECT trim(jiji), sum(cantidad) FROM (\n"
                    + " select  \n"
                    + " case  \n"
                    + " when smapgm.nropgm= '618' then '53683'  \n"
                    + " when smapgm.nropgm= '687' then '103288'  \n"
                    + " when smapgm.nropgm= '689' then '104821'  \n"
                    + " when smapgm.nropgm= '688' then '104820'  \n"
                    + " when smapgm.nropgm= '196' then '104236'  \n"
                    + " when smapgm.nropgm= '197' then '104196'  \n"
                    + " when smapgm.nropgm= '192' then '103614'  \n"
                    + " when smapgm.nropgm= '194' then '103783'  \n"
                    + " when smapgm.nropgm= '195' then '104967'  \n"
                    + " when smapgm.nropgm= '135' then '52444'  \n"
                    + " when smapgm.nropgm= '733' then '53682'  \n"
                    + " else smapgm.snepgm end as jiji, \n"
                    + " count(smamtr.codbas) as cantidad\n"
                    + " from smamtr  \n"
                    + " join smacia on smacia.codcia = smamtr.codcia \n"
                    + " join smabas on smabas.codcia = smamtr.codcia and smabas.codprs = smamtr.codbas \n"
                    + " join smapgm on smapgm.codcia = smamtr.codcia and smapgm.nropgm = smamtr.nropgm \n"
                    + " where  \n"
                    + "	 (smamtr.stdmtr='Matriculado.' \n"
                    + " and smamtr.agnprs = '" + anio + "' \n"
                    + " and smamtr.prdprs='" + periodo + "' \n"
                    + " and COALESCE(smapgm.snepgm, '909090') <> '0' \n"
                    + " and substring (smamtr.codbas from 7 for 1)<>'5' \n"
                    + "	) or \n"
                    + "	(smamtr.stdmtr='Matriculado.' \n"
                    + " and (smamtr.agnprs = '" + anioAnteriorX + "' and ((smamtr.prdprs='" + periodoAnteriorX + "'))\n"
                    + "	and smapgm.prdpgm = 'AGN'\n"
                    + "	and smapgm.tpopgm ='Postgrado'\n"
                    + "	and smapgm.nropgm <> '192' \n"
                    + "	and smapgm.nropgm <> '684' \n"
                    + "	and smapgm.nropgm <> '686' \n"
                    + " and COALESCE(smapgm.snepgm, '909090') <> '0' \n"
                    + " and substring (smamtr.codbas from 7 for 1)<>'5' \n"
                    + "	and smabas.codprs  not in (select smabas.codprs from smarsg\n"
                    + "	join smacia on smacia.codcia = smarsg.codcia\n"
                    + "	join smarsb on smarsb.codcia = smarsg.codcia and smarsb.nrorsg = smarsg.nrorsg\n"
                    + "	join smabas on smabas.codcia = smarsb.codcia and smabas.codprs = smarsb.codprs and smarsb.stdrsb = 'Generada..'\n"
                    + "	join smapgm on smapgm.codcia = smabas.codcia and smapgm.nropgm = smabas.nropgm\n"
                    + "	where smarsg.codcia = 'UDC' and smapgm.nropgm <> '192' and smapgm.prdpgm = 'AGN' and smapgm.tpopgm ='Postgrado'\n"
                    + "	and CAST(smarsg.fhgrsg AS DATE) < '" + fechaSup + "' \n"
                    + "	and CAST(smarsg.fhgrsg AS DATE) > '" + fechaInf + "')\n"
                    + "	)\n"
                    + "	)\n"
                    + "	GROUP BY smapgm.snepgm, smapgm.nropgm\n"
                    + " ) AS programas \n"
                    + " GROUP BY jiji ORDER BY jiji::integer ASC", "sma_db_ix12_udc", "192.168.8.16", "userextern", "userextern.");
            session.setAttribute("sma", rs1);

            Result rs2 = conSql.CargarSql2("select\n"
                    + "matriculado.pro_consecutivo\n"
                    + ",programa.prog_nombre as prog_nombre\n"
                    + ", \"count\"(*)\n"
                    + "from matriculado\n"
                    + "INNER join participante on participante.codigo_unico = matriculado.codigo_unico and participante.tipo_doc_unico = matriculado.tipo_doc_unico\n"
                    + "inner join programa on programa.pro_consecutivo = matriculado.pro_consecutivo\n"
                    + "where matriculado.est_annio = '" + anio + "' and matriculado.est_semestre='0" + periodo + "' \n"
                    + "GROUP BY matriculado.pro_consecutivo, programa.prog_nombre\n"
                    + "ORDER BY matriculado.pro_consecutivo ASC", "ODS", "201.245.192.2", "postgres", "");
            session.setAttribute("snies", rs2);
            response.sendRedirect("html/tables/tablaComparativa.jsp");

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

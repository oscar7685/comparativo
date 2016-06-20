/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controlador;

import java.io.IOException;
import java.io.PrintWriter;
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
public class verDiferencias extends HttpServlet {

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
        String anio = request.getParameter("anio");
        String periodo = request.getParameter("semestre");
        String codigoSNIES = request.getParameter("snies");
        String codigoSNIES_SMA = codigoSNIES;

        if ("52444".equals(codigoSNIES_SMA)) {
            codigoSNIES_SMA = "54443";
        }

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

        try {
            sqlController conSql = new sqlController();
            Result rs1 = null;

            if ("103783".equals(codigoSNIES_SMA) || "104236".equals(codigoSNIES_SMA) || "104967".equals(codigoSNIES_SMA) 
                    || "103614".equals(codigoSNIES_SMA)) {
                String nroPgm = "";
                if ("103783".equals(codigoSNIES_SMA)) {
                    nroPgm = "194";
                } else if ("104236".equals(codigoSNIES_SMA)) {
                    nroPgm = "196";
                } else if ("104967".equals(codigoSNIES_SMA)) {
                    nroPgm = "195";
                } else if ("103614".equals(codigoSNIES_SMA)) {
                    nroPgm = "192";
                }

                rs1 = conSql.CargarSql2("select\n"
                        + " (SELECT DISTINCT(smamtr.nribas) FROM smamtr where smamtr.codbas = smabas.codprs ORDER BY smamtr.nribas LIMIT 1) as id1,  \n"
                        + " (SELECT DISTINCT(smamtr.nribas) FROM smamtr where smamtr.codbas = smabas.codprs ORDER BY smamtr.nribas LIMIT 1 OFFSET 1) as id2, \n"
                        + " (SELECT DISTINCT(smamtr.nribas) FROM smamtr where smamtr.codbas = smabas.codprs ORDER BY smamtr.nribas LIMIT 1 OFFSET 2) as id3, \n"
                        + " split_part(smabas.nomprs,' ',1) as primer_nombre_estudiante, \n"
                        + " split_part(smabas.apeprs,' ',1) as primer_apellido_estudiante, \n"
                        + " split_part(smabas.apeprs,' ',2) as segundo_apellido_estudiante, \n"
                        + " smabas.codprs as codigo_estudiantil \n"
                        + " from smabas \n"
                        + " inner join smapgm on smapgm.nropgm = smabas.nropgm \n"
                        + " inner join smamtr on smamtr.codbas = smabas.codprs \n"
                        + " where (smamtr.stdmtr='Matriculado.'  \n"
                        + " and smamtr.agnprs = '" + anio + "'  \n"
                        + " and smamtr.prdprs='" + periodo + "' \n"
                        + " and smapgm.nropgm = '"+nroPgm+"' \n"
                        + " and substring (smamtr.codbas from 7 for 1)<>'5'\n"
                        + ")\n"
                        + "UNION\n"
                        + "select  \n"
                        + " (SELECT DISTINCT(smamtr.nribas) FROM smamtr where smamtr.codbas = smabas.codprs ORDER BY smamtr.nribas LIMIT 1) as id1,  \n"
                        + " (SELECT DISTINCT(smamtr.nribas) FROM smamtr where smamtr.codbas = smabas.codprs ORDER BY smamtr.nribas LIMIT 1 OFFSET 1) as id2, \n"
                        + " (SELECT DISTINCT(smamtr.nribas) FROM smamtr where smamtr.codbas = smabas.codprs ORDER BY smamtr.nribas LIMIT 1 OFFSET 2) as id3, \n"
                        + " split_part(smabas.nomprs,' ',1) as primer_nombre_estudiante, \n"
                        + " split_part(smabas.apeprs,' ',1) as primer_apellido_estudiante, \n"
                        + " split_part(smabas.apeprs,' ',2) as segundo_apellido_estudiante, \n"
                        + " smabas.codprs as codigo_estudiantil \n"
                        + " from smabas \n"
                        + " inner join smapgm on smapgm.nropgm = smabas.nropgm \n"
                        + " inner join smamtr on smamtr.codbas = smabas.codprs \n"
                        + " where\n"
                        + "	(smamtr.stdmtr='Matriculado.'  \n"
                        + "  and smamtr.agnprs = '" + anioAnteriorX + "' \n"
                        + "  and smamtr.prdprs='" + periodoAnteriorX + "'\n"
                        + " 	and smapgm.prdpgm = 'AGN' \n"
                        + " 	and smapgm.tpopgm ='Postgrado' \n"
                        + " 	and smapgm.nropgm = '"+nroPgm+"' \n"
                        + "  and substring (smamtr.codbas from 7 for 1)<>'5'  \n"
                        + " 	and smabas.codprs  not in (select smabas.codprs from smarsg \n"
                        + " 	join smacia on smacia.codcia = smarsg.codcia \n"
                        + " 	join smarsb on smarsb.codcia = smarsg.codcia and smarsb.nrorsg = smarsg.nrorsg \n"
                        + " 	join smabas on smabas.codcia = smarsb.codcia and smabas.codprs = smarsb.codprs and smarsb.stdrsb = 'Generada..' \n"
                        + " 	join smapgm on smapgm.codcia = smabas.codcia and smapgm.nropgm = smabas.nropgm \n"
                        + " 	where smarsg.codcia = 'UDC' and smapgm.nropgm = '"+nroPgm+"' and smapgm.prdpgm = 'AGN' and smapgm.tpopgm ='Postgrado' \n"
                        + " 	and CAST(smarsg.fhgrsg AS DATE) < '" + fechaSup + "'  \n"
                        + " 	and CAST(smarsg.fhgrsg AS DATE) > '" + fechaInf + "') \n"
                        + " 	)", "sma_db_ix12_udc", "192.168.8.16", "userextern", "userextern.");

            } else {
                rs1 = conSql.CargarSql2("select\n"
                        + " (SELECT DISTINCT(smamtr.nribas) FROM smamtr where smamtr.codbas = smabas.codprs ORDER BY smamtr.nribas LIMIT 1) as id1,  \n"
                        + " (SELECT DISTINCT(smamtr.nribas) FROM smamtr where smamtr.codbas = smabas.codprs ORDER BY smamtr.nribas LIMIT 1 OFFSET 1) as id2, \n"
                        + " (SELECT DISTINCT(smamtr.nribas) FROM smamtr where smamtr.codbas = smabas.codprs ORDER BY smamtr.nribas LIMIT 1 OFFSET 2) as id3, \n"
                        + " split_part(smabas.nomprs,' ',1) as primer_nombre_estudiante, \n"
                        + " split_part(smabas.apeprs,' ',1) as primer_apellido_estudiante, \n"
                        + " split_part(smabas.apeprs,' ',2) as segundo_apellido_estudiante, \n"
                        + " smabas.codprs as codigo_estudiantil \n"
                        + " from smabas \n"
                        + " inner join smapgm on smapgm.nropgm = smabas.nropgm \n"
                        + " inner join smamtr on smamtr.codbas = smabas.codprs \n"
                        + " where (smamtr.stdmtr='Matriculado.'  \n"
                        + " and smamtr.agnprs = '" + anio + "'  \n"
                        + " and smamtr.prdprs='" + periodo + "' \n"
                        + " and smapgm.snepgm = '" + codigoSNIES_SMA + "'\n"
                        + " and substring (smamtr.codbas from 7 for 1)<>'5'\n"
                        + ")\n"
                        + "UNION\n"
                        + "select  \n"
                        + " (SELECT DISTINCT(smamtr.nribas) FROM smamtr where smamtr.codbas = smabas.codprs ORDER BY smamtr.nribas LIMIT 1) as id1,  \n"
                        + " (SELECT DISTINCT(smamtr.nribas) FROM smamtr where smamtr.codbas = smabas.codprs ORDER BY smamtr.nribas LIMIT 1 OFFSET 1) as id2, \n"
                        + " (SELECT DISTINCT(smamtr.nribas) FROM smamtr where smamtr.codbas = smabas.codprs ORDER BY smamtr.nribas LIMIT 1 OFFSET 2) as id3, \n"
                        + " split_part(smabas.nomprs,' ',1) as primer_nombre_estudiante, \n"
                        + " split_part(smabas.apeprs,' ',1) as primer_apellido_estudiante, \n"
                        + " split_part(smabas.apeprs,' ',2) as segundo_apellido_estudiante, \n"
                        + " smabas.codprs as codigo_estudiantil \n"
                        + " from smabas \n"
                        + " inner join smapgm on smapgm.nropgm = smabas.nropgm \n"
                        + " inner join smamtr on smamtr.codbas = smabas.codprs \n"
                        + " where\n"
                        + "  (smamtr.stdmtr='Matriculado.'  \n"
                        + "  and smamtr.agnprs = '" + anioAnteriorX + "' \n"
                        + "  and smamtr.prdprs='" + periodoAnteriorX + "'\n"
                        + "  and smapgm.prdpgm = 'AGN' \n"
                        + "  and smapgm.tpopgm ='Postgrado' \n"
                        + "  and smapgm.nropgm <> '684'\n"
                        + "  and smapgm.nropgm <> '686'\n"
                        + "  and smapgm.snepgm = '" + codigoSNIES_SMA + "'  \n"
                        + "  and substring (smamtr.codbas from 7 for 1)<>'5'  \n"
                        + "  and smabas.codprs  not in (select smabas.codprs from smarsg \n"
                        + "  join smacia on smacia.codcia = smarsg.codcia \n"
                        + "  join smarsb on smarsb.codcia = smarsg.codcia and smarsb.nrorsg = smarsg.nrorsg \n"
                        + "  join smabas on smabas.codcia = smarsb.codcia and smabas.codprs = smarsb.codprs and smarsb.stdrsb = 'Generada..' \n"
                        + "  join smapgm on smapgm.codcia = smabas.codcia and smapgm.nropgm = smabas.nropgm \n"
                        + "  where smarsg.codcia = 'UDC' and smapgm.snepgm = '" + codigoSNIES_SMA + "' and smapgm.prdpgm = 'AGN' and smapgm.tpopgm ='Postgrado' \n"
                        + "  and CAST(smarsg.fhgrsg AS DATE) < '" + fechaSup + "'  \n"
                        + "  and CAST(smarsg.fhgrsg AS DATE) > '" + fechaInf + "') \n"
                        + "  )", "sma_db_ix12_udc", "192.168.8.16", "userextern", "userextern.");
            }

            Result rs2 = conSql.CargarSql2("select \n"
                    + " matriculado.codigo_unico,\n"
                    + " participante.primer_nombre,\n"
                    + " participante.primer_apellido,\n"
                    + " participante.segundo_apellido,\n"
                    + " programa.prog_nombre as prog_nombre\n"
                    + " from matriculado \n"
                    + " INNER join participante on participante.codigo_unico = matriculado.codigo_unico and participante.tipo_doc_unico = matriculado.tipo_doc_unico \n"
                    + " inner join programa on programa.pro_consecutivo = matriculado.pro_consecutivo \n"
                    + " where matriculado.est_annio = '" + anio + "' and matriculado.est_semestre='0" + periodo + "' and matriculado.pro_consecutivo = '" + codigoSNIES + "'", "ODS", "201.245.192.2", "postgres", "");
            session.setAttribute("matriculadosSNIES", rs2);
            session.setAttribute("matriculadosSMA", rs1);
            response.sendRedirect("html/tables/cuadroComparativo.jsp");
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

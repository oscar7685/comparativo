<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Plugin -->
<link rel="stylesheet" href="../../assets/vendor/magnific-popup/magnific-popup.css">
<script src="../../assets/vendor/magnific-popup/jquery.magnific-popup.js"></script>
<style type="text/css">
    .table a {
        text-decoration: none;
    }
</style>
<table class="table table-hover">
    <thead>
        <tr>
            <th>Código SNIES</th>
            <th>Programa</th>
            <th># SMA</th>
            <th># SNIES</th>
            <th>Diferencia</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <c:set var="indiceSNIES" value='0'></c:set>       
            <c:forEach items="${sma.rowsByIndex}" var="item" varStatus="iter">
                <c:choose>
                    <c:when test="${item[0] > snies.rowsByIndex[indiceSNIES][0]}">
                    <tr>
                        <td>${snies.rowsByIndex[indiceSNIES][0]}</td>
                        <td>${snies.rowsByIndex[indiceSNIES][1]}</td>
                        <td>0</td>
                        <td>${snies.rowsByIndex[indiceSNIES][2]}</td>
                        <td><a class="popup-with-css-anim" href="/comparativo/verDiferencias"
                               data-effect="mfp-zoom-in">${snies.rowsByIndex[indiceSNIES][2]}</a></td>
                    </tr>
                    <c:set var="indiceSNIES" value='${indiceSNIES+1}'></c:set>           

                    <c:if test="${item[0] > snies.rowsByIndex[indiceSNIES][0]}">
                        <c:forEach begin="1" end="${item[0] - snies.rowsByIndex[indiceSNIES][0]}">
                            <c:if test="${item[0] > snies.rowsByIndex[indiceSNIES][0]}">
                                <tr>
                                    <td>${snies.rowsByIndex[indiceSNIES][0]}</td>
                                    <td>${snies.rowsByIndex[indiceSNIES][1]}</td>
                                    <td>0</td>
                                    <td>${snies.rowsByIndex[indiceSNIES][2]}</td>
                                    <td><a class="popup-with-css-anim" href="/comparativo/verDiferencias"
                                           data-effect="mfp-zoom-in">${snies.rowsByIndex[indiceSNIES][2]}</a></td>
                                </tr>
                                <c:set var="indiceSNIES" value='${indiceSNIES+1}'></c:set> 
                            </c:if>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${item[0].toString() == snies.rowsByIndex[indiceSNIES][0].toString()}">
                                <tr>
                                    <td>${item[0]}</td>
                                    <td>${snies.rowsByIndex[indiceSNIES][1]}</td>
                                    <td>${item[1]}</td>
                                    <td><a class="popup-with-css-anim" href="/comparativo/verDiferencias"
                                           data-effect="mfp-zoom-in">${snies.rowsByIndex[indiceSNIES][2]}</a></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${item[1] - snies.rowsByIndex[indiceSNIES][2] > 0 }">
                                                <span class="text-danger text-semibold"><i class="icon wb-chevron-up-mini" aria-hidden="true"></i>${item[1] - snies.rowsByIndex[indiceSNIES][2]}</span>
                                                </c:when>
                                                <c:when test="${item[1] - snies.rowsByIndex[indiceSNIES][2] < 0 }">
                                                <span class="text-danger text-semibold"><i class="icon wb-chevron-down-mini" aria-hidden="true"></i>${item[1] - snies.rowsByIndex[indiceSNIES][2]}</span>
                                                </c:when>
                                                <c:otherwise>
                                                <a class="btn btn-pure btn-primary icon wb-thumb-up popup-with-css-anim" 
                                                   href="/comparativo/verDiferencias"
                                                   data-effect="mfp-zoom-in"></a>
                                            </c:otherwise>    

                                        </c:choose>

                                    </td>
                                </tr>
                                <c:set var="indiceSNIES" value='${indiceSNIES+1}'></c:set>           
                            </c:when>
                            <c:when test="${item[0] < snies.rowsByIndex[indiceSNIES][0]}">
                                <tr>
                                    <td>${item[0]}</td>
                                    <td>
                                        <c:if test="${item[0].toString() == '775' }">
                                            MAESTRIA EN MICROBIOLOGIA
                                        </c:if>
                                        <c:if test="${item[0].toString() == '816' }">
                                            ESPECIALIZACION EN INGENIERIA SANITARIA Y AMBIENTAL  
                                        </c:if>
                                        <c:if test="${item[0].toString() == '52453' }">
                                            MAESTRIA EN EDUCACION
                                        </c:if>
                                        <c:if test="${item[0].toString() == '53682' }">
                                            TECNICA PROFESIONAL EN PRODUCCION ACUICOLA
                                        </c:if>
                                        <c:if test="${item[0].toString() == '54682' }">
                                            DOCTORADO EN MEDICINA TROPICAL
                                        </c:if>
                                        <c:if test="${item[0].toString() == '7188' }">
                                            DOCTORADO EN CIENCIAS DE LA EDUCACION
                                        </c:if>
                                        <c:if test="${item[0].toString() == '91050' }">
                                            DOCTORADO EN CIENCIAS FISICAS
                                        </c:if>
                                        <c:if test="${item[0].toString() == '90905' }">
                                            MAESTRIA EN BIOQUIMICA
                                        </c:if>
                                        <c:if test="${item[0].toString() == '103614' }">
                                            ESPECIALIZACION EN INGENIERIA DE AGUA POTABLE Y SANEAMIENTO BÁSICO
                                        </c:if>
                                        <c:if test="${item[0].toString() == '104967' }">
                                            MAESTRIA EN EDUCACION CON ENFASIS EN CIENCIAS EXACTAS, NATURALES Y DEL LENGUAJE
                                        </c:if>
                                    </td>
                                    <td>${item[1]}</td>
                                    <td>0</td>
                                    <td><a class="popup-with-css-anim" href="/comparativo/verDiferencias?anio=' + anio + '&semestre=' + semestre"
                                           data-effect="mfp-zoom-in">${item[1]}</a></td>
                                </tr>
                            </c:when>    
                            <c:otherwise>
                                <tr>
                                    <td>${item[0]}</td>
                                    <td>
                                        <c:if test="${item[0].toString() == '775' }">
                                            MAESTRIA EN MICROBIOLOGIA
                                        </c:if>
                                        <c:if test="${item[0].toString() == '816' }">
                                            ESPECIALIZACION EN INGENIERIA SANITARIA Y AMBIENTAL  
                                        </c:if>
                                        <c:if test="${item[0].toString() == '7188' }">
                                            DOCTORADO EN CIENCIAS DE LA EDUCACION
                                        </c:if>
                                        <c:if test="${item[0].toString() == '52453' }">
                                            MAESTRIA EN EDUCACION
                                        </c:if>
                                        <c:if test="${item[0].toString() == '53682' }">
                                            TECNICA PROFESIONAL EN PRODUCCION ACUICOLA
                                        </c:if>
                                        <c:if test="${item[0].toString() == '54682' }">
                                            DOCTORADO EN MEDICINA TROPICAL
                                        </c:if>

                                        <c:if test="${item[0].toString() == '91050' }">
                                            DOCTORADO EN CIENCIAS FISICAS
                                        </c:if>
                                        <c:if test="${item[0].toString() == '90905' }">
                                            MAESTRIA EN BIOQUIMICA
                                        </c:if>
                                        <c:if test="${item[0].toString() == '103614' }">
                                            ESPECIALIZACION EN INGENIERIA DE AGUA POTABLE Y SANEAMIENTO BÁSICO
                                        </c:if>
                                        <c:if test="${item[0].toString() == '104967' }">
                                            MAESTRIA EN EDUCACION CON ENFASIS EN CIENCIAS EXACTAS, NATURALES Y DEL LENGUAJE
                                        </c:if>

                                    </td>
                                    <td>${item[1]}</td>
                                    <td>0</td>
                                    <td><span class="text-danger text-semibold">
                                            <i class="icon wb-chevron-up-mini" aria-hidden="true"></i>
                                            <a class="popup-with-css-anim" href="/comparativo/verDiferencias"
                                               data-effect="mfp-zoom-in">${item[1]}</a>
                                        </span>
                                    </td>
                                </tr>
                            </c:otherwise>  
                        </c:choose>
                    </c:if>
                </c:when>    
                <c:when test="${item[0].toString() == snies.rowsByIndex[indiceSNIES][0].toString()}">
                    <tr>
                        <td>${item[0]}</td>
                        <td>${snies.rowsByIndex[indiceSNIES][1]}</td>
                        <td>${item[1]}</td>
                        <td>${snies.rowsByIndex[indiceSNIES][2]}</td>
                        <td>
                            <c:choose>
                                <c:when test="${item[1] - snies.rowsByIndex[indiceSNIES][2] > 0 }">
                                    <span class="text-danger text-semibold"><i class="icon wb-chevron-up-mini" aria-hidden="true"></i>
                                        <a class="popup-with-css-anim" href="/comparativo/verDiferencias"
                                           data-effect="mfp-zoom-in">${item[1] - snies.rowsByIndex[indiceSNIES][2]}</a>
                                    </span>
                                </c:when>
                                <c:when test="${item[1] - snies.rowsByIndex[indiceSNIES][2] < 0 }">
                                    <span class="text-danger text-semibold"><i class="icon wb-chevron-down-mini" aria-hidden="true"></i>
                                        <a class="popup-with-css-anim" href="/comparativo/verDiferencias"
                                           data-effect="mfp-zoom-in">${item[1] - snies.rowsByIndex[indiceSNIES][2]}</a>
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-pure btn-primary icon wb-thumb-up popup-with-css-anim" 
                                       href="/comparativo/verDiferencias"
                                       data-effect="mfp-zoom-in"></a>
                                </c:otherwise>    

                            </c:choose>

                        </td>
                    </tr>
                    <c:set var="indiceSNIES" value='${indiceSNIES+1}'></c:set>           
                </c:when>
                <c:when test="${item[0] < snies.rowsByIndex[indiceSNIES][0]}">
                    <tr>
                        <td>${item[0]}</td>
                        <td>
                            <c:if test="${item[0].toString() == '816' }">
                                ESPECIALIZACION EN INGENIERIA SANITARIA Y AMBIENTAL  
                            </c:if>
                            <c:if test="${item[0].toString() == '52453' }">
                                MAESTRIA EN EDUCACION
                            </c:if>
                            <c:if test="${item[0].toString() == '53682' }">
                                TECNICA PROFESIONAL EN PRODUCCION ACUICOLA
                            </c:if>
                            <c:if test="${item[0].toString() == '54682' }">
                                DOCTORADO EN MEDICINA TROPICAL
                            </c:if>
                            <c:if test="${item[0].toString() == '91050' }">
                                DOCTORADO EN CIENCIAS FISICAS
                            </c:if>
                            <c:if test="${item[0].toString() == '103614' }">
                                ESPECIALIZACION EN INGENIERIA DE AGUA POTABLE Y SANEAMIENTO BÁSICO
                            </c:if>
                            <c:if test="${item[0].toString() == '104967' }">
                                MAESTRIA EN EDUCACION CON ENFASIS EN CIENCIAS EXACTAS, NATURALES Y DEL LENGUAJE
                            </c:if>
                        </td>
                        <td>${item[1]}</td>
                        <td>0</td>
                        <td>
                            <span class="text-danger text-semibold"><i class="icon wb-chevron-up-mini" aria-hidden="true"></i>
                                <a class="popup-with-css-anim" href="/comparativo/verDiferencias"
                                   data-effect="mfp-zoom-in">${item[1]}</a>
                            </span>
                        </td>
                    </tr>
                </c:when>    
                <c:otherwise>
                    <tr>
                        <td>${item[0]}</td>
                        <td>
                            <c:if test="${item[0].toString() == '816' }">
                                ESPECIALIZACION EN INGENIERIA SANITARIA Y AMBIENTAL  
                            </c:if>
                            <c:if test="${item[0].toString() == '52453' }">
                                MAESTRIA EN EDUCACION
                            </c:if>
                            <c:if test="${item[0].toString() == '53682' }">
                                TECNICA PROFESIONAL EN PRODUCCION ACUICOLA
                            </c:if>
                            <c:if test="${item[0].toString() == '54682' }">
                                DOCTORADO EN MEDICINA TROPICAL
                            </c:if>
                            <c:if test="${item[0].toString() == '91050' }">
                                DOCTORADO EN CIENCIAS FISICAS
                            </c:if>
                            <c:if test="${item[0].toString() == '103614' }">
                                ESPECIALIZACION EN INGENIERIA DE AGUA POTABLE Y SANEAMIENTO BÁSICO
                            </c:if>
                            <c:if test="${item[0].toString() == '104967' }">
                                MAESTRIA EN EDUCACION CON ENFASIS EN CIENCIAS EXACTAS, NATURALES Y DEL LENGUAJE
                            </c:if>

                        </td>
                        <td>${item[1]}</td>
                        <td>0</td>
                        <td><a class="popup-with-css-anim" href="/comparativo/verDiferencias"
                               data-effect="mfp-zoom-in">${item[1]}</a></td>
                    </tr>
                </c:otherwise>    
            </c:choose>    
            </tr>
        </c:forEach>
        </tr>
    </tbody>
</table>
<script type="text/javascript">
    $(function() {
        var anio = $("#selectAnio").val();
        var semestre = $("#selectPeriodo").val();
        $('.popup-with-css-anim').each(function(index) {
            $(this).attr("href", "/comparativo/verDiferencias?anio=" + anio + "&semestre=" + semestre + "&snies=" + $(this).parents("tr").children("td:first-child").text());
        });

        $('.popup-with-css-anim').magnificPopup({
            type: 'ajax',
            overflowY: 'scroll',
            closeOnContentClick: false,
            closeOnBgClick: false,
            showCloseBtn: true,
            enableEscapeKey: false,
            closeBtnInside: true
        });
    });

</script>

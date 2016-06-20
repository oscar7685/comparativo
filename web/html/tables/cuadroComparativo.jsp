<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Plugin -->
<style>
    .lightbox-block {
        /*max-width: 800px;*/
        padding: 15px 20px;
        margin: 40px auto;
        overflow: auto;
        background: #fff;
        border-radius: 3px;
    }
    .mfp-close {
        color: #fff !important;
    }
</style>
<div class="lightbox-block">
    <button class="btn btn-primary" id="comparador">Comparar</button>
    <div class="row">
        <div class="col-md-7">
            <table class="table table-hover" id="tablaSMA">
                <thead>
                    <tr>
                        <th colspan="5">SMA</th>
                    </tr>
                    <tr>
                        <th>ID</th>
                        <th>Primer Nombre</th>
                        <th>Primer Apellido</th>
                        <th>Segundo Apellido</th>
                        <th>Codigo Est</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${matriculadosSMA.rowsByIndex}" var="item" varStatus="iter">
                        <tr>
                            <td>${item[0]}
                                <c:if test="${item[1] != null}">
                                    ,${item[1]} 
                                </c:if>
                                <c:if test="${item[2] != null}">
                                    ,${item[2]} 
                                </c:if>   
                            </td>
                            <td>${item[3]}</td>
                            <td>${item[4]}</td>
                            <td>${item[5]}</td>
                            <td>${item[6]}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-5">
            <table class="table table-hover" id="tablaSNIES">
                <thead>
                    <tr>
                        <th colspan="4">SNIES</th>
                    </tr>
                    <tr>
                        <th>ID</th>
                        <th>Primer Nombre</th>
                        <th>Primer Apellido</th>
                        <th>Segundo Apellido</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${matriculadosSNIES.rowsByIndex}" var="item2" varStatus="iter2">
                        <tr>
                            <td>${item2[0]}</td>
                            <td>${item2[1]}</td>
                            <td>${item2[2]}</td>
                            <td>${item2[3]}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>


</div>
<script type="text/javascript">
    $(function() {
        $("#comparador").click(function() {
            $('#tablaSNIES tbody > tr').each(function() {
                var currentRowHTML = $(this);

                var idActual = $(currentRowHTML).find("td:first").html().trim();
                $('#tablaSMA tbody > tr').each(function() {
                    var ids = $(this).find("td:first").html().trim();
                    var idAux = new Array();
                    idAux = ids.split(",");

                    for (a in idAux) {
                        if (idAux[a].trim() === idActual) {
                            $(currentRowHTML).remove();
                            $(this).remove();
                        }
                    }
                });
            });
        });
    });
</script>
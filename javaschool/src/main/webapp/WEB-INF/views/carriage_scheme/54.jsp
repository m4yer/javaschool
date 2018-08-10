<!-- 54 seats scheme -->
<div class="col-md-12">
    <table class="ticket-table unselectable" cellspacing="0" cellpadding="0">
        <tr class="text-center">
            <td class="toilet-block toilet-left"></td>
            <td class="line-block"></td>
            <c:forEach var="i" begin="0" end="8" varStatus="loop">
                <td valign="center">
                    <div id="<c:out value="${i*4+2}"/>" class="seat top" onclick="chooseSeat(this.id)"><c:out
                            value="${i*4+2}"/></div>
                    <div id="<c:out value="${i*4+1}"/>" class="seat bottom" onclick="chooseSeat(this.id)"><c:out
                            value="${i*4+1}"/></div>
                </td>
                <td class="separate-vertical"></td>
                <td class="empty-block"></td>
                <td class="separate-vertical"></td>
                <td valign="center">
                    <div id="<c:out value="${i*4+4}"/>" class="seat top" onclick="chooseSeat(this.id)"><c:out
                            value="${i*4+4}"/></div>
                    <div id="<c:out value="${i*4+3}"/>" class="seat bottom" onclick="chooseSeat(this.id)"><c:out
                            value="${i*4+3}"/></div>
                </td>
                <td class="line-block"></td>
            </c:forEach>
            <td class="toilet-block toilet-right"></td>
        </tr>
        <tr>
            <td class="train-hall" colspan="1000"></td>
        </tr>
        <tr class="text-center">
            <td class="toilet-block-transparent toilet-left"></td>
            <td class="line-block"></td>
            <c:forEach var="i" begin="0" end="8" varStatus="loop">
                <c:set var="j" value="${27-i}"/>
                <td valign="center">
                    <div id="<c:out value="${j*2}"/>" class="seat top" onclick="chooseSeat(this.id)"><c:out
                            value="${j*2}"/></div>
                </td>
                <td class="separate-vertical"></td>
                <td class="empty-block"></td>
                <td class="separate-vertical"></td>
                <td valign="center">
                    <div id="<c:out value="${j*2-1}"/>" class="seat bottom" onclick="chooseSeat(this.id)"><c:out
                            value="${j*2-1}"/></div>
                </td>
                <td class="line-block"></td>
            </c:forEach>
            <td class="toilet-block-transparent toilet-right"></td>
        </tr>
    </table>
</div>
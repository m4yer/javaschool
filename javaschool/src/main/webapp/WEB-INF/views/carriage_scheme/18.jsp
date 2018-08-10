<!-- 18 seats scheme -->
<div class="col-md-12">
    <table class="ticket-table unselectable" cellspacing="0" cellpadding="0">
        <tr class="text-center">
            <td class="toilet-block toilet-left"></td>
            <td class="line-block"></td>
            <c:forEach var="i" begin="0" end="8" varStatus="loop">
                <td valign="center">
                    <div class="separate-horizontal"></div>
                    <div id="<c:out value="${i*2+1}"/>" class="seat seat-luxe bottom"
                         onclick="chooseSeat(this.id)"><c:out value="${i*2+1}"/></div>
                </td>
                <td class="separate-vertical"></td>
                <td class="empty-block"></td>
                <td class="separate-vertical"></td>
                <td valign="center">
                    <div id="<c:out value="${i*2+2}"/>" class="seat seat-luxe bottom"
                         onclick="chooseSeat(this.id)"><c:out value="${i*2+2}"/></div>
                </td>
                <td class="line-block"></td>
            </c:forEach>
            <td class="toilet-block toilet-right"></td>
        </tr>
        <tr>
            <td class="train-hall" colspan="100"></td>
        </tr>
    </table>
</div>
<!-- 66 seats scheme -->
<div class="col-md-12">
    <table class="ticket-table unselectable" cellspacing="0" cellpadding="0">
        <tr class="text-center">
            <td class="toilet-block toilet-left"></td>
            <td class="line-block"></td>
            <td valign="center">
                <div id="1" class="seat back-left" onclick="chooseSeat(this.id)">1</div>
                <div class="space-between-seats"></div>
                <div id="2" class="seat back-left" onclick="chooseSeat(this.id)">2</div>
            </td>
            <td class="empty-block-seating"></td>
            <c:forEach var="i" begin="0" end="15" varStatus="loop">
                <td valign="center">
                    <div id="<c:out value="${i*4+3}"/>" class="seat back-left" onclick="chooseSeat(this.id)" ng-click="chooseSeat($event)"><c:out
                            value="${i*4+3}"/></div>
                    <div class="space-between-seats"></div>
                    <div id="<c:out value="${i*4+4}"/>" class="seat back-left" onclick="chooseSeat(this.id)" ng-click="chooseSeat($event)"><c:out
                            value="${i*4+4}"/></div>
                </td>
                <td class="empty-block-seating"></td>
            </c:forEach>
            <td class="line-block"></td>
            <td class="toilet-block toilet-right"></td>
        </tr>
        <tr>
            <td class="train-hall" colspan="1000"></td>
        </tr>
        <tr class="text-center">
            <td class="toilet-block-transparent toilet-left"></td>
            <td class="line-block"></td>
            <td valign="center">
                <div class="seat seat-transparent"></div>
                <div class="space-between-seats"></div>
                <div class="seat seat-transparent"></div>
            </td>
            <td class="empty-block-seating"></td>
            <c:forEach var="i" begin="0" end="15" varStatus="loop">
                <td valign="center">
                    <div id="<c:out value="${i*4+6}"/>" class="seat back-right" onclick="chooseSeat(this.id)" ng-click="chooseSeat($event)"><c:out
                            value="${i*4+6}"/></div>
                    <div class="space-between-seats"></div>
                    <div id="<c:out value="${i*4+5}"/>" class="seat back-right" onclick="chooseSeat(this.id)" ng-click="chooseSeat($event)"><c:out
                            value="${i*4+5}"/></div>
                </td>
                <td class="empty-block-seating"></td>
            </c:forEach>
            <td class="line-block"></td>
            <td class="toilet-block-transparent toilet-right"></td>
        </tr>
    </table>
</div>
var station = $("#stationName").val();

var ws = new WebSocket("ws://localhost:8090/schedule-1.0/example");
ws.onopen = function()
{
    console.log('Web Socket is connected.');
    ws.send(station);
    console.log('Sending to backend that we need to listen schedule changes for: ' + station);
};
ws.onmessage = function (evt)
{
    var msg = evt.data;
    // msg - name of station received from ClientEndpoint.java
    // i.e - msg received from queue with the name of the station which schedule was edited
    console.log('Need to update schedule for: ' + msg);
};
ws.onclose = function()
{

    console.log('JS: Connection is closed.');
};
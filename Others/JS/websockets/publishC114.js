var ReconnectingWebSocket = require('reconnecting-websocket');
var WS = require('ws');

const options = {
    WebSocket: WS, // custom WebSocket constructor
    connectionTimeout: 1000,
    maxRetries: 10,
};

const rws = new ReconnectingWebSocket('wss://01OZLElajKpQJonw@ws.convexglobal.io/stream/2cc2aa/2cc2aa', [], options);

var dataResourceId = '2cc2aa';
var accessToken = '01OZLElajKpQJonw';


var payload = {
  "headers": {
    "Authorization": "Bearer 01OZLElajKpQJonw"
}, 
"content": {
  "searchSpan": {
    "from": "UTCDATETIME",
    "to": "UTCDATETIME"
  },
"top": {
  "sort": [
    {
      "input": {"builtInProperty": "$ts"},
      "order": "Asc"
    }], 
"count": 1000
}}};

rws.onopen = function(e) {
    rws.send(payload);
};

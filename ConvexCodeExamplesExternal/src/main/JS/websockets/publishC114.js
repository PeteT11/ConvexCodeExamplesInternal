#!/usr/bin/env node
//Simple Node-based example to publish data using HTTP
//Libraries required:

var dataResourceId = '2cc2aa';
var accessToken = '01OZLElajKpQJonw';

var https = require('https');

const WebSocket = require('ws');


var headers = {
  Authorization: 'Bearer '+accessToken
};

var options = {
  host: 'http.convexglobal.io',
  port: 443,
  path: '/stream/'+dataResourceId,
  method: 'PUT',
  headers: headers
};

const ws = new WebSocket('wss://01OZLElajKpQJonw@ws.convexglobal.io/stream/2cc2aa",');

ws.on('open', function open() {
  ws.send('something');
});

ws.on('message', function incoming(data) {
  console.log(data);
});
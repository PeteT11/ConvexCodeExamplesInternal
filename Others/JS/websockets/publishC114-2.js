#!/usr/bin/env node
//Simple Node-based example to publish data using HTTP
//Libraries required:

var dataResourceId = '2cc2aa';
var accessToken = '01OZLElajKpQJonw';

var https = require('https');

var WebSocketClient = require('websocket').client;
var ws = new WebSocketClient();


var headers = {
  Authorization: 'Bearer '+accessToken
};

var options = {
  headers: headers
};


ws.on('open', function open() {
  console.log('socket open');
  ws.send('something');
});

ws.on('message', function incoming(data) {
  console.log(data);
});

ws.on('error', function error() {
  console.log('Error establishing connection');
});

ws.connect('wss://ws.convexglobal.io/stream/2cc2aa', 'wss', options);

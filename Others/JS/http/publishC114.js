#!/usr/bin/env node
//Simple Node-based example to publish data using HTTP
//Libraries required:

var dataResourceId = '2cc2aa';
var accessToken = '01OZLElajKpQJonw';

var https = require('https');

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

var req = https.request(options, function(res) {
  console.log('STATUS: ' + res.statusCode);
  console.log('HEADERS: ' + JSON.stringify(res.headers));
  res.setEncoding('utf8');
  res.on('data', function (chunk) {
    console.log('BODY: ' + chunk);
  });
});

req.on('error', function(e) {
  console.log('problem with request: ' + e.message);
});

// write data to request body
req.write('Hello World from HTTP\n');
req.end();
#!/usr/bin/env node
//Simple Node-based example to publish data using MQTT
//Libraries required:

var dataResourceId = '2cc2aa';
var accessToken = "01OZLElajKpQJonw";

var mqtt = require('mqtt');
const fs = require('fs');

var message = "Hello";

var options = {
    username: accessToken,
    port: 8883,
    host: 'mqtt.convexglobal.io',
    protocol: 'mqtts',
    rejectUnauthorized: false
};

var topic = "stream/" + dataResourceId;
var client = mqtt.connect("mqtt.projectconvex.co.uk", options);

client.on("connect", function () {
    console.log("Connected:  " + client.connected);
    if (client.connected === true) {
        client.publish(topic, message, options);

    }
});

client.on("error", function () {
    console.log("Connection error occured");    
});



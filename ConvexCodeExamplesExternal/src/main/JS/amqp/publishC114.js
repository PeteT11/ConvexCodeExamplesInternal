amqps://01OZLElajKpQJonw@amqp.convexglobal.io:5671/stream#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

amqp.connect('amqps://01OZLElajKpQJonw@amqp.convexglobal.io:5671/stream', function(error0, connection) {
  if (error0) {
    throw error0;
  }
  connection.createChannel(function(error1, channel) {
    if (error1) {
      throw error1;
    }
    var queue = '2cc2aa';
    var msg = 'Hello world';

    //channel.assertQueue(queue, {
      //durable: true
    //});

    //channel.sendToQueue(queue, Buffer.from(msg));
    channel.publish(queue, queue, Buffer.from('Hello World!'));

    console.log(" [x] Sent %s", msg);
  });
});


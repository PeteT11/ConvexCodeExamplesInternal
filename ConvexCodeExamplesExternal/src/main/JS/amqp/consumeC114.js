#!/usr/bin/env node
//Simple Node-based example to consume data using AMQP
//Libraries required:

var dataResourceId = '2cc2aa';
var accessToken = '01OZLElajKpQJonw';

var amqp = require('amqplib/callback_api');

amqp.connect('amqps://' +accessToken +'@amqp.convexglobal.io:5671/stream', function(error0, connection) {
  if (error0) {
    throw error0;
  }
  connection.createChannel(function(error1, channel) {
    if (error1) {
      throw error1;
    }
    var exchange = dataResourceId;
    var queue = dataResourceId;

    channel.assertExchange(exchange, 'topic', {
      durable: true
    });

    channel.assertQueue(queue, {
      durable: true
    }, function(error2, q) {
      if (error2) {
        throw error2;
      }
      console.log(" [*] Waiting for messages in %s. To exit press CTRL+C", q.queue);
      channel.bindQueue(q.queue, exchange, '');

      channel.consume(q.queue, function(msg) {
        if(msg.content) {
            console.log(" [x] %s", msg.content.toString());
          }
      }, {
        noAck: true
      });
    });
  });
});

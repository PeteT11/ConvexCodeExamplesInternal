#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

amqp.connect('amqps://01OZLElajKpQJonw@amqp.convexglobal.io:5671/stream', function(error0, connection) {
  if (error0) {
    throw error0;
  }
  connection.createChannel(function(error1, channel) {
    if (error1) {
      throw error1;
    }
    var exchange = '2cc2aa';

    channel.assertExchange(exchange, 'topic', {
      durable: true
    });

    channel.assertQueue(exchange, {
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

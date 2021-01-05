//Simple Node-based example to publish data using AMQP
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
    var queue = '2cc2aa';
    var msg = 'Hello world';
   
    channel.publish(queue, queue, Buffer.from(msg));

    console.log(" [x] Sent %s", msg);
  });
});
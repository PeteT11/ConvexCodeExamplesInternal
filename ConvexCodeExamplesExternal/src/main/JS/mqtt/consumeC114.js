var mqtt = require('mqtt');

var options = {
    username: "01OZLElajKpQJonw",
    port: 8883,
    host: 'mqtt.convexglobal.io',
    protocol: 'mqtts',
    rejectUnauthorized: false

};

var topic = "stream/2cc2aa";

var mqttClient = mqtt.connect("mqtt.convexglobal.io", options);

// Mqtt error calback
mqttClient.on('error', (err) => {
   console.log(err);
   this.mqttClient.end();
});

//Setup callback
mqttClient.on('connect', () => {
   console.log(`mqtt client connected`);
});

// mqtt subscriptions
mqttClient.subscribe(topic, {qos: 0});

// When a message arrives, console.log it
mqttClient.on('message', function (topic, message) {
    console.log(message.toString());
});

mqttClient.on('close', () => {
    console.log(`mqtt client disconnected`);
});

// Sends a mqtt message to topic: mytopic
//sendMessage(message) {
//  this.mqttClient.publish(topic, message);
//}

//module.exports = MqttHandler;

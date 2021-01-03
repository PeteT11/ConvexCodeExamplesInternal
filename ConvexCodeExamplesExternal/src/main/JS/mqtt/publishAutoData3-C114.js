var mqtt = require('mqtt');
const fs = require('fs');

var message = "Hello";

var numReadingsToPublish = 275;

var options = {
    username: "01OZLElajKpQJonw",
    port: 8883,
    host: 'mqtt.convexglobal.io',
    protocol: 'mqtts',
    rejectUnauthorized: false

};

var counter = 0;

var topic = "stream/2cc2aa";

var client = mqtt.connect("mqtt.projectconvex.co.uk", options);

console.log("OBD2 Data Simulator and MQTT Publisher");
console.log("Peter Thompson, 25_07_2020, V0.1");
console.log("Message Publish Rate: 1 every 5 seconds");

client.on("connect", function () {
    console.log("Connected:  " + client.connected);

    if (client.connected == true) {

            setInterval(function() {

                var powerReduction = Math.floor(Math.random() * 11);
                var powerLevel = 100 - powerReduction;

                var temperatureReduction = getRndInteger(-5, 5);
                var temp = 30 - temperatureReduction;

                var timestamp = new Date().getTime();

                var powerReading = new Object();

                powerReading.lat =getRndLat();
                powerReading.long =getRndLong();

                powerReading.power = powerLevel;
                powerReading.temp = temp;
                powerReading.message_published = timestamp;

                client.publish(topic, JSON.stringify(powerReading), options);
                console.log(""+JSON.stringify(powerReading));

                counter++;
                if (counter > (numReadingsToPublish - 2)) counter = 0;

            }, 5000);

    }
});

function getRndInteger(min, max) {
  return Math.floor(Math.random() * (max - min) ) + min;
}

function getRndLat() {
  return 1.5 + Math.random() / 10; 
}

function getRndLong() {
  return 52.4 + Math.random() / 10;

}

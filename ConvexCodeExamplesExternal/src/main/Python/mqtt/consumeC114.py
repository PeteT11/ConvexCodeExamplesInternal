import paho.mqtt.client as mqtt
import ssl
import time

host = "mqtt.convexglobal.io"
username = "01OZLElajKpQJonw"
topic = "stream/2cc2aa"


# The callback for when the client receives a CONNACK response from the server.
def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))

# The callback for when a PUBLISH message is received from the server.
def on_message(client, userdata, message):
    print("message received " ,str(message.payload.decode("utf-8")))
    print("message topic=",message.topic)
    print("message qos=",message.qos)
    print("message retain flag=",message.retain)


client = mqtt.Client(client_id="P1", clean_session=True, userdata=None, protocol=mqtt.MQTTv311, transport="tcp")

client.username_pw_set(username="01OZLElajKpQJonw", password="")

client.on_connect = on_connect
client.on_message = on_message

client.tls_set(ca_certs=None, certfile=None, keyfile=None, cert_reqs=ssl.CERT_OPTIONAL,
    tls_version=ssl.PROTOCOL_TLS, ciphers=None)

client.connect(host, 8883, 60)

#client.publish(topic, payload="Hello World", qos=0, retain=False)

client.loop_start()

print("Subscribing to: ",topic)
client.subscribe(topic)
time.sleep(30)
client.loop_stop()
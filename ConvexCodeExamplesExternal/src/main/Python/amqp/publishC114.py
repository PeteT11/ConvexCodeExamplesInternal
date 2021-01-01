#!/usr/bin/env python
import pika
import ssl

ssl_context = ssl.create_default_context()
ssl_options = pika.SSLOptions(ssl_context, "amqp.convexglobal.io")

parameters = pika.URLParameters('amqps://01nI9pMLYRL4BWTY:guest@amqp.convexglobal.io:5671/stream')

#credentials = pika.PlainCredentials('01nI9pMLYRL4BWTY', '01nI9pMLYRL4BWTY')
#parameters = pika.ConnectionParameters(port=5671, virtual_host='stream', credentials=credentials, ssl_options=ssl_options)

connection = pika.BlockingConnection(parameters)
channel = connection.channel()

channel.queue_declare(queue='2cc2aa')

channel.basic_publish(exchange='2cc2aa', routing_key='', body='Hello World!')
print(" [x] Sent 'Hello World!'")
connection.close()

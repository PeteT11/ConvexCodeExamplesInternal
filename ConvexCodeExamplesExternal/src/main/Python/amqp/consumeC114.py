#!/usr/bin/env python
import pika
import ssl
import sys
import os

ssl_context = ssl.create_default_context()
ssl_options = pika.SSLOptions(ssl_context, "amqp.convexglobal.io")

def main():

   credentials = pika.PlainCredentials('01OZLElajKpQJonw', '')
   parameters = pika.ConnectionParameters(host='amqp.convexglobal.io', port=5671, virtual_host='stream', credentials=credentials, ssl_options=ssl_options)

   connection = pika.BlockingConnection(parameters)
   channel = connection.channel()

   #channel.queue_bind(queue='2cc2aa', exchange='2cc2aa')

   def callback(ch, method, properties, body):
        print(" [x] Received %r" % body)

   channel.basic_consume(queue='2cc2aa', on_message_callback=callback, auto_ack=True)

   print(' [*] Waiting for messages. To exit press CTRL+C')
   channel.start_consuming()

if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print('Interrupted')
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)

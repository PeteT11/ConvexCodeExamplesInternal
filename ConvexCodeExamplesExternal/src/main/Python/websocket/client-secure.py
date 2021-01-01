#!/usr/bin/env python

# WSS (WS over TLS) client example, with a self-signed certificate

import asyncio
import pathlib
import ssl
import websockets

ssl_context = ssl.SSLContext(ssl.PROTOCOL_TLS_CLIENT)
#localhost_pem = pathlib.Path(__file__).with_name("localhost.pem")
#ssl_context.load_verify_locations(localhost_pem)

async def hello():


    header = {'Authorization': 'Bearer 01OZLElajKpQJonw'}

    uri = "wss://ws.convexglobal.io/stream/2cc2aa"

    async with websockets.connect(
        uri, ssl=ssl_context, header=header
    ) as websocket:
        name = input("Enter the string to send: ")

        await websocket.send(name)
        print(f"> {name}")

        greeting = await websocket.recv()
        print(f"< {greeting}")

asyncio.get_event_loop().run_until_complete(hello())


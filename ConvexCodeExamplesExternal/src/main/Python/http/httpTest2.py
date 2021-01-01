try:
    from urllib.request import Request, urlopen  # Python 3
except ImportError:
    from urllib2 import Request, urlopen  # Python 2

req = Request('https://http.convexglobal.io/stream/2cc2aa')
req.add_header('Authorization', 'Bearer 01OZLElajKpQJonw')
content = urlopen(req).read()

print(content)

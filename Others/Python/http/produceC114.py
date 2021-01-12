import requests

headers = {'Authorization': 'Bearer 01OZLElajKpQJonw'}

data = 'hello world'

r = requests.put("https://http.convexglobal.io/stream/2cc2aa", data=data, headers=headers)
print(r.status_code, r.reason)



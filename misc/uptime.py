import matplotlib.pyplot as plt
import requests
import time
from collections import defaultdict

global_status_codes = defaultdict(lambda: 0)

def measure():
	global global_status_codes
	headers = {"connection": "close"}
	r = requests.get("http://localhost:8080/frontend",
		headers=headers)
	global_status_codes[r.status_code] += 1

def draw():
	global global_status_codes
	labels = []
	values = []
	explode = []
	for key in global_status_codes:
		labels.append("{}, {} entries".format(str(key), global_status_codes[key]))
		values.append(str(global_status_codes[key]))
		explode.append(0.02)
	plt.pie(values, autopct="%1.1f%%", labels=labels, explode=explode,
		shadow=True, startangle=130)
	plt.title("Status code stats of GET /frontend")
	plt.savefig("status.png")
	plt.close()

def loop():
	measure()
	draw()
	time.sleep(300)
	
if __name__ == "__main__":
	while True:
		loop()

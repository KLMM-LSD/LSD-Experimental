import matplotlib.pyplot as plt
from collections import defaultdict
import requests
import time

file_graph = "latencies.png"
latencies = defaultdict(lambda: 0)

def measure():
	headers = {"connection": "close"}
	r = requests.get("http://localhost:8080/latest/frontpage", headers=headers)
	status = r.status_code
	ms = int(r.elapsed.total_seconds() * 1000)
	latencies[ms] += 1

def draw():
	global file_graph
	plt.bar(list(latencies.keys()), latencies.values(), color="g")
	plt.ylabel("Amount in bucket")
	plt.title("Latency of GET /latest/frontpage in ms")
	plt.savefig(file_graph)
	plt.close()

while True:
	measure()
	draw()
	time.sleep(300)


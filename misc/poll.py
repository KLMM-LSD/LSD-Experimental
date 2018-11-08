import matplotlib.pyplot as plt
from collections import defaultdict
import requests
import time

file_latencies = "latencies.pkl"
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
	plt.bar(list(latencies.keys()), latencies.values(), color="g", alpha=0.5)
	plt.ylabel("Amount in bucket")
	plt.title("Latency of GET /latest/frontpage in ms")
	plt.savefig(file_graph)

while True:
	measure()
	draw()
	time.sleep(30)


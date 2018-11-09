import matplotlib.pyplot as plt
import fileinput

for line in fileinput.input():
	idle = float(line)
	active = 100 - idle
	labels = ["Idle {}%".format(idle), "Busy {}%".format(active)]
	sizes = [idle, active]
	colors = ["lightgreen", "red"]
	explode = [0.0, 0.0]
	plt.pie(sizes, explode=explode, labels=labels, colors=colors,
		shadow=True, startangle=130)
	plt.title("CPU Usage")
	plt.savefig("usage.png")

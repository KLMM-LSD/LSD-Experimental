import sys
import fileinput

prev = 0

def missing_callback(idx):
	sys.stdout.write("{} is missing\n".format(idx))

for line in fileinput.input():
	num = int(line)
	for x in range(prev + 1, num):
		missing_callback(x)
	prev = num

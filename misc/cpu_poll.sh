#!/bin/bash
while true; do
	mpstat | grep -Eo "[0-9]*\.[0-9]*$" | python3 plot_idle_cpu.py
	sleep 300
done

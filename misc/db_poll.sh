#!/bin/bash
if [[ $EUID -ne 0 ]]; then
	echo "Run as root"
	exit 1
fi

while true; do
	DATE=$(date +%s)
	SIZE=$(du -m /var/lib/mysql/lsd | grep -Eo "[0-9]*")
	echo "$DATE,$SIZE" >> "sizes.csv"
	sleep 3600
done


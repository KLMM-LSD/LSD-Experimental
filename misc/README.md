# Miscellaneous files

| File | Purpose |
| --- | --- |
| find_gaps.py | Check for missing numbers in a sequence from stdin, starting from 1 |
| poll.py | Check the response time and generate a histogram periodically |
| uptime.py | Generate pie chart of uptime |
| plot_idle_cpu.py | Generate pie chart of idle cpu |
| plot_size.py | Generate line chart of database size in megs |

```
$ mysql -N -uroot -proot lsd < postids.sql | python find_gaps.py
```

![](image.png)
![](status.png)
![](usage.png)
![](sizes.png)

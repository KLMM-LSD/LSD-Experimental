import matplotlib.pyplot as plt
import pandas as pd
import time

def csv_to_df(csvfile):
	return pd.read_csv(csvfile)

while True:
	dataframe = csv_to_df("sizes.csv")
	dataframe.plot(kind="line",x="timestamp",y="megabytes",color="red")
	plt.savefig("sizes.png")
	plt.close()
	time.sleep(3600)


import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

def sendMail(input):
	fromaddr = "KLMMLSD2@gmail.com"
	toaddr = "KLMMLSD2@gmail.com"
	msg = MIMEMultipart()
	msg['From'] = fromaddr
	msg['To'] = toaddr
	msg['Subject'] = "Alert!"

	body = "Be aware that it is now 100mb bigger!"
	msg.attach(MIMEText(body, 'plain'))

	server = smtplib.SMTP('smtp.gmail.com', 587)
	server.starttls()
	server.login(fromaddr, "LsdGruppe2")
	text = msg.as_string()
	server.sendmail(fromaddr, toaddr, text)
	server.quit()
	
def main():
	sendMail()
	
if __name__ == "__main__":
	main()
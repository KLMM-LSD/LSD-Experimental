# Security

| Asset | Risk | Likelihood |
| --- | --- | --- |
| SQL-Databse | Literally nothing | Rare |
| Droplet | Very low | Rare |

- Nothing worth stealing in the database; all posts are public; helge hashed passwords beforehand
- Prepared statements
- Database only accepts logins from localhost, need to copromise droplet to hack
- Escape html in posts
- SSH keys needed
- Two-factor authentication on Digitalocean
- Stateless
- No stack traces exposed to frontend

## Vulnerabilities

- Target this project: https://github.com/stanleyh007/Project_HNClone
- Checked with LGTM [results](https://raw.githubusercontent.com/KLMM-LSD/LSD-Experimental/lorem/scan.PNG)
- Attempt at social engineering
![](soceng.PNG)

## OWTF
Will use Docker and Docker-compose

TBD

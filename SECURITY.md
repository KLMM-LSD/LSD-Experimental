# Security

| Asset | Risk | Likelihood |
| --- | --- | --- |
| SQL-Databse | Literally nothing | Rare |
| Droplet | Very Low | Rare |

- Nothing worth stealing in the database; all posts are public; helge hashed asspwrods beforehand
- Prepared statements
- Database only accepts logins from localhost, need 2 copromise droplet 2 hack
- Escape html in posts
- ssh keys needed
- 2FA on Digitalocean
- Stateless
- No stack traces exposed to frontend

## Vulnberaribitlies

- Our asignment was to ATTACK this project: https://github.com/stanleyh007/Project_HNClone
- We checked with LGTM [results](scan.PNG)
- We also tried social engineerging, no respone yet
![](soceng.PNG)

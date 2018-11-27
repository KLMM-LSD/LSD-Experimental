# Security

| Asset | Risk | Likelihood |
| --- | --- | --- |
| SQL-Databse | Literally nothing | Rare |
| Droplet | Very Low | Rare |

- nothing worth stealing in the database; all posts are public; helge hashed asspwrods beforehand
- prepared statements
- db only accepts logins from localhost, need 2 copromise droplet 2 hack
- escape html in posts
- ssh keys needed
- 2fa on digitalocean
- stateless
- no stack traces exposed to frontend

## Vulnberaribitlies

our asignment was to ATTACK this project: https://github.com/stanleyh007/Project_HNClone

we checked with https://lgtm.com/
![](scan.PNG)
though these are not security related


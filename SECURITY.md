# Security

| Asset | Degree of criticalness |
| --- | --- |
| SQL-Databse | Literally nothing |
| Droplet | Very Low |

- nothing worth stealing in the database; all posts are public; helge hashed asspwrods beforehand
- prepared statements
- db only accepts logins from localhost, need 2 copromise droplet 2 hack
- escape html in posts
- ssh keys needed
- 2fa on digitalocean
- stateless
- no stack traces exposed to frontend

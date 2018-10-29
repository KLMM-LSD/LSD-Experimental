DROP DATABASE IF EXISTS lsd;
CREATE DATABASE lsd;

USE lsd;

CREATE TABLE users (
	userid		INT NOT NULL UNIQUE AUTO_INCREMENT,
	usertype	ENUM ('admin', 'user', 'banned') NOT NULL DEFAULT 'user',

	username	VARCHAR (30) BINARY NOT NULL UNIQUE,
	userpassword	VARCHAR (30) NOT NULL,

	PRIMARY KEY (userid)
);

CREATE TABLE posts (
	postid		INT NOT NULL UNIQUE,
	posttype	ENUM ('comment', 'story') NOT NULL,
	postparentid	INT,
	postauthorid	INT NOT NULL,

	postthreadid	INT,

	postcontent	VARCHAR (3000) NOT NULL,

	FOREIGN KEY (postparentid) REFERENCES posts(postid),
	FOREIGN KEY (postthreadid) REFERENCES posts(postid),
	FOREIGN KEY (postauthorid) REFERENCES users(userid),
	PRIMARY KEY (postid)
);

CREATE TABLE ratings (
	ratingid	INT NOT NULL AUTO_INCREMENT,
	ratingauthorid	INT NOT NULL,
	ratingvalue	INT NOT NULL,
	ratingpostid	INT NOT NULL,

	FOREIGN KEY (ratingauthorid) REFERENCES users(userid),
	FOREIGN KEY (ratingpostid) REFERENCES posts(postid),
	PRIMARY KEY (ratingid)
);


create database board default character set utf8;

GRANT ALL PRIVILEGES ON *.* TO 'jspexam'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'jspexam'@'%'

create table board.member (
    memberid varchar(50) primary key,
    name varchar(50) not null,
    password varchar(10) not null,
    regdate datetime not null
) engine=InnoDB default character set = utf8;

create table board.article (
    article_no int auto_increment primary key,
    writer_id varchar(50) not null,
    writer_name varchar(50) not null,
    title varchar(255) not null,
    regdate datetime not null,
    moddate datetime not null,
    read_cnt int
) engine=InnoDB default character set = utf8;

create table board.article_content (
    article_no int primary key,
    content text
) engine=InnoDB default character set = utf8;

CREATE TABLE board.comment (
	comment_no int auto_increment primary key,
	article_no int not null,
	memberid varchar(50) not null,
	name varchar(50) not null,
	parent_comment_no int default 0,
	depth int default 0,
	comment_order int not null,
	comment_content varchar(1000) not null,
	regdate datetime not null,
	moddate datetime not null,
	is_delete char(1) default 'N'
) engine=InnoDB default character set = utf-8;
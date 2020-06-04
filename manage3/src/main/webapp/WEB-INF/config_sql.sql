CREATE TABLE config
(
    idx              NUMBER           PRIMARY KEY, 
    tableName        VARCHAR2(20)     NOT NULL, 
    subject          VARCHAR2(255)    NOT NULL, 
    admin            VARCHAR2(255)    NOT NULL,
    createDate       timestamp        DEFAULT sysdate,
    list_level       NUMBER(1)        NOT NULL, 
    read_level       NUMBER(1)        NOT NULL, 
    writer_level     NUMBER(1)        NOT NULL, 
    comment_level    NUMBER(1)        NOT NULL,  
    use_secret       Char(1)          NOT NULL, 
    newIcon          NUMBER(4)        NOT NULL, 
    hotIcon          NUMBER(5)        NOT NULL, 
    gallery_cols     NUMBER(2)        NOT NULL,
    mainSelect 		 number(1) 		  DEFAULT 0
);
CREATE SEQUENCE config_idx_seq;

CREATE TABLE board (
	idx 		NUMBER PRIMARY KEY,
	cf_idx 		NUMBER NOT NULL,
	subject 	varchar2(255) NOT NULL,
	content 	varchar2(4000) NOT NULL,
	writer 		varchar2(30) NOT NULL,
	writer_nick varchar2(30) NOT NULL,
	password    varchar2(60),
	createDate  timestamp DEFAULT sysdate,
	updateDate  timestamp,
	hit 		NUMBER(10) DEFAULT 0,
	CONSTRAINT fk_cf_idx_boardName FOREIGN KEY (cf_idx) REFERENCES CONFIG(IDX) ON DELETE CASCADE
);
CREATE SEQUENCE board_idx_seq;

create table category(
	idx number primary key,
	REF NUMBER NOT NULL,
	grp NUMBER default 0,
	seq number default 0,
	lvl number default 0,
	content varchar(100) not NULL,
	CONSTRAINT fk_ref_configIdx FOREIGN KEY (ref) REFERENCES config(idx) ON DELETE cascade
);
CREATE SEQUENCE category_idx_seq;

CREATE TABLE comments(
	idx 	   NUMBER PRIMARY KEY,
	REF 	   NUMBER NOT NULL,
	mb_id	   varchar2(30) NOT NULL,
	mb_nick    varchar2(30) NOT NULL,
	createDate timestamp DEFAULT sysdate,
	content    varchar2(2000) NOT NULL,
	updateDate timestamp,
	CONSTRAINT fk_idx_boardIdx FOREIGN KEY (ref) REFERENCES board(idx) ON DELETE CASCADE
);
CREATE SEQUENCE comments_idx_seq;

CREATE TABLE files(
	idx 	 number PRIMARY KEY,
	REF 	 number NOT NULL,
	origName varchar2(200) NOT NULL,
	saveName varchar2(200) NOT NULL,
	CONSTRAINT fk_filesIdx_boardIdx FOREIGN KEY (ref) REFERENCES board(idx) ON DELETE CASCADE
);
CREATE SEQUENCE files_idx_seq;

CREATE TABLE member
(
    idx          NUMBER           PRIMARY key, 
    id           VARCHAR2(30)     NOT NULL, 
    password     VARCHAR2(60)     NOT NULL, 
    name         VARCHAR2(30)     NOT null, 
    nick         VARCHAR2(30), 
    email        VARCHAR2(30)     NOT null, 
    sex          NUMBER(1), 
    birth        TIMESTAMP, 
    hp           VARCHAR2(20), 
    certify      CHAR(1)          DEFAULT 'N', 
    zipCode      VARCHAR2(10), 
    addr         VARCHAR2(255), 
    addr2        VARCHAR2(255), 
    joinDate     TIMESTAMP        NOT NULL, 
    mailing      CHAR(1)          DEFAULT 'N', 
    sms          CHAR(1)          DEFAULT 'N', 
    leaveDate    TIMESTAMP,
    enabled 	 char(1) 		  NOT NULL
);
CREATE SEQUENCE member_idx_SEQ;

CREATE TABLE member_role
(
    idx    NUMBER          PRIMARY KEY, 
    mb_idx NUMBER   		 NOT NULL, 
    role   VARCHAR2(20)    NOT NULL, 
    CONSTRAINT fk_member_idx FOREIGN KEY (mb_idX) REFERENCES MEMBER(idx) ON DELETE CASCADE
);
CREATE SEQUENCE member_role_idx_SEQ;

CREATE TABLE banner(
	idx 	   number PRIMARY KEY,
	startDate  timestamp NOT NULL,
	endDate    timestamp NOT NULL,
	uploadDate timestamp DEFAULT sysdate,
	title 	   varchar2(300) NOT NULL,
	url 	   varchar2(300) NOT NULL,
	image 	   varchar2(200) NOT null
);
CREATE SEQUENCE banner_idx_seq;
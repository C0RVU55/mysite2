--테이블 / 시퀀스 삭제
drop table guestbook;
drop SEQUENCE seq_no;

commit;
rollback;

--테이블 생성
create table guestbook(
    no number,
    name VARCHAR2(80),
    password VARCHAR2(20),
    content VARCHAR2(2000),
    reg_date date,
    PRIMARY KEY (no)
);

--시퀀스 생성
create SEQUENCE seq_no
  INCREMENT BY 1
  start with 1;
  
--출력
SELECT  no,
        name,
        password,
        content,
        to_char(reg_date, 'YYYY-MM-DD HH24:MI:SS') reg_date
FROM guestbook;

--입력(날짜는 컬럼명이 아니라 sysdate만 넣음. select는 어떻게 보여줄지를 나타내는 거) 
insert into guestbook
VALUES(seq_no.nextval, '이다현', '1234', '안녕하세요', sysdate);

--삭제
delete from guestbook
where no=1
and password='2222';
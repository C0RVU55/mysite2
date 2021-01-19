--drop
drop table board;
drop SEQUENCE seq_board_no;

commit;
rollback;

--생성
create table board(
    no number,
    title VARCHAR2(500) not null,
    content VARCHAR2(4000),
    hit number default 0,
    reg_date date not null,
    user_no not null,
    PRIMARY KEY (no),
    CONSTRAINT board_fk FOREIGN KEY (user_no)
    REFERENCES users(no)
);

create SEQUENCE seq_board_no
INCREMENT by 1
start with 1
nocache;

--출력
SELECT
    *
FROM board;

SELECT  b.no,
        title,
        content,
        hit,
        to_char(reg_date, 'YYYY-MM-DD') reg_date,
        user_no,
        id,
        password,
        name,
        gender
FROM board b, users u
where b.user_no = u.no
order by b.no desc;

--입력
insert into board
values(seq_board_no.nextval, 'aaa', '입력테스트', 0, sysdate, 1);

insert into board
values(seq_board_no.nextval, '게시판1', '게시판글1', 0, sysdate, 1);

insert into board
values(seq_board_no.nextval, '게시판2', '게시판글2', 0, sysdate, 2);

insert into board
values(seq_board_no.nextval, '게시판3', '게시판글3', 0, sysdate, 3);

--게시글 정보 가져오기
SELECT  b.no,
        name,
        hit,
        to_char(reg_date, 'YYYY-MM-DD') reg_date,
        title,
        content,
        user_no
FROM board b, users u
where  b.user_no = u.no
and b.no = 3;

--조회수 증가
update board
set hit = hit + 1
where no = 1;

--삭제
delete from board
where no = 1;

--수정
update board
set title = '수정',
    content = 'ㅇㅇㅇ'
where no = 7;

--검색
SELECT  b.no,
        title,
        name,
        hit,
        to_char(reg_date, 'YYYY-MM-DD') reg_date,
        content
FROM board b left join users u
on b.user_no = u.no
where title like '%1%'
or content like '%내%'
or name like '%1%'
order by b.no desc;
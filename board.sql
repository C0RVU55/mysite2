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
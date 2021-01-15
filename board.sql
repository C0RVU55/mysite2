--drop
drop table board;
drop SEQUENCE seq_board_no;

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
--drop
drop table users;
drop SEQUENCE seq_users_no;

create table users(
    no number,
    id VARCHAR2(20) UNIQUE not null,
    password VARCHAR2(20) not null,
    name VARCHAR2(20),
    gender VARCHAR2(10),
    PRIMARY KEY (no)
);

--시퀀스 내부적으로 10정도 미리 잡아두기 때문에 껐다 키면 숫자 몇씩 건너뛰게 되는 경우가 있는데 
--노캐시 추가하면 속도는 늦을 수는 있어도 숫자 건너 뛰는 일은 없음.
create SEQUENCE seq_users_no
INCREMENT by 1
start with 1
nocache;

insert into users
values(seq_users_no.nextval, 'aaa', '1234', '이다현', 'f');

commit;
rollback;

--select
SELECT
    *
FROM users;
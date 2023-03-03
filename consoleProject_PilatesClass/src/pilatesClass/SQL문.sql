drop database if exists console;
create database console;
use console;

drop table if exists 회원;
create table 회원(
   회원번호_pk int auto_increment primary key , 
    아이디 varchar(20) not null unique,
    비밀번호 varchar(20) not null,
    전화번호 varchar(20) not null,
    이름 varchar(20) not null,
    등급 int not null       -- 1:일반회원[회원가입] 2:강사[강사등록화면]
);

drop table if exists 스케줄;
create table 스케줄(
   스케줄번호_pk  int auto_increment primary key,
   수강일시 datetime not null,
    금액 int not null,
    회원번호_fk int,
    foreign key( 회원번호_fk ) references 회원( 회원번호_pk ) on delete set null -- 회원탈퇴시 수업 보존
);

drop table if exists 수강내역;
create table 수강내역(
   수강내역번호 int auto_increment primary key , 
    회원번호_fk int,   -- 누가 
    스케줄번호_fk int,   -- 어떤 스케줄~
    foreign key( 회원번호_fk ) references 회원( 회원번호_pk ) on delete set null , -- 회원탈퇴시 예약내역 보존
    foreign key( 스케줄번호_fk ) references 스케줄( 스케줄번호_pk ) on delete set null -- 수업을 삭제하면 해당수업 예약도 삭제
);

-- 1. 회원가입
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( 'qwe' ,'qwe' ,'010-4444-4444' ,'유재석' , 1 );
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( 'qqq' ,'qqq' ,'010-1234-1234' ,'김현수' , 1 );
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( 'aaa' ,'aaa' ,'010-4321-4321' ,'서장훈' , 1 );
select * from 회원;
-- 2. 강사가입
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( 'asd' ,'asd' ,'010-5555-5555' ,'강호동' , 2 );
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( 'zxc' ,'zxc' ,'010-1234-4897' ,'신동엽' , 2 );

-- 2-1. 관리자 비밀번호 등록
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( '관리자' ,'1234' ,'관리자' ,'관리자' , 3 );

-- 2-2. 관리자 로그인
select * from 회원 where 등급 = 3 and 비밀번호 = 1234;

-- 2. 관리자가 수업 등록 
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-02-02 10:00:00' , 30000 , 4 );
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-03-02 11:00:00' , 30000 , 4 );
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-03-06 16:00:00' , 30000 , 4 );
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-03-13 14:00:00' , 30000 , 4 );
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-03-15 17:00:00' , 30000 , 5 );
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-03-16 14:00:00' , 30000 , 5 );

select * from 회원;
select * from 스케줄;

-- 3. 회원이 수강신청 
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 1 , 2 );


--  전체 강사들의 스케줄 
select * from 회원 , 스케줄 where 회원.회원번호_pk = 스케줄.회원번호_fk;

-- 4. 특정 강사의 스케줄만 확인 
select * from 회원 , 스케줄 where 회원.회원번호_pk = 스케줄.회원번호_fk and 스케줄.회원번호_fk = 2; -- 강사 본인것만
select * from 회원;
select * from 스케줄;
select * from 수강내역;

select * from 회원 , 수강내역 where 회원.회원번호_pk = 수강내역.회원번호_fk and 수강내역.회원번호_fk = 1; -- 학생 본인것만

-- 모든 강사 출력
select * from 회원 where 등급 = 2;
select 회원번호_pk , 아이디 , 전화번호 , 이름 from 회원 where 등급 = 2;
-- 모든 회원 출력
select * from 회원 where 등급 = 1;
select 회원번호_pk , 아이디 , 전화번호 , 이름 from 회원 where 등급 = 1;

select * from 회원;
select * from 스케줄;
select * from 수강내역;
-- 예약
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 1 , 1 );
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 5 , 4 );
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 6 , 5 );
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 6 , 3 );

-- 수강번호 스케줄 일시 , 금액 , 아이디 출력
select 수강내역번호 , 수강일시 , 금액 , 아이디 , 회원번호_pk , 스케줄번호_pk from 회원 m , 스케줄 s , 수강내역 r where m.회원번호_pk = s.회원번호_fk and s.스케줄번호_pk = r.스케줄번호_fk;
select 수강내역번호 , 수강일시 , 금액 , 아이디 , 회원번호_pk , 스케줄번호_pk from 회원 m , 스케줄 s , 수강내역 r where m.회원번호_pk = s.회원번호_fk and s.스케줄번호_pk = r.스케줄번호_fk and s.회원번호_fk = 4;

drop table if exists 강사순위;
create table 강사순위(
	회원번호_fk int,
    예약수 int ,
    순위 int ,
    foreign key( 회원번호_fk ) references 회원( 회원번호_pk )
);
select * from 강사순위;
select * from 회원;
select * from 스케줄;
select * from 수강내역 a , 스케줄 b where a.스케줄번호_fk = b.스케줄번호_pk;
-- 수강내역에 존재하는 스케줄이면 강사 정보 
select * from 수강내역 a , 스케줄 b , 회원 c where a.스케줄번호_fk = b.스케줄번호_pk and b.회원번호_fk = c.회원번호_pk;
-- 회원번호 별 그룹 후 이름과 레코드수 표시 후 레코드수 기준으로 내림차순 
select c.이름 as 강사명 , count(*) as 누적수강생 , sum( b.금액) as 총매출액  from 수강내역 a , 스케줄 b , 회원 c where a.스케줄번호_fk = b.스케줄번호_pk and b.회원번호_fk = c.회원번호_pk group by c.회원번호_pk order by count(*) desc ;
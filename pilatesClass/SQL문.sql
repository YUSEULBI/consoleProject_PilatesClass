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

drop table if exists message;
create table message(
   mno int auto_increment primary key ,
   title varchar(200) not null,
    content longtext not null ,
    state boolean not null,
    회원번호_fk int ,
    foreign key ( 회원번호_fk ) references 회원( 회원번호_pk ) on delete set null
);


-- 1. 관리자 등록
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( '관리자' ,'1234' ,'관리자' ,'관리자' , 3 );
-- 1. 회원가입
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( 'qwe' ,'qwe' ,'010-1111-1111' ,'유재석' , 1 );
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( 'qqq' ,'qqq' ,'010-2222-2222' ,'박나래' , 1 );
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( 'aaa' ,'aaa' ,'010-3333-3333' ,'서장훈' , 1 );
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( 'rrr' ,'rrr' ,'010-3434-3434' ,'홍진경' , 1 );
select * from 회원;
-- 2. 강사가입
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( 'asd' ,'asd' ,'010-5555-5555' ,'강호동' , 2 );
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( 'zxc' ,'zxc' ,'010-6666-6666' ,'신동엽' , 2 );
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( 'zzz' ,'zzz' ,'010-7777-7777' ,'장도연' , 2 );
insert into 회원( 아이디 , 비밀번호 , 전화번호 , 이름 , 등급 ) values( 'ccc' ,'ccc' ,'010-8888-8888' ,'송은이' , 2 );



-- 2-2. 관리자 로그인
select * from 회원 where 등급 = 3 and 비밀번호 = 1234;
-- 모든 강사조회
select * from 회원 where 등급 = 2;
-- 2. 관리자가 수업 등록 
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-02-02 10:00:00' , 70000 , 6 );
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-03-02 11:00:00' , 30000 , 6 );
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-03-08 14:00:00' , 30000 , 6 );
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-03-09 17:00:00' , 30000 , 6 );
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-03-10 14:00:00' , 30000 , 7 );
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-03-16 16:00:00' , 65000 , 7 );
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-03-18 17:00:00' , 30000 , 8 );
insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( '2023-03-15 12:00:00' , 35000 , 8 );



-- 3. 회원이 수강신청 
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 2 , 2 );

--  전체 강사들의 스케줄 
select * from 회원 , 스케줄 where 회원.회원번호_pk = 스케줄.회원번호_fk;

-- 4. 특정 강사의 스케줄만 확인 
select * from 회원 , 스케줄 where 회원.회원번호_pk = 스케줄.회원번호_fk and 스케줄.회원번호_fk = 2; -- 강사 본인것만

-- 5. 
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
-- 예약 ( 회원 2,3,4,5  스케줄번호 1~9)
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 2 , 1 );
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 3 , 1 );
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 4 , 1 );
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 4 , 2 );
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 3 , 3 );
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 3 , 4 );
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 4 , 5 );
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 4 , 6 );
insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( 4 , 8 );



-- 수강번호 스케줄 일시 , 금액 , 아이디 출력
select 수강내역번호 , 수강일시 , 금액 , 아이디 , 회원번호_pk , 스케줄번호_pk from 회원 m , 스케줄 s , 수강내역 r where m.회원번호_pk = s.회원번호_fk and s.스케줄번호_pk = r.스케줄번호_fk;

select 수강내역번호 , 수강일시 , 금액 , 아이디 , 회원번호_pk , 스케줄번호_pk from 회원 m , 스케줄 s , 수강내역 r where m.회원번호_pk = s.회원번호_fk and s.스케줄번호_pk = r.스케줄번호_fk and s.회원번호_fk = 4;







-- 수강내역에 존재하는 스케줄이면 강사 정보 
select * from 수강내역 a , 스케줄 b , 회원 c where a.스케줄번호_fk = b.스케줄번호_pk and b.회원번호_fk = c.회원번호_pk;

-- 회원번호 별 그룹 후 이름과 레코드수 표시 후 레코드수 기준으로 내림차순 
select c.이름 as 강사명 , count(*) as 누적수강생 , sum( b.금액) as 총매출액  from 수강내역 a , 스케줄 b , 회원 c where a.스케줄번호_fk = b.스케줄번호_pk and b.회원번호_fk = c.회원번호_pk group by c.회원번호_pk order by count(*) desc ;

-- 총매출액 수강내역의 총매출액
select count(*) as 누적예약수 , sum(스케줄.금액) as 누적매출액 from 회원,스케줄,수강내역 where 회원.회원번호_pk = 스케줄.회원번호_fk and 스케줄.스케줄번호_pk = 수강내역.스케줄번호_fk ;

-- 예약건의 수강일시 , 금액
select 수강일시,금액 from 스케줄,수강내역 where 스케줄.스케줄번호_pk = 수강내역.스케줄번호_fk;

-- 3월 예약건 수강일시
select 수강일시 , 금액 from 스케줄,수강내역 where 스케줄.스케줄번호_pk = 수강내역.스케줄번호_fk and date_format(수강일시,'%Y-%m') = '2023-03';
-- 입력한 월의 예약건수, 매출액
select count(*) as 해당월예약건 , sum(스케줄.금액) as 해당월총매출액 from 스케줄,수강내역 where 스케줄.스케줄번호_pk = 수강내역.스케줄번호_fk and date_format(수강일시,'%Y') = 2023 and date_format(수강일시,'%m') = 3 and date_format(수강일시,'%d') = 2;



select * from 회원;
select 회원번호_pk from 회원 where 등급 =1;

drop table if exists point;
create table point(
   point int ,
    reason varchar(100) ,
    daterecord datetime default now() ,
    회원번호_fk int ,
    foreign key ( 회원번호_fk ) references 회원(회원번호_pk)
); 
select * from point;
select * from point where 회원번호_fk = 1;
insert into point(point,reason,회원번호_fk) values( 100 , '구매' , 1 );
insert into point(point,reason,회원번호_fk) values( 200 , '구매2' , 1 );
insert into point(point,reason,회원번호_fk) values( -200 , '사용' , 1 );
select * from point where 회원번호_fk = 1;
select sum(point.point) from point where 회원번호_fk = 1;

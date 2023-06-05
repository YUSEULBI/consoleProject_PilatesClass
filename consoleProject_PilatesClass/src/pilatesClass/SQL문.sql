drop database if exists console;
create database console;
use console;

drop table if exists member;
create table member(
	mno int auto_increment primary key , -- 식별번호
	mid varchar(20) not null unique, -- 아이디
	mpw varchar(20) not null, -- 패스워드
	mphone varchar(20) not null, -- 전화번호
    mname varchar(20) not null, -- 이름
    mrole int not null       -- 1:일반회원[회원가입] 2:강사[강사등록화면]
);

-- 1. 관리자 등록
insert into member( mid , mpw , mphone , mname , mrole ) values( 'admin' ,'1234' ,'010-0000-0000' ,'관리자' , 3 );
-- 1. 회원가입
insert into member( mid , mpw , mphone , mname , mrole ) values( 'qwe' ,'qwe' ,'010-1111-1111' ,'유재석' , 1 );
insert into member( mid , mpw , mphone , mname , mrole ) values( 'qqq' ,'qqq' ,'010-2222-2222' ,'박나래' , 1 );
insert into member( mid , mpw , mphone , mname , mrole ) values( 'aaa' ,'aaa' ,'010-3333-3333' ,'서장훈' , 1 );
insert into member( mid , mpw , mphone , mname , mrole ) values( 'rrr' ,'rrr' ,'010-3434-3434' ,'홍진경' , 1 );
select * from member;
-- 2. 강사가입
insert into member( mid , mpw , mphone , mname , mrole ) values( 'asd' ,'asd' ,'010-5555-5555' ,'강호동' , 2 );
insert into member( mid , mpw , mphone , mname , mrole ) values( 'zxc' ,'zxc' ,'010-6666-6666' ,'신동엽' , 2 );
insert into member( mid , mpw , mphone , mname , mrole ) values( 'zzz' ,'zzz' ,'010-7777-7777' ,'장도연' , 2 );
insert into member( mid , mpw , mphone , mname , mrole ) values( 'ccc' ,'ccc' ,'010-8888-8888' ,'송은이' , 2 );

-- 2-2. 관리자 로그인
select * from member where mrole = 3 and mpw = 1234;
-- 모든 강사조회
select * from member where mrole = 2;
select mno , mid , mphone , mname from member where mrole = 2;

-- 모든 회원 출력
select * from member where mrole = 1;
select mno , mid , mphone , mname from member where mrole = 1;

drop table if exists classschedule;
create table classschedule(
	sno  int auto_increment primary key, -- 식별번호
	sdate datetime not null, -- 수강일시
	sprice int not null, -- 금액
	mno int, -- 회원번호
	foreign key( mno ) references member( mno ) on delete set null -- 회원탈퇴시 수업 보존
);
select * from classschedule;
-- 2. 관리자가 수업 등록 
insert into classschedule( sdate , sprice , mno ) values( '2023-02-02 10:00:00' , 70000 , 6 );
insert into classschedule( sdate , sprice , mno ) values( '2023-03-02 11:00:00' , 30000 , 6 );
insert into classschedule( sdate , sprice , mno ) values( '2023-03-08 14:00:00' , 30000 , 6 );
insert into classschedule( sdate , sprice , mno ) values( '2023-03-09 17:00:00' , 30000 , 6 );
insert into classschedule( sdate , sprice , mno ) values( '2023-03-10 14:00:00' , 30000 , 7 );
insert into classschedule( sdate , sprice , mno ) values( '2023-03-16 16:00:00' , 65000 , 7 );
insert into classschedule( sdate , sprice , mno ) values( '2023-03-18 17:00:00' , 30000 , 8 );
insert into classschedule( sdate , sprice , mno ) values( '2023-03-15 12:00:00' , 35000 , 8 );

--  전체 강사들의 스케줄 
select * from member , classschedule where member.mno = classschedule.mno;

-- 4. 특정 강사의 스케줄만 확인 
select * from member , classschedule where member.mno = classschedule.mno and classschedule.mno = 6; -- 강사 본인것만


drop table if exists reservation;
create table reservation(
	rno int auto_increment primary key , 
	mno int,   -- 누가 
	sno int,   -- 어떤 스케줄~
	foreign key( mno ) references member( mno ) on delete set null , -- 회원탈퇴시 예약내역 보존 , mno만 null
    foreign key( sno ) references classschedule( sno ) on delete set null -- 스케줄 삭제시 수강내역 보존, sno만 null
);
select * from reservation;
-- 회원이 수강신청 
insert into reservation( mno , sno ) values( 2 , 2 );
-- 학생 본인의 수강내역만
select * from member , reservation where member.mno = reservation.mno and reservation.mno = 2; 
select r.rno,s.sdate,s.sprice,m.mname from member m ,classschedule s,reservation r where m.mno=s.mno  and r.sno = s.sno and r.mno=2;

-- 예약 ( 회원 2,3,4,5  스케줄번호 1~9)
insert into reservation( mno , sno ) values( 2 , 1 );
insert into reservation( mno , sno ) values( 3 , 1 );
insert into reservation( mno , sno ) values( 4 , 1 );
insert into reservation( mno , sno ) values( 4 , 2 );
insert into reservation( mno , sno ) values( 3 , 3 );
insert into reservation( mno , sno ) values( 3 , 4 );
insert into reservation( mno , sno ) values( 4 , 5 );
insert into reservation( mno , sno ) values( 4 , 6 );
insert into reservation( mno , sno ) values( 4 , 8 );


-- 수강번호 스케줄 일시 , 금액 , 아이디 출력
select rno , sdate , sprice , mid , m.mno , s.sno from member m , classschedule s , reservation r where m.mno = s.mno and s.sno = r.sno;
select rno , sdate , sprice , mid , m.mno , s.sno from member m , classschedule s , reservation r where m.mno = s.mno and s.sno = r.sno and s.mno = 6;
-- 수강내역 [스케줄+강사 정보]
select * from reservation r , classschedule s , member m where r.sno = s.sno and s.mno = m.mno;

-- 강사별 매출 - 회원번호 별 그룹 후 이름과 레코드수 표시 후 레코드수 기준으로 내림차순 
select m.mname as 강사명 , count(*) as 누적수강생 , sum( s.sprice) as 총매출액  from reservation r , classschedule s , member m where r.sno = s.sno and s.mno = m.mno group by m.mno order by count(*) desc ;

-- 총매출액 수강내역의 총매출액
select count(*) as 누적예약수 , sum(s.sprice) as 누적매출액 from member m , classschedule s , reservation r where m.mno = s.mno and s.sno = r.sno ;

-- 예약건의 수강일시 , 금액
select sdate,sprice from classschedule s , reservation r where s.sno = r.sno;

-- 3월 예약건 수강일시
select sdate , sprice from classschedule s , reservation r where s.sno = r.sno and date_format(sdate,'%Y-%m') = '2023-03';

-- 입력한 날짜의 예약건수, 매출액
select count(*) as 해당월예약건 , sum(s.sprice) as 해당월총매출액 from classschedule s , reservation r where s.sno = r.sno and date_format(sdate,'%Y') = 2023 and date_format(sdate,'%m') = 3 and date_format(sdate,'%d') = 2;

select sno , sdate , sprice , mname from member , classschedule where member.mno = classschedule.mno AND classschedule.sdate >= NOW() + INTERVAL 3 HOUR;

-- 예약 취소 가능한지 확인용
select r.rno , r.mno , s.sno , s.sdate , s.sprice from reservation r , classschedule s where r.sno = s.sno and sdate > now() and  r.rno = 12;
-- 로그인 한 사람의 수강번호가 맞는지 확인
select * from reservation where rno = 12 and mno = 2;



-- 메시지(공지 기능)
drop table if exists message;
create table message(
	msgno int auto_increment primary key , --  식별번호
	title varchar(200) not null, -- 제목
	content longtext not null , -- 내용
    state boolean not null, -- 열람여부
    mno int , -- 회원번호 fk 
    foreign key ( mno ) references member( mno ) on delete set null -- 회원탈퇴시 메시지 보존 , mno만 null
);

drop table if exists point;
create table point(
	pno int auto_increment primary key, -- 식별번호
	pointvalue int , -- 포인트
    reason varchar(100) , -- 포인트변동사유
    daterecord datetime default now() , -- 포인트변동일시
    mno int , -- 회원번호 fk
    rno int , -- 예약번호 fk 어떤 예약건에 의한 것인지 확인
    foreign key ( mno ) references member(mno) on delete set null , -- 회원탈퇴시 포인트 보존, mno만 null
    foreign key ( rno ) references reservation( rno) on delete set null
); 
select * from point;
select * from point where mno = 2;
-- 예약취소시 예약시 적립된 포인트 다시 차감하도록
select * from point where pointvalue > 0 and rno = 13 and mno = 2;
insert into point ( pointvalue , reason , mno , rno ) values( 100 , "테스트" , 4 , 12 ) ;

select * from point where mno = 1;
insert into point(pointvalue,reason,mno) values( 100 , '구매' , 1 );
insert into point(pointvalue,reason,mno) values( 200 , '구매2' , 1 );
insert into point(pointvalue,reason,mno) values( -200 , '사용' , 1 );
select * from point where mno = 1;
select sum(point.pointvalue) from point where mno = 1;

drop database if exists institute_db;
create database institute_db;
use institute_db;

create table courses(
	course_id int primary key,
	course_name varchar(50) not null,
	course_desc varchar(200) not null,
	course_credits int not null,
	course_type varchar(50) not null
);

insert into courses values
('500101','OOP','Object Oriented Programming','4','Undergraduate'),
('500102','DSA','Data Structures and Algorithms','4','Undergraduate'),
('500103','DM','Discrete Mathematics','4','Undergraduate'),
('500104','AIML','Artificial Intelligence and Machine learning','8','Postgraduate'),
('500201','EG','Engineering Graphics','4','Undergraduate'),
('500202','CHE','Chemistry','4','Undergraduate'),
('500203','IOS','Input Output Systems','4','Undergraduate'),
('500204','ACN','Advanced Computer Networks','8','Postgraduate'),
('500301','MET','Metallurgy','4','Undergraduate'),
('500302','IS','Induction Systems','4','Undergraduate'),
('500303','EMS','Engine and Motor Structures','4','Undergraduate'),
('500304','AEP','Advance Energy Physics ','8','Postgraduate'),
('500401','BCH','Bio Chemistry','4','Undergraduate'),
('500402','BI','Bio Informatics','4','Undergraduate'),
('500403','IMM','Immunology','4','Undergraduate'),
('500404','BRD','Biological Reasearch Design','8','Postgraduate'),
('500501','ADC','Analog and Digital Communication','4','Undergraduate'),
('500502','AE','Analog Electronics','4','Undergraduate'),
('500503','DE','Digital Electronics','4','Undergraduate'),
('500504','EEM','Engineering Electro Magnetics','8','Postgraduate');

create table departments(
	dept_id int primary key,
	dept_name varchar(50) not null,
	dept_desc varchar(200) not null,
	dept_type varchar(50) not null
);

insert into departments values
('5001','COE','Division of Computer Engineering','Academic'),
('5002','IT','Division of Information Technology','Academic'),
('5003','MECH','Division of Mechanical Engineering','Academic'),
('5004','BIOTECH','Division of Biotechnology','Academic'),
('5005','EE','Division of Electrical Engineering','Academic'),
('5006','SA','Department of Student Affairs','Non Academic'),
('5007','PR','Department of Public Relations','Non Academic'),
('5008','IA','Internal Audit','Non Academic'),
('5009','LIB','Library Management','Non Academic'),
('5010','SECURITY','Department of security services','Non Academic'),
('5011','ACCOUNTS','Accounts Office','Non Academic'),
('5012','HRD','Department of Human Resources','Non Academic'),
('5013','ERD','Department of Employee Relations','Non Academic'),
('5014','ICT','Information and Communication Technology','Non Academic'),
('5015','ASA','Academic and Student Administration','Non Academic');


create table roles(
	role_id int primary key,
	role_name varchar(50),
	role_desc varchar(50),
	role_type varchar(50)
);

create table staff(
	staff_id int primary key,
	staff_name varchar(50) not null,
	staff_address varchar(200) not null,
	staff_dob varchar(50) not null,
	staff_sex varchar(50) not null,
	course_id int,
	role_id int,
	dept_id int not null,
	foreign key (course_id) references courses(course_id),
	foreign key (dept_id) references departments(dept_id)
);

insert into staff values
('5002','staff-b','sample-address','09-09-1999','M',500101,1001,5001),
('5003','staff-c','sample-address','09-09-1999','M',500101,1005,5001),
('5004','staff-d','sample-address','09-09-1999','M',500101,1014,5001),
('5005','staff-e','sample-address','09-09-1999','M',500101,1006,5001),
('5006','staff-f','sample-address','09-09-1999','M',null,1001,5001),
('5007','staff-g','sample-address','09-09-1999','M',null,1002,5001),
('5008','staff-h','sample-address','09-09-1999','M',null,1006,5001),
('5009','staff-i','sample-address','09-09-1999','M',500101,1001,5001),
('5010','staff-j','sample-address','09-09-1999','M',500101,1002,5001),
('5011','staff-k','sample-address','09-09-1999','M',500101,1003,5001);
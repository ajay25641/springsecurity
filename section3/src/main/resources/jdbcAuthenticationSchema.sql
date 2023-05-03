create table users(
    id int serial primary key,
	username varchar(255) not null ,
	password varchar(255) not null,
	enabled int not null
);

create table authorities (
    id int serial primary key,
	username varchar(255) not null,
	authority varchar(255) not null,
	constraint fk_authorities_users foreign key(id) references users(id)
);

INSERT INTO users (username,password,enabled) values('happy','12345',1);
INSERT INTO authorities (username,authority) VALUES('happy','write');

CREATE TABLE customer(

id serial primary key,
email text not null,
password text not null,
role text not null
);

INSERT INTO customer (email,password,role) VALUES('ajay25641@gmail.com','12345','admin');



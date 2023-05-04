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



drop table customer;

drop table users;

drop table authorities;


CREATE TABLE customer(
  customer_id serial primary key,
  name varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  mobile_num varchar(20) NOT NULL,
  password varchar(500) NOT NULL,
  role varchar(100) NOT NULL,
  created_At TIMESTAMP DEFAULT NULL

);

INSERT INTO customer(name,email,mobile_num, password, role,created_At)
            VALUES('ajay','ajay25641@gmail.com','8651789057','12345','ADMIN',CURRENT_DATE);


CREATE TABLE accounts (
  customer_id int NOT NULL,
  account_number INT primary key,
  account_type varchar(100) NOT NULL,
  branch_address varchar(200) NOT NULL,
  created_At TIMESTAMP DEFAULT NULL,
  CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE

);


INSERT INTO accounts (customer_id, account_number, account_type, branch_address, created_At)
 VALUES (1, 2780, 'Savings', '123 Main Street, New York', CURRENT_DATE);


CREATE TABLE account_transactions (
  transaction_id uuid DEFAULT uuid_generate_v4 (),
  account_number int NOT NULL,
  customer_id int NOT NULL,
  transaction_dt TIMESTAMP NOT NULL,
  transaction_summary varchar(200) NOT NULL,
  transaction_type varchar(100) NOT NULL,
  transaction_amt int NOT NULL,
  closing_balance int NOT NULL,
  created_At TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (transaction_id),
  CONSTRAINT accounts_ibfk_2 FOREIGN KEY (account_number) REFERENCES accounts (account_number) ON DELETE CASCADE,
  CONSTRAINT acct_user_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);

INSERT INTO account_transactions (account_number, customer_id, transaction_dt, transaction_summary, transaction_type,transaction_amt,
closing_balance, created_At)  VALUES (2780, 1, CURRENT_DATE-7, 'Coffee Shop', 'Withdrawal', 30,34500,CURRENT_DATE-7);

INSERT INTO account_transactions ( account_number, customer_id, transaction_dt, transaction_summary, transaction_type,transaction_amt,
closing_balance, created_At)  VALUES (2780, 1, CURRENT_DATE-6, 'Uber', 'Withdrawal', 100,34400,CURRENT_DATE-6);

INSERT INTO account_transactions ( account_number, customer_id, transaction_dt, transaction_summary, transaction_type,transaction_amt,
closing_balance, created_At)  VALUES (2780, 1, CURRENT_DATE-5, 'Self Deposit', 'Deposit', 500,34900,CURRENT_DATE-5);

INSERT INTO account_transactions (account_number, customer_id, transaction_dt, transaction_summary, transaction_type,transaction_amt,
closing_balance, created_At)  VALUES (2780, 1, CURRENT_DATE-4, 'Ebay', 'Withdrawal', 600,34300,CURRENT_DATE-4);

INSERT INTO account_transactions (account_number, customer_id, transaction_dt, transaction_summary, transaction_type,transaction_amt,
closing_balance, created_At)  VALUES ( 2780, 1, CURRENT_DATE-2, 'OnlineTransfer', 'Deposit', 700,35000,CURRENT_DATE-2);

INSERT INTO account_transactions ( account_number, customer_id, transaction_dt, transaction_summary, transaction_type,transaction_amt,
closing_balance, created_At)  VALUES (2780, 1, CURRENT_DATE-1, 'Amazon.com', 'Withdrawal', 100,34900,CURRENT_DATE-1);


CREATE TABLE loans (
  loan_number serial primary key,
  customer_id int NOT NULL,
  start_dt TIMESTAMP NOT NULL,
  loan_type varchar(100) NOT NULL,
  total_loan int NOT NULL,
  amount_paid int NOT NULL,
  outstanding_amount int NOT NULL,
  created_At TIMESTAMP DEFAULT NULL,
  CONSTRAINT loan_customer_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);

INSERT INTO loans ( customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, created_At)
 VALUES ( 1, '2020-10-13', 'Home', 200000, 50000, 150000, '2020-10-13');

INSERT INTO loans ( customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, created_At)
 VALUES ( 1, '2020-06-06', 'Vehicle', 40000, 10000, 30000, '2020-06-06');

INSERT INTO loans ( customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, created_At)
 VALUES ( 1, '2018-02-14', 'Home', 50000, 10000, 40000, '2018-02-14');

INSERT INTO loans ( customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, created_At)
 VALUES ( 1, '2018-02-14', 'Personal', 10000, 3500, 6500, '2018-02-14');

 CREATE TABLE cards (
   card_id serial primary key,
   card_number varchar(100) NOT NULL,
   customer_id int NOT NULL,
   card_type varchar(100) NOT NULL,
   total_limit int NOT NULL,
   amount_used int NOT NULL,
   available_amount int NOT NULL,
   created_At TIMESTAMP DEFAULT NULL,
   CONSTRAINT card_customer_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
 );

 INSERT INTO cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, created_At)
  VALUES ('4565XXXX4656', 1, 'Credit', 10000, 500, 9500, CURRENT_DATE);

 INSERT INTO cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, created_At)
  VALUES ('3455XXXX8673', 1, 'Credit', 7500, 600, 6900, CURRENT_DATE);

 INSERT INTO cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, created_At)
  VALUES ('2359XXXX9346', 1, 'Credit', 20000, 4000, 16000, CURRENT_DATE);


  CREATE TABLE notice_details (
    notice_id serial primary key,
    notice_summary varchar(200) NOT NULL,
    notice_details varchar(500) NOT NULL,
    notic_beg_dt TIMESTAMP NOT NULL,
    notic_end_dt TIMESTAMP DEFAULT NULL,
    created_At TIMESTAMP DEFAULT NULL,
    upTIMESTAMP_dt TIMESTAMP DEFAULT NULL,

  );

  INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, created_At, upTIMESTAMP_dt)
  VALUES ('Home Loan Interest rates reduced', 'Home loan interest rates are reduced as per the goverment guidelines. The upTIMESTAMPd rates will be effective immediately',
  CURRENT_DATE - 30, CURRENT_DATE + 30, CURRENT_DATE, null);

  INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, created_At, upTIMESTAMP_dt)
  VALUES ('Net Banking Offers', 'Customers who will opt for Internet banking while opening a saving account will get a $50 amazon voucher',
  CURRENT_DATE - 30, CURRENT_DATE + 30, CURRENT_DATE, null);

  INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, created_At, upTIMESTAMP_dt)
  VALUES ('Mobile App Downtime', 'The mobile application of the EazyBank will be down from 2AM-5AM on 12/05/2020 due to maintenance activities',
  CURRENT_DATE - 30, CURRENT_DATE + 30, CURRENT_DATE, null);

  INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, created_At, upTIMESTAMP_dt)
  VALUES ('E Auction notice', 'There will be a e-auction on 12/08/2020 on the Bank website for all the stubborn arrears.Interested parties can participate in the e-auction',
  CURRENT_DATE - 30, CURRENT_DATE + 30, CURRENT_DATE, null);

  INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, created_At, upTIMESTAMP_dt)
  VALUES ('Launch of Millennia Cards', 'Millennia Credit Cards are launched for the premium customers of EazyBank. With these cards, you will get 5% cashback for each purchase',
  CURRENT_DATE - 30, CURRENT_DATE + 30, CURRENT_DATE, null);

  INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, created_At, upTIMESTAMP_dt)
  VALUES ('COVID-19 Insurance', 'EazyBank launched an insurance policy which will cover COVID-19 expenses. Please reach out to the branch for more details',
  CURRENT_DATE - 30, CURRENT_DATE + 30, CURRENT_DATE, null);

CREATE TABLE contact_messages (
  contact_id serial primary key,
  contact_name varchar(50) NOT NULL,
  contact_email varchar(100) NOT NULL,
  subject varchar(500) NOT NULL,
  message varchar(2000) NOT NULL,
  created_At TIMESTAMP DEFAULT NULL

);



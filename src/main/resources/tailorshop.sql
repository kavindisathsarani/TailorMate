
drop database tailorShop;

create database tailorShop;

show databases;

use tailorShop;

create table customer(
                         customerId varchar(10) primary key,
                         name varchar(50),
                         gender varchar(10),
                         address varchar(50),
                         contactNumber int(10),
                         email varchar(50)
);


create table measurement(
                            measurementId varchar(10) primary key,
                            neckSize double(10,1),
                            armhole double(10,1),
                            sleeveLength double(10,1),
                            wrist double(10,1),
                            chest double(10,1),
                            torsoLength double(10,1),
                            waist double(10,1),
                            hip double(10,1),
                            crotchLength double(10,1),
                            shoulderLength double(10,1),
                            thighCircumference double(10,1),
                            waistToHem double(10,1),
                            employeeId varchar(10),
                            customerId varchar(10)
);


create table employee(
                         employeeId varchar(10) primary key,
                         name varchar(50),
                         address varchar(50),
                         contactNumber int(10),
                         position varchar(10)
);


create table material(
                         materialId varchar(10) primary key,
                         description varchar(50),
                         qty double(10,1),
                         unitPrice decimal(10,2),
                         customerId varchar(10)
);


create table garment(
                        garmentId varchar(10) primary key,
                        name varchar(50),
                        description varchar(50),
                        category varchar(30),
                        size varchar(20),
                        qtyOnHand double(10,1),
                        materialCost decimal(10,2),
                        towage decimal(10,2),
                        totalPrice decimal(10,2)
);


create table materialDetail(
                               garmentId varchar(10),
                               materialId varchar(10),
                               qty int(6)
);


create table garmentDetail(
                              garmentId varchar(10),
                              employeeId varchar(10)
);

create table orders(
                       orderId varchar(10) primary key,
                       startDate date,
                       dueDate date,
                       status varchar(20),
                       customerId varchar(10)
);

create table orderDetail(
                            orderId varchar(10),
                            garmentId varchar(10),
                            qty int(6)
);


create table payment(
                        paymentId varchar(10) primary key,
                        TotalCost decimal(10,2),
                        amount decimal(10,2),
                        balance decimal(10,2),
                        status varchar(20),
                        date date,
                        orderId varchar(10)
);




alter table measurement
    add foreign key(employeeId) references employee(employeeId) on update cascade on delete cascade;

alter table measurement
    add foreign key(customerId) references customer(customerId) on update cascade on delete cascade;

alter table material
    add foreign key(customerId) references customer(customerId) on update cascade on delete cascade;

alter table materialDetail
    add foreign key(garmentId) references garment(garmentId) on update cascade on delete cascade;

alter table materialDetail
    add foreign key(materialId) references material(materialId) on update cascade on delete cascade;

alter table garmentDetail
    add foreign key(garmentId) references garment(garmentId) on update cascade on delete cascade;

alter table garmentDetail
    add foreign key(employeeId) references employee(employeeId) on update cascade on delete cascade;

alter table orders
    add foreign key(customerId) references customer(customerId) on update cascade on delete cascade;

alter table orderDetail
    add foreign key(orderId) references orders(orderId) on update cascade on delete cascade;

alter table orderDetail
    add foreign key(garmentId) references garment(garmentId) on update cascade on delete cascade;

alter table payment
    add foreign key(orderId) references orders(orderId) on update cascade on delete cascade;







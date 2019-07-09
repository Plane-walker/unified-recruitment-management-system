drop table if exists comments;
drop table if exists apply;
drop table if exists positions;
drop table if exists applicant;
drop table if exists company;

/*==============================================================*/
/* Table: company                                               */
/*==============================================================*/
create table company
(
   ID                   varchar(30) not null,
   pwHash               varchar(32) not null,
   name                 varchar(30) not null,
   country              varchar(30) not null,
   avator               varchar(200) not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: applicant                                             */
/*==============================================================*/
create table applicant
(
   ID                   varchar(30) not null,
   pwHash               varchar(32) not null,
   name                 varchar(30) not null,
   com_ID               varchar(30),
   pos_name             varchar(30),
   gender               varchar(10) not null,
   country              varchar(30) not null,
   phone                varchar(30),
   email                varchar(40),
   avator               varchar(200) not null,
   primary key (ID)
);

alter table applicant add constraint FK_Reference_3 foreign key (com_ID)
      references company (ID) on delete restrict on update restrict;

/*==============================================================*/
/* Table: positions                                             */
/*==============================================================*/
create table positions
(
   com_ID               varchar(30) not null,
   com_name             varchar(30) not null,
   name                 varchar(30) not null,
   information          varchar(200),
   hits                 bigint not null,
   salary               double not null,
   city                 varchar(30) not null,
   academic             varchar(30),
   number               int not null,
   type                 varchar(20) not null,
   primary key (com_ID, name)
);

alter table positions add constraint FK_Reference_4 foreign key (com_ID)
      references company (ID) on delete restrict on update restrict;

/*==============================================================*/
/* Table: apply                                                 */
/*==============================================================*/
create table apply
(
   app_ID               varchar(30) not null,
   com_ID               varchar(30) not null,
   name                 varchar(30) not null,
   material             varchar(200),
   status               varchar(10) not null,
   information          varchar(200),
   datetime             datetime not null,
   primary key (app_ID, com_ID, name)
);

alter table apply add constraint FK_Reference_1 foreign key (app_ID)
      references applicant (ID) on delete restrict on update restrict;

alter table apply add constraint FK_Reference_5 foreign key (com_ID, name)
      references positions (com_ID, name) on delete restrict on update restrict;

/*==============================================================*/
/* Table: comments                                              */
/*==============================================================*/
create table comments
(
   datetime             datetime not null,
   app_ID               varchar(30) not null,
   com_ID               varchar(30) not null,
   name                 varchar(30) not null,
   comment              varchar(200) not null,
   primary key (datetime, app_ID, com_ID, name)
);

alter table comments add constraint FK_Reference_6 foreign key (app_ID)
      references applicant (ID) on delete restrict on update restrict;

alter table comments add constraint FK_Reference_7 foreign key (com_ID, name)
      references positions (com_ID, name) on delete restrict on update restrict;



drop trigger if exists employ;
DELIMITER !!
create trigger employ after update on applicant for each row
begin
if new.com_ID is not null then
delete from apply
where app_ID=new.ID;
end if;
end
!!
DELIMITER ;
drop trigger if exists commentdel;
DELIMITER !!
create trigger commentdel before delete on positions for each row
begin
delete from comments
where comments.com_ID=old.com_ID and comments.name=old.name;
end
!!
DELIMITER ;
drop trigger if exists commentadd;
DELIMITER !!
create trigger commentadd after insert on comments for each row
begin
update positions
set positions.hits=positions.hits+1
where positions.com_ID=new.com_ID and positions.name=new.name;
end
!!
DELIMITER ;
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
   ID                   varchar(20) not null,
   pwHash               varchar(32),
   name                 varchar(20),
   country              varchar(20),
   avator               varchar(150),
   primary key (ID)
);

/*==============================================================*/
/* Table: applicant                                             */
/*==============================================================*/
create table applicant
(
   ID                   varchar(20) not null,
   pwHash               varchar(32),
   name                 varchar(20),
   com_ID               varchar(20),
   gender               varchar(4),
   country              varchar(20),
   phone                varchar(20),
   email                varchar(40),
   avator               varchar(150),
   primary key (ID)
);

alter table applicant add constraint FK_Reference_3 foreign key (com_ID)
      references company (ID) on delete restrict on update restrict;

/*==============================================================*/
/* Table: positions                                             */
/*==============================================================*/
create table positions
(
   com_ID               varchar(20) not null,
   name                 varchar(20) not null,
   information          varchar(150),
   begintime            datetime not null,
   endtime              datetime not null,
   primary key (com_ID, name, begintime, endtime)
);

alter table positions add constraint FK_Reference_4 foreign key (com_ID)
      references company (ID) on delete restrict on update restrict;


/*==============================================================*/
/* Table: apply                                                 */
/*==============================================================*/
create table apply
(
   app_ID               varchar(20) not null,
   com_ID               varchar(20) not null,
   name                 varchar(20) not null,
   material             varchar(150),
   status               varchar(5) not null,
   information          varchar(200),
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
   app_ID               varchar(20) not null,
   com_ID               varchar(20) not null,
   datetime             datetime not null,
   comment              varchar(150) not null,
   primary key (app_ID, com_ID, datetime)
);

alter table comments add constraint FK_Reference_6 foreign key (app_ID)
      references applicant (ID) on delete restrict on update restrict;

alter table comments add constraint FK_Reference_7 foreign key (com_ID)
      references company (ID) on delete restrict on update restrict;

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
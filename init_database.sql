create table CUSTOMERS
(
  CUSTNAME VARCHAR2(20) not null
    constraint CUSTOMERS_PK
      primary key,
  AGE      NUMBER       not null
)
/

create table RESOURCES
(
  UNI_ID    VARCHAR2(20) not null
    constraint RESOURCES_PK
      primary key,
  PRICE     NUMBER       not null,
  NUM_TOTAL NUMBER       not null,
  NUM_AVIAL NUMBER       not null
)
/

create table FLIGHTS
(
  ID         VARCHAR2(20) not null
    constraint FLIGHTS_PK
      primary key
    constraint FLIGHTS_RESOURCES_ID_FK
      references RESOURCES,
  FLIGHT_NUM VARCHAR2(20) not null,
  FROM_CITY  VARCHAR2(20),
  ARIV_CITY  VARCHAR2(20)
)
/

create table RESERVATIONS
(
  CUSTNAME VARCHAR2(20) not null
    constraint RESERVATIONS_NAME_FK
      references CUSTOMERS,
  TYPE     NUMBER,
  RESVKEY  VARCHAR2(20) not null
    constraint RESERVATIONS_ID_FK
      references RESOURCES,
  constraint RESERVATIONS_PK
    primary key (CUSTNAME, RESVKEY)
)
/

create or replace trigger TRIGGER2
  after insert or update of NUM_TOTAL
  on RESOURCES
  for each row
declare
  v_resv_count int;
begin
  select count(*) into v_resv_count from reservations where RESVKEY = :new.UNI_ID;
  IF :new.num_avial + v_resv_count != :new.num_total THEN
    RAISE_APPLICATION_ERROR(-20002, ' :new.num_avial+v_resv_count !=:new.num_total');
  END IF;
end;
/

create or replace trigger TRIGGER1
  after insert or delete
  on RESERVATIONS
  for each row
declare
  v_resv_key  VARCHAR2(20);
  v_num_avial int;
  num_total   int;
begin
  case
    when inserting then
      v_resv_key := :new.resvkey;
    when deleting then
      v_resv_key := :old.resvkey;
    end case;
  select num_avial,num_total into v_num_avial,num_total from resources where UNI_ID = v_resv_key;
  case
    when inserting then
      v_num_avial := v_num_avial - 1;
    when deleting then
      v_num_avial := v_num_avial + 1;
    end case;
  IF v_num_avial < 0 or v_num_avial > num_total THEN
    RAISE_APPLICATION_ERROR(-20001, ' num_avil must be >=0 and <=num_total');
  END IF;
  update resources set num_avial=v_num_avial where UNI_ID = v_resv_key;
end;
/

create table EMERGENT_CONTRACT
(
  CUSTNAME      VARCHAR2(20) not null
    constraint EMERGENT_FK
      references CUSTOMERS
        on delete cascade,
  CONTRACT_NAME VARCHAR2(20) not null,
  PHONE         VARCHAR2(20) not null,
  constraint EMERGENT_CONTRACT_PK
    primary key (CUSTNAME, CONTRACT_NAME)
)
/

create table HOTELS
(
  ID       VARCHAR2(20) not null
    constraint HOTELS_PK
      primary key
    constraint HOTELS_RESOURCES_ID_FK
      references RESOURCES,
  NAME     VARCHAR2(20) not null,
  LOCATION VARCHAR2(20) not null
)
/

create table CARS
(
  ID       VARCHAR2(20) not null
    constraint CARS_PK
      primary key
    constraint CARS_RESOURCES_ID_FK
      references RESOURCES,
  TYPE     VARCHAR2(20) not null,
  LOCATION VARCHAR2(20) not null
)
/




create table HOTELS
(
  ID         VARCHAR2(20) not null
    constraint HOTELS_PK
      primary key
    constraint HOTELS_RESOURCES_ID_FK
      references RESOURCES,
  NAME VARCHAR2(20) not null,
  LOCATION  VARCHAR2(20) not null
);

create table CARS
(
  ID         VARCHAR2(20) not null
    constraint CARS_PK
      primary key
    constraint CARS_RESOURCES_ID_FK
      references RESOURCES,
  TYPE VARCHAR2(20) not null,
  LOCATION  VARCHAR2(20) not null
);

CREATE OR REPLACE TRIGGER trigger1
after insert or delete on reservations
FOR EACH ROW
declare 
v_resv_key VARCHAR2(20);
v_num_avial int;
num_total int;
begin
    case
        when inserting then
          v_resv_key:= :new.resvkey;
        when deleting then
          v_resv_key:= :old.resvkey;
    end case;
    select num_avial,num_total into v_num_avial,num_total from resources where UNI_ID=v_resv_key;
    case
        when inserting then
          v_num_avial:=v_num_avial-1;
        when deleting then
          v_num_avial:=v_num_avial+1;
    end case;
  IF v_num_avial<0 or v_num_avial>num_total THEN 
      RAISE_APPLICATION_ERROR(-20001, ' num_avil must be >=0 and <=num_total');
  END IF;
  update resources set num_avial=v_num_avial where UNI_ID=v_resv_key; 
end;

CREATE OR REPLACE TRIGGER trigger2
after  insert or update of num_total on resources
FOR EACH ROW
declare 
v_resv_count int;
begin
  select count(*) into v_resv_count from reservations where RESVKEY=:new.UNI_ID;
  IF  :new.num_avial+v_resv_count !=:new.num_total THEN 
      RAISE_APPLICATION_ERROR(-20002, ' :new.num_avial+v_resv_count !=:new.num_total');
  END IF;
end;

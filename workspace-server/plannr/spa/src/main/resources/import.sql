
insert into TBL_USER(ACTIVATION_SALT,COUNTRY_CODE,EMAIL,FIRST_NAME,LAST_NAME,PASSWORD,SELF_REGISTERED,VALIDATED) VALUES(1234,'CHE','raffael.schmid@plannr.ch','Raffael','Schmid','plannr',1,1);
insert into TBL_USER(ACTIVATION_SALT,CITY,COUNTRY_CODE,STREET1,ZIP,EMAIL,FIRST_NAME,LAST_NAME,PASSWORD,SELF_REGISTERED,VALIDATED) VALUES(1234,'Zuerich','CHE','-',8000,'flavor.flav@plannr.ch','Flavor','Flav','plannr',1,1);

insert into TBL_ROLE(ROLE_NAME) values('administrator');
	
insert into TBL_TEAM(NAME, DESCRIPTION,OWNER_ID) values('Fiftynine GmbH - Projektteam Plannr','Fiftynine GmbH - Projektteam Plannr',1);
insert into TBL_TEAM(NAME, DESCRIPTION,OWNER_ID) values('Fiftynine GmbH - Projektteam Mobile','Fiftynine GmbH - Projektteam Mobile',1);
insert into TBL_TEAM(NAME, DESCRIPTION,OWNER_ID) values('Fiftynine GmbH - Projektteam Scala/Lift','Fiftynine GmbH - Projektteam Scala/Lift',1);


insert into MEMBERSHIP(USER_ID,TEAM_ID) values(2,1);
insert into MEMBERSHIP(USER_ID,TEAM_ID) values(2,2);


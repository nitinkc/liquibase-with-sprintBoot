# liquibase-with-sprintBoot

Begin with the following dependencies


Create the following schema so that the initial setup so that create table adn insert statements with them can be done

```sql
CREATE SCHEMA `liquibaseLearn`;

CREATE USER 'nitin'@'localhost' IDENTIFIED BY 'E@syP@$$';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE, SHOW VIEW, CREATE, ALTER, REFERENCES, INDEX, CREATE VIEW, CREATE ROUTINE,
	ALTER ROUTINE, EVENT, DROP, TRIGGER ON `liquibaseLearn`.* TO 'nitin'@'localhost';
FLUSH PRIVILEGES;
```



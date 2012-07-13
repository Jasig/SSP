This maven project provides an interface to the liquibase tool for managing the integration database of SSP.

If you are just using the defaults, run this tool.  It will create tables and views based on those tables.

:TODO How to change/add database drivers.

To apply all ChangeSets
mvn clean compile properties:read-project-properties liquibase:update

To Rollback a ChangeSet
mvn clean compile properties:read-project-properties liquibase:rollback -Dliquibase.rollbackCount=1
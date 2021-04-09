package com.nitin.liquibase.med;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

@Slf4j
public abstract class AbstractJavaChange implements CustomTaskChange {

    protected abstract void execute(Connection connection) throws Exception;

    @Override
    public void execute(Database database) throws CustomChangeException {
        try {
            JdbcConnection databaseConnection = (JdbcConnection) database.getConnection();
            execute(databaseConnection.getWrappedConnection());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomChangeException(e);
        }
    }

    public String getConfirmationMessage() {
        return null;
    }

    public void setUp() {
    }

    public void setFileOpener(ResourceAccessor resourceAccessor) {
    }

    public ValidationErrors validate(Database database) {
        return null;
    }
}

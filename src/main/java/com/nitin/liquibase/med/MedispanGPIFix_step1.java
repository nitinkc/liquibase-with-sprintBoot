package com.nitin.liquibase.med;

import java.sql.Connection;

public class MedispanGPIFix_step1 extends AbstractJavaChange {
    @Override
    protected void execute(Connection connection) throws Exception {
        MedispanGPIFix.step1(connection);
    }
}

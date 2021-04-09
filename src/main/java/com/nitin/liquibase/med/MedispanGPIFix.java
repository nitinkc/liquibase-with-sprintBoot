package com.nitin.liquibase.med;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * update medication_GPI column of MED_ORDER Table from corresponding MEDISPAN_GPI column of REF_MEDICATION table.
 */
@Slf4j
public class MedispanGPIFix {

    private static final int BATCH_SIZE = 400;
    private static final int FETCH_SIZE = 1000;

    private static String SELECT_MEDISPAN_GPI_REF_MEDICATION_SQL
            = "SELECT MEDICATION_CODE_ID,"
            .concat("MEDISPAN_GPI ")
            .concat("FROM REF_MEDICATION");

    private static String SELECT_MED_ORDER_SQL
            = "SELECT MED_ORDER_ID, MEDICATION_CODE_ID from MED_ORDER"
            .concat(" where MEDICATION_GPI is null");

    private static String UPDATE_MEDICATION_CODEID_MEDORDER_SQL
            = "UPDATE MED_ORDER SET MEDICATION_GPI = ?,"
            .concat(" UPDATE_DATE_TIME_GMT=?, RECORD_VERSION=?, UPDATE_RELEASE_NUMBER=? WHERE MEDICATION_CODE_ID=? ");


    public static void step1(Connection connection) throws SQLException {
        updateMedOrderTable(connection, "MED_ORDER", SELECT_MEDISPAN_GPI_REF_MEDICATION_SQL, UPDATE_MEDICATION_CODEID_MEDORDER_SQL);
    }

    private static void updateMedOrderTable(Connection connection, String tableName, String selectSql, String updateSql) throws SQLException {
        log.info("STEP1: processing started:");
        log.info(selectSql);
        // Connection config
        connection.setAutoCommit(false);
        Statement selectStatement = connection.createStatement();
        selectStatement.setFetchDirection(ResultSet.FETCH_FORWARD);
        //selectStatement.setFetchSize(FETCH_SIZE);

        //Fetch MEDICATION_CODE_ID and MEDISPAN_GPI to prepare Map
        Map<String, String> gpiMap = new HashMap<>();

        ResultSet resultSetForMap = selectStatement.executeQuery(selectSql);
        while (resultSetForMap.next()) {
            String medicationCodeId = resultSetForMap.getString("MEDICATION_CODE_ID");
            String medispanGPI = resultSetForMap.getString("MEDISPAN_GPI");
            gpiMap.put(medicationCodeId, medispanGPI);
        }

        //History Table is not present for Med_Order Table

        // Populate UPDATE query values from the map and EXECUTE as BATCH
        int batch_incrementer = 0;
        boolean pendindingToCommit = false;

        ResultSet resultSet = selectStatement.executeQuery(SELECT_MED_ORDER_SQL);

        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql);) {
            while (resultSet.next()) {
                if (prepareUpdateQuery(resultSet, updateStatement, gpiMap, tableName)) {
                    updateStatement.addBatch();
                    pendindingToCommit = true;
                    if (++batch_incrementer >= BATCH_SIZE) {
                        batch_incrementer = 0;
                        pendindingToCommit = false;
                        updateStatement.executeBatch();
                        connection.commit();
                        log.info("STEP1: Batch Committed");
                    }
                }
            }
            if (pendindingToCommit) {
                updateStatement.executeBatch();
                connection.commit();
                log.info("STEP1: Batch Committed from pendindingToCommit");
            }
        }
        selectStatement.close();
        log.info("STEP1: processing completed");
    }

    private static boolean prepareUpdateQuery(ResultSet resultSet, PreparedStatement updateStatement, Map<String, String> gpiMap, String tablename)
            throws SQLException {
        try {
            Long record_version = resultSet.getLong("RECORD_VERSION") + 1;
            Long updateReleaseNumber = 13L;
            Timestamp updateDateTimeGMT = Timestamp.from(Instant.now());

            log.info(" Updating the Table with PK (MED_ORDER_ID) :: " + resultSet.getString(tablename + "_ID"));

            //UPDATE MED_ORDER SET MEDICATION_GPI = ?, UPDATE_DATE_TIME_GMT=?, RECORD_VERSION=?, UPDATE_RELEASE_NUMBER=?
            // WHERE MEDICATION_CODE_ID=?
            updateStatement.setString(1, gpiMap.get(resultSet.getString("MEDICATION_CODE_ID")));//MEDISPAN_GPI
            updateStatement.setTimestamp(2, updateDateTimeGMT);
            updateStatement.setLong(3, record_version);
            updateStatement.setLong(4, updateReleaseNumber);
            //updateStatement.setString(5,resultSet.getString("MED_ORDER_ID"));
            updateStatement.setString(5,"2.16.840.1.113883.3.2390.2.2.700:1000");

            return true;
        } catch (Exception e) {
            log.error(String.format("Exception: For the Primary_KEY :%s : %s", resultSet.getString(tablename + "_ID"), e.toString()));
            return false;
        }
    }
}
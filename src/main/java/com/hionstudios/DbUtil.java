package com.hionstudios;

import org.javalite.activejdbc.Base;

import java.sql.SQLException;

/**
 * This file wraps all the Active JDBC's essential methods
 */
public class DbUtil {
    /**
     * Opens a Transaction
     *
     * @throws SQLException if Database is not accessible
     */
    public static void openTransaction() throws SQLException {
        open();
        Base.connection().setAutoCommit(false);
        Base.openTransaction();
    }

    /**
     * Opens a non-transactional connection
     */
    public static void open() {
        if (!Base.hasConnection()) {
            Base.open();
        }
    }

    /**
     * Commit the changes made in a Transaction
     */
    public static void commitTransaction() {
        if (Base.hasConnection()) {
            Base.commitTransaction();
        }
    }

    /**
     * Closes a Transaction
     */
    public static void close() {
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    /**
     * Rollback a Transaction
     */
    public static void rollback() {
        if (Base.hasConnection()) {
            Base.rollbackTransaction();
        }
    }
}
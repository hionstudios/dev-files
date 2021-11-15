package com.hionstudios;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This file has the methods to open, close a database connection
 * It wraps Active JDBC's methods wrapped in the DbUtil file and also handles the exceptions
 */
public interface DbTransaction {
    Logger LOGGER = Logger.getLogger(DbTransaction.class.getName());

    /**
     * Use this method to open a database connection.
     * This is read-only
     * The connection is closed after fetching the required data
     *
     * @return response of the method() overloaded
     */
    default MapResponse read() {
        try {
            DbUtil.open();
            return method();
        } finally {
            DbUtil.close();
        }
    }

    /**
     * Use this method to open a transaction
     * This method is used in the case of Write, Delete, Update operations
     * Read operation is also allowed
     * In case of any exception, the transaction is roll-backed
     * After successful execution of the method, the transaction is committed
     *
     * @return Response as a MapResponse
     */
    default MapResponse write() {
        MapResponse o = null;
        try {
            DbUtil.openTransaction();
            o = method();
            if ("failed".equals(o.getString("status"))) {
                String m = o.getString("message");
                throw new Exception(m != null ? m : "");
            }
            DbUtil.commitTransaction();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            DbUtil.rollback();
        } finally {
            DbUtil.close();
        }
        return o;
    }

    /**
     * This method has to be overloaded
     * The response has to be defined in this method
     *
     * @return Response data as a MapResponse
     */
    MapResponse method();
}

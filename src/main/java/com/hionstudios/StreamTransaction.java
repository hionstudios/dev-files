package com.hionstudios;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface StreamTransaction {
    Logger LOGGER = Logger.getLogger(StreamTransaction.class.getName());

    default void write() {
        try {
            DbUtil.openTransaction();
            method();
            DbUtil.commitTransaction();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            DbUtil.rollback();
        } finally {
            DbUtil.close();
        }
    }

    default void read() {
        try {
            DbUtil.open();
            method();
        } finally {
            DbUtil.close();
        }
    }

    void method();
}

package org.penguinframework.test.type;

import org.dbunit.operation.DatabaseOperation;

public enum OperationType {
    CLEAN_INSERT(DatabaseOperation.CLEAN_INSERT), INSERT(DatabaseOperation.INSERT);

    private DatabaseOperation databaseOperation;

    private OperationType(DatabaseOperation databaseOperation) {
        this.databaseOperation = databaseOperation;
    }

    public DatabaseOperation getDatabaseOperation() {
        return this.databaseOperation;
    }
}

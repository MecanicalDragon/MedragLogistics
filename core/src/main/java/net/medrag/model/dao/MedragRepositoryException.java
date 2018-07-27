package net.medrag.model.dao;

import javax.persistence.PersistenceException;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class MedragRepositoryException extends Exception {

    public enum OperationType {

        SAVE("Could not save in the database "),
        MERGE("Could not merge in the database "),
        SAVE_OR_UPDATE("Could not save or update in the database "),
        REMOVE("Could not remove from the database "),
        FIND("Could not find in the database "),
        BY_SIMPLE_NAME("Could not find by simple name in the database "),
        UPDATE("Could not update in the database "),
        LIST("Could not extract from the database list of "),
        REFRESH("Could not refresh ");

        private String failedOperation;

        OperationType(String failedOperation){
            this.failedOperation = failedOperation;
        }

        @Override
        public String toString(){
            return failedOperation;
        }

    }

    public MedragRepositoryException(String message) {
        super(message);
    }

    public MedragRepositoryException(Throwable cause) {
        super(cause);
    }

    public MedragRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }



}

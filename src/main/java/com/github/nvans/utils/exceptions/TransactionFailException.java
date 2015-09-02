package com.github.nvans.utils.exceptions;

/**
 * Created by nvans on 02.09.2015.
 *
 * @author Ivan Konovalov
 */
public class TransactionFailException extends RuntimeException {

    public TransactionFailException() {
    }

    public TransactionFailException(String msg) {
        super(msg);
    }

    public TransactionFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionFailException(Throwable cause) {
        super(cause);
    }
}

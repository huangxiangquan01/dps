package cn.xqhuang.dps.transaction;



public interface TransactionalExecutor {

    Object execute() throws Throwable;

    TransactionalInfo getTransactionInfo();
}

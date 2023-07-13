/*
 * package com.cig.core.dal.datasource.jta;
 * 
 * import javax.transaction.TransactionManager; import
 * javax.transaction.UserTransaction;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.transaction.PlatformTransactionManager; import
 * org.springframework.transaction.jta.JtaTransactionManager;
 * 
 * import com.atomikos.icatch.jta.UserTransactionImp; import
 * com.atomikos.icatch.jta.UserTransactionManager;
 * 
 * @Configuration public class JtaConfiguration {
 * 
 *//**
	 * jta事务管理
	 * 
	 * @return
	 * @throws Throwable
	 */
/*
 * @Bean(name = "jtaTransactionManager") public PlatformTransactionManager
 * jtaTransactionManager() throws Throwable { return new
 * JtaTransactionManager(userTransaction(), transactionManager()); }
 * 
 *//**
	 * 
	 * @return
	 * @throws Throwable
	 */
/*
 * @Bean public UserTransaction userTransaction() throws Throwable {
 * UserTransactionImp userTransactionImp = new UserTransactionImp();
 * userTransactionImp.setTransactionTimeout(10 * 1000); return
 * userTransactionImp; }
 * 
 *//**
	 * 
	 * @return
	 * @throws Throwable
	 *//*
		 * @Bean(initMethod = "init", destroyMethod = "close") public TransactionManager
		 * transactionManager() throws Throwable { UserTransactionManager
		 * transactionManager = new UserTransactionManager();
		 * transactionManager.setForceShutdown(false); return transactionManager; }
		 * 
		 * }
		 */
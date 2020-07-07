package com.hsm.cache;

import com.hsm.db.bean.TAccount;

import java.io.PrintWriter;

public class AccountCaffeineCache extends CaffeineCacheBase<Integer, TAccount> {
    public static final AccountCaffeineCache INSTANCE = new AccountCaffeineCache();

    @Override
    public void printStatistics(PrintWriter printer) {
        super.printStatistics(printer);
    }

    @Override
    public TAccount get(Integer key) {
        TAccount account = new TAccount();
        account.accountId = 1;

        return account;
    }

    @Override
    public TAccount loadValue(Integer key) {
        return super.loadValue(key);
    }

    @Override
    public void saveValue(Integer key, TAccount value) {
        super.saveValue(key, value);
    }
}

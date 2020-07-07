package com.hsm.cache;

import com.hsm.db.bean.TAccount;

public class AccountGuavaCache extends CacheBase<Integer, TAccount> {
    public static final AccountGuavaCache INSTANCE = new AccountGuavaCache();

    @Override
    public TAccount get(Integer key) {

        TAccount account = new TAccount();
        account.accountId = 1;

        return account;
    }

    @Override
    protected TAccount loadValue(Integer key) throws Exception {
        return super.loadValue(key);
    }

    @Override
    protected void saveValue(Integer key, TAccount value) {
        super.saveValue(key, value);
    }
}

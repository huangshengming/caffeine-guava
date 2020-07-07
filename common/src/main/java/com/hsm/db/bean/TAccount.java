package com.hsm.db.bean;

import java.util.Objects;

public class TAccount {
    public long id;
    public int accountId;
    public String accountName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TAccount tAccount = (TAccount) o;
        return id == tAccount.id &&
                accountId == tAccount.accountId &&
                Objects.equals(accountName, tAccount.accountName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, accountName);
    }
}

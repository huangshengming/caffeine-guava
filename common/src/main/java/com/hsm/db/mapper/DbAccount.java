package com.hsm.db.mapper;

import com.hsm.db.bean.TAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DbAccount {

    @Select("select * from t_account where `accountId`=#{accountId}")
    TAccount selectAccountByAccountId(int accountId);

    @Update("update t_account set `accountName`=#{bean.accountName} where `accountId`=#{bean.accountId}")
    int updateAccountNameByAccountId(@Param("bean") TAccount account);

}

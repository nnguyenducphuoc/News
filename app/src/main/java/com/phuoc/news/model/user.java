package com.phuoc.news.model;

import java.util.List;

public class user {
    private List<account> accountList;

    public user(List<account> accountList) {
        this.accountList = accountList;
    }

    public List<account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<account> accountList) {
        this.accountList = accountList;
    }
}

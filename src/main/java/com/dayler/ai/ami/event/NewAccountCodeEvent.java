package com.dayler.ai.ami.event;

import java.util.Map;

public class NewAccountCodeEvent extends AmiEvent {
    public static final String RESPONSE_NAME = "NewAccountCode";

    private String accountCode;
    private String oldAccountCode;

    public NewAccountCodeEvent() {
        setName(RESPONSE_NAME);
    }

    @Override
    public void build(Map<String, String> metadata, Object ctx) {
        super.build(metadata);
        setAccountCode(metadata.get(AmiResponseField.AccountCode.name()));
        setOldAccountCode(metadata.get(AmiResponseField.OldAccountCode.name()));
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getOldAccountCode() {
        return oldAccountCode;
    }

    public void setOldAccountCode(String oldAccountCode) {
        this.oldAccountCode = oldAccountCode;
    }

    @Override
    public String toString() {
        return toString(String.format("accountCode=%s, oldAccountCode=%s", accountCode, oldAccountCode));
    }
}
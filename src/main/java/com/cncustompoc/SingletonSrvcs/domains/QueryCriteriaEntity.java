package com.cncustompoc.SingletonSrvcs.domains;

public class QueryCriteriaEntity {
    public String getColName() {
        return ColName;
    }

    public void setColName(String colName) {
        ColName = colName;
    }

    public String getColValue() {
        return ColValue;
    }

    public void setColValue(String colValue) {
        ColValue = colValue;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    private String ColName;
    private String ColValue;
    private String Operator;

}

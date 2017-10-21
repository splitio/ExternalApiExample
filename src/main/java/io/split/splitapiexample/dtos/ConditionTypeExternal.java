package io.split.splitapiexample.dtos;

public enum ConditionTypeExternal {
    //Keys
    IN_SEGMENT,

    //Dep Matcher
    IN_SPLIT,

    // Custom Attributes
    BOOLEAN,

    ON_DATE,
    ON_OR_AFTER_DATE,
    ON_OR_BEFORE_DATE,
    BETWEEN_DATE,

    EQUAL_SET,
    ANY_OF_SET,
    ALL_OF_SET,
    PART_OF_SET,
    EQUAL_NUMBER,
    LESS_THAN_OR_EQUAL_NUMBER,
    GREATER_THAN_OR_EQUAL_NUMBER,
    BETWEEN_NUMBER,

    //Keys and Custom Attributes
    IN_LIST_STRING,
    STARTS_WITH_STRING,
    ENDS_WITH_STRING,
    CONTAINS_STRING,
    MATCHES_STRING,
    ;
}

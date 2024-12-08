package br.com.prontorecife.auth.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LoginFlowEnum {
    CRM,
    CPF;
    //CNPJ;

    @JsonValue
    @Override
    public String toString() {
        return name();
    }

    @JsonCreator
    public static LoginFlowEnum fromValue(String value) {
        return LoginFlowEnum.valueOf(value.toUpperCase());
    }
}
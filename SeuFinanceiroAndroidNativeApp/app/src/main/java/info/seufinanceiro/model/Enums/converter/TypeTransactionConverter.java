package info.seufinanceiro.model.enums.converter;

import info.seufinanceiro.model.enums.TypeTransaction;

public class TypeTransactionConverter {

    public static TypeTransaction convert(String type){
        return type == TypeTransaction.INPUT.getDescription() ? TypeTransaction.INPUT : TypeTransaction.OUTPUT;
    }

}

package ro.cburcea.playground.spring.batch.retry;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class RecordFieldSetMapper implements FieldSetMapper<Transaction> {

    @Override
    public Transaction mapFieldSet(FieldSet fieldSet) {
        Transaction transaction = new Transaction();

        transaction.setId(fieldSet.readInt(0));
        transaction.setUsername(fieldSet.readString("username"));
        transaction.setUserId(fieldSet.readInt(2));
        transaction.setTransactionDate(fieldSet.readString(3));
        transaction.setAmount(fieldSet.readDouble(4));
        return transaction;
    }
}
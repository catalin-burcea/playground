package ro.cburcea.playground.core.serialization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class SerializationUtility {

    public static void main(String[] args) throws IOException {
        Product product = new Product();
        product.setName("Lenovo");
        product.setDescription("Lenovo description");

        String serializedObj = serializeObjectToString(product);

        System.out.println("Serialized Product object to string:");
        System.out.println(serializedObj);
    }

    public static String serializeObjectToString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();

        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
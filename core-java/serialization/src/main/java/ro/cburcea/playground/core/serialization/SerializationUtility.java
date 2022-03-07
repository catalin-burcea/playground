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

        ProductDefault productDefault = new ProductDefault();
        productDefault.setName("Lenovo");
        productDefault.setDescription("Lenovo description");

        ProductTransient productTransient = new ProductTransient();
        productTransient.setName("Lenovo");
        productTransient.setDescription("Lenovo description");
        ProductTransient.commonDescription = "Lenovo common description";

        String serializedObj = serializeObjectToString(product);
        String serializedObj2 = serializeObjectToString(productDefault);
        String serializedObj3 = serializeObjectToString(productTransient);

        System.out.println("Serialized Product objects to string:");
        System.out.println(serializedObj);
        System.out.println(serializedObj2);
        System.out.println(serializedObj3);
    }

    public static String serializeObjectToString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();

        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
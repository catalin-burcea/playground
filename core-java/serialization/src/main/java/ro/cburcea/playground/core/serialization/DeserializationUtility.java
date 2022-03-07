package ro.cburcea.playground.core.serialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class DeserializationUtility {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String serializedObj = "rO0ABXNyADByby5jYnVyY2VhLnBsYXlncm91bmQuY29yZS5zZXJpYWxpemF0aW9uLlByb2R1Y3QAAAAAABLWhwIAAkwAC2Rlc2NyaXB0aW9udAASTGphdmEvbGFuZy9TdHJpbmc7TAAEbmFtZXEAfgABeHB0ABJMZW5vdm8gZGVzY3JpcHRpb250AAZMZW5vdm8=";
        String serializedObjDefaultUID = "rO0ABXNyADdyby5jYnVyY2VhLnBsYXlncm91bmQuY29yZS5zZXJpYWxpemF0aW9uLlByb2R1Y3REZWZhdWx0XPYGdrIcpSQCAAJMAAtkZXNjcmlwdGlvbnQAEkxqYXZhL2xhbmcvU3RyaW5nO0wABG5hbWVxAH4AAXhwdAASTGVub3ZvIGRlc2NyaXB0aW9udAAGTGVub3Zv";
        System.out.println("Deserializing Product...");

        /* compatible changes:
            deleting a field
            adding a new field
            renaming a new field
            changing visibility modifiers
        */
        /* incompatible changes:
            changing the type of a field: ClassCastException
            changing serialVersionUID: InvalidClassException
        */
        Product deserializedObj = (Product) deSerializeObjectFromString(serializedObj);


        /* incompatible changes:
            deleting a field: InvalidClassException
            adding a new field: InvalidClassException
            renaming a new field: InvalidClassException
            changing visibility modifiers: InvalidClassException
            changing the type of a field: InvalidClassException
        */
        ProductDefault deserializedObjDefaultUID = (ProductDefault) deSerializeObjectFromString(serializedObjDefaultUID);

        System.out.println("Product.name: " + deserializedObj.getName());
        System.out.println("Product.description: " + deserializedObj.getDescription());
        System.out.println("Product.price: " + deserializedObj.getPrice());

        System.out.println("ProductDefault.name: " + deserializedObjDefaultUID.getName());
        System.out.println("ProductDefault.description: " + deserializedObjDefaultUID.getDescription());
    }

    public static Object deSerializeObjectFromString(String s) throws IOException, ClassNotFoundException {

        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }
}
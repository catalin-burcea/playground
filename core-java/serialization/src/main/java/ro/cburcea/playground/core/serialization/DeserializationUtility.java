package ro.cburcea.playground.core.serialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class DeserializationUtility {

    /*
    The serialization runtime associates with each serializable class a version number, called a serialVersionUID,
     which is used during deserialization to verify that the sender and receiver of a serialized object have loaded classes
     for that object that are compatible with respect to serialization.
      If the receiver has loaded a class for the object that has a different serialVersionUID than that of the corresponding
      sender's class, then deserialization will result in an InvalidClassException. A serializable class can declare
       its own serialVersionUID explicitly by declaring a field named serialVersionUID that must be static, final, and of type long.

    If a serializable class does not explicitly declare a serialVersionUID,
     then the serialization runtime will calculate a default serialVersionUID value for that class based on various aspects of the class,
      as described in the Java(TM) Object Serialization Specification. However, it is strongly recommended that all serializable classes
       explicitly declare serialVersionUID values, since the default serialVersionUID computation is highly sensitive to class details
        that may vary depending on compiler implementations, and can thus result in unexpected InvalidClassExceptions during deserialization.
         Therefore, to guarantee a consistent serialVersionUID value across different java compiler implementations,
          a serializable class must declare an explicit serialVersionUID value. It is also strongly advised that explicit
           serialVersionUID declarations use the private modifier where possible, since such declarations apply only to the immediately
            declaring class — serialVersionUID fields are not useful as inherited members.
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String serializedObj = "rO0ABXNyADByby5jYnVyY2VhLnBsYXlncm91bmQuY29yZS5zZXJpYWxpemF0aW9uLlByb2R1Y3QAAAAAABLWhwIAAkwAC2Rlc2NyaXB0aW9udAASTGphdmEvbGFuZy9TdHJpbmc7TAAEbmFtZXEAfgABeHB0ABJMZW5vdm8gZGVzY3JpcHRpb250AAZMZW5vdm8=";
        String serializedObjDefaultUID = "rO0ABXNyADdyby5jYnVyY2VhLnBsYXlncm91bmQuY29yZS5zZXJpYWxpemF0aW9uLlByb2R1Y3REZWZhdWx0XPYGdrIcpSQCAAJMAAtkZXNjcmlwdGlvbnQAEkxqYXZhL2xhbmcvU3RyaW5nO0wABG5hbWVxAH4AAXhwdAASTGVub3ZvIGRlc2NyaXB0aW9udAAGTGVub3Zv";
        String serializedObjTransient = "rO0ABXNyADlyby5jYnVyY2VhLnBsYXlncm91bmQuY29yZS5zZXJpYWxpemF0aW9uLlByb2R1Y3RUcmFuc2llbnQAAAAAABLWhwIAAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZzt4cHQABkxlbm92bw==";
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

        /*
            transient and static fields are not serialized
         */
        ProductTransient.commonDescription = "updated common desc";
        ProductTransient deserializedObjTransient = (ProductTransient) deSerializeObjectFromString(serializedObjTransient);

        System.out.println("Product.name: " + deserializedObj.getName());
        System.out.println("Product.description: " + deserializedObj.getDescription());
        System.out.println("Product.price: " + deserializedObj.getPrice());
        System.out.println();

        System.out.println("ProductDefault.name: " + deserializedObjDefaultUID.getName());
        System.out.println("ProductDefault.description: " + deserializedObjDefaultUID.getDescription());
        System.out.println();

        System.out.println("ProductTransient.name: " + deserializedObjTransient.getName());
        System.out.println("ProductTransient.description: " + deserializedObjTransient.getDescription());
        System.out.println("ProductTransient.commonDescription: " + ProductTransient.commonDescription);
        System.out.println();
    }

    public static Object deSerializeObjectFromString(String s) throws IOException, ClassNotFoundException {

        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }
}
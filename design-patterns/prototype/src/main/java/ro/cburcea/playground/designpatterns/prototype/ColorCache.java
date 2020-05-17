package ro.cburcea.playground.designpatterns.prototype;

import java.util.HashMap;
import java.util.Map;

public class ColorCache {

    private static Map<String, Color> colorMap = new HashMap<>();

    static {
        // load cache
        colorMap.put("blue", new BlueColorPrototype());
        colorMap.put("black", new BlackColorPrototype());
    }

    public static Color getColor(String colorName) {
        return (Color) colorMap.get(colorName).clone();
    }
} 
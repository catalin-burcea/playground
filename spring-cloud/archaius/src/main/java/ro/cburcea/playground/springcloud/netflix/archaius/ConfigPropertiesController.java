package ro.cburcea.playground.springcloud.netflix.archaius;

import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.config.validation.ValidationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ConfigPropertiesController {

    private static final String NOT_FOUND = "not found!";

    private DynamicStringProperty propertyOneWithDynamic = DynamicPropertyFactory.getInstance()
            .getStringProperty("archaius.properties.one", NOT_FOUND);

    private DynamicStringProperty propertyTwoWithDynamic = DynamicPropertyFactory.getInstance()
            .getStringProperty("archaius.properties.two", NOT_FOUND);

    private DynamicStringProperty propertyThreeWithDynamic = DynamicPropertyFactory.getInstance()
            .getStringProperty("archaius.properties.three", NOT_FOUND);

    private DynamicIntProperty positiveIntProp = new DynamicIntProperty("positiveIntProp", 0) {
        @Override
        public void validate(String newValue) {
            if (Integer.parseInt(newValue) < 0) {
                throw new ValidationException("Cannot be negative");
            }
        }
    };

    @GetMapping("/archaius")
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(propertyOneWithDynamic.getName(), propertyOneWithDynamic.get());
        properties.put(propertyTwoWithDynamic.getName(), propertyTwoWithDynamic.get());
        properties.put(propertyThreeWithDynamic.getName(), propertyThreeWithDynamic.get());
        properties.put(positiveIntProp.getName(), positiveIntProp.get());

        return properties;
    }
}
package ro.cburcea.playground.springboot.actuator.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.GitInfoContributor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.info.GitProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomGitInfoContributor extends GitInfoContributor {

    @Autowired
    public CustomGitInfoContributor(GitProperties properties) {
        super(properties);
    }

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> map = generateContent();
        map.put("dirty", getProperties().get("dirty"));
        builder.withDetail("git", map);
    }
}
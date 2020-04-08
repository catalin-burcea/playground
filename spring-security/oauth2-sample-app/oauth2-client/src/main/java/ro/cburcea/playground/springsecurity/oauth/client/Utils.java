package ro.cburcea.playground.springsecurity.oauth.client;

public final class Utils {

    private Utils() {}

    static final String RESOURCE_SERVER_URL = "http://localhost:8081";
    static final String REDIRECT_URL = "http://localhost:8082/oauth2/redirect";
    static final String OAUTH2_TOKEN_URL = "http://localhost:8080/oauth/token";
    static final String OAUTH2_AUTHORIZE_URL = "http://localhost:8080/oauth/authorize?grant_type=authorization_code&response_type=code&client_id=web&state=1234";

}

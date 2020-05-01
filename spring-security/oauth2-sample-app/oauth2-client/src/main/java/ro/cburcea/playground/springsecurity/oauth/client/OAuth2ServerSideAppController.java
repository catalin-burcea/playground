package ro.cburcea.playground.springsecurity.oauth.client;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ro.cburcea.playground.springsecurity.oauth.client.model.Student;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static ro.cburcea.playground.springsecurity.oauth.client.Utils.*;

@Controller
public class OAuth2ServerSideAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2ServerSideAppController.class);

    private static final String ACCESS_TOKEN_COOKIE = "access_token_cookie";
    private static final String REFRESH_TOKEN_COOKIE = "refresh_token_cookie";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String BASIC_AUTH_CLIENT_CREDENTIALS = Base64.getEncoder().encodeToString("web:secret".getBytes());
    private RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * When a user logs out, their token is not immediately removed from the token store, instead it remains valid until it expires on its own.
     * And so, revocation of a token will mean removing that token from the token store.
     */
    @PostMapping("logout")
    public String logout(@CookieValue(ACCESS_TOKEN_COOKIE) String accessToken, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(BASIC_AUTH_CLIENT_CREDENTIALS);
        HttpEntity<?> request = new HttpEntity<>(headers);

        restTemplate.exchange(OAUTH2_TOKEN_URL + "?accessToken=" + accessToken, HttpMethod.DELETE, request, Boolean.class);
        setTokenCookie(response, null, "/", ACCESS_TOKEN_COOKIE);
        setTokenCookie(response, null, "/", REFRESH_TOKEN_COOKIE);

        return "redirect:/home";
    }


    @GetMapping("/oauth2/redirect")
    public String redirect(@RequestParam(required = false) String code, @RequestParam String state, @RequestParam(required = false) String error, HttpServletResponse response) throws IOException {
        if (error != null) {
            return "redirect:/home";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(BASIC_AUTH_CLIENT_CREDENTIALS);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", REDIRECT_URL);
        map.add("client_id", "web");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> accessTokeResponse = restTemplate.postForEntity(OAUTH2_TOKEN_URL, request, String.class);
        JsonNode accessTokenData = objectMapper.readTree(accessTokeResponse.getBody());

        final String accessToken = accessTokenData.get(ACCESS_TOKEN).textValue();
        final String refreshToken = accessTokenData.get(REFRESH_TOKEN).textValue();
        setTokenCookie(response, accessToken, "/", ACCESS_TOKEN_COOKIE);
        setTokenCookie(response, refreshToken, "/", REFRESH_TOKEN_COOKIE);

        return "redirect:/home";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@Valid @ModelAttribute Student student,
                              BindingResult errors,
                              Model model,
                              @CookieValue(value = ACCESS_TOKEN_COOKIE, required = false) String token) {
        LOGGER.info("access_token: {}", token);
        if (!errors.hasErrors()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);
            HttpEntity<Student> request = new HttpEntity<>(student, headers);
            try {
                restTemplate.postForObject(RESOURCE_SERVER_URL + "/students", request, Student.class);
            } catch (RestClientException ex) {
                return "redirect:/accessdenied";
            }
            return "redirect:/listStudents";
        }
        setUserDetails(model, token);
        return "addStudent";
    }

    @GetMapping("/addStudent")
    public String addStudent(Model model, @CookieValue(value = ACCESS_TOKEN_COOKIE, required = false) String token) {
        model.addAttribute("student", new Student());

        try {
            setUserDetails(model, token);
        } catch (Exception ex) {
            return "redirect:/accessdenied";
        }

        return "addStudent.html";
    }

    private void setUserDetails(Model model, String token) {
        DecodedJWT jwt = JWT.decode(token);
        String username = jwt.getClaim("user_name").asString();
        List<String> scopes = jwt.getClaim("scope").asList(String.class);
        model.addAttribute("username", username);
        model.addAttribute("scopes", scopes);

        LOGGER.info("username: {}", username);
        LOGGER.info("scopes: {}", scopes);
    }

    @GetMapping("/listStudents")
    public String listStudent(Model model, @CookieValue(value = ACCESS_TOKEN_COOKIE, required = false) String token) {
        LOGGER.info("access_token: {}", token);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity entity = new HttpEntity(headers);

        try {
            ResponseEntity<List> students = restTemplate.exchange(RESOURCE_SERVER_URL + "/students", HttpMethod.GET, entity, List.class);
            model.addAttribute("students", students.getBody());
        } catch (RestClientException ex) {
            return "redirect:/accessdenied";
        }

        setUserDetails(model, token);

        return "listStudents.html";
    }

    private void setTokenCookie(HttpServletResponse response, String token, String path, String name) {
        Cookie cookie = new Cookie(name, token);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

}
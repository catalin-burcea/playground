package ro.cburcea.playground.springsecurity.oauth.client;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
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

    private static final String ACCESS_TOKEN_COOKIE = "access_token";
    private RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("logout")
    public String logout(HttpServletResponse response) {
        setAccessTokenCookie(response, null);

        return "redirect:/home";
    }


    @GetMapping("/oauth2/redirect")
    public String redirect(@RequestParam(required = false) String code, @RequestParam String state, @RequestParam(required = false) String error, HttpServletResponse response) throws IOException {
        if(error != null) {
            return "redirect:/home";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(Base64.getEncoder().encodeToString("web:secret".getBytes()));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", REDIRECT_URL);
        map.add("client_id", "web");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> accessTokeResponse = restTemplate.postForEntity(OAUTH2_TOKEN_URL, request, String.class);
        JsonNode accessTokenData = objectMapper.readTree(accessTokeResponse.getBody());

        final String accessToken = accessTokenData.get(ACCESS_TOKEN_COOKIE).textValue();
        setAccessTokenCookie(response, accessToken);

        return "redirect:/home";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@Valid @ModelAttribute Student student,
                              BindingResult errors,
                              Model model,
                              @CookieValue(ACCESS_TOKEN_COOKIE) String token) {
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
    public String addStudent(Model model, @CookieValue(ACCESS_TOKEN_COOKIE) String token) {
        model.addAttribute("student", new Student());

        try {
            setUserDetails(model, token);
        } catch (JWTDecodeException ex) {
            return "redirect:/accessdenied";
        }

        return "addStudent.html";
    }

    private void setUserDetails(Model model, @CookieValue(ACCESS_TOKEN_COOKIE) String token) {
        DecodedJWT jwt = JWT.decode(token);
        String username = jwt.getClaim("sub").asString();
        List<String> scopes = jwt.getClaim("scope").asList(String.class);
        model.addAttribute("username", username);
        model.addAttribute("scopes", scopes);

        LOGGER.info("username: {}", username);
        LOGGER.info("scopes: {}", scopes);
    }

    @GetMapping("/listStudents")
    public String listStudent(Model model, @CookieValue(ACCESS_TOKEN_COOKIE) String token) {
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

    private void setAccessTokenCookie(HttpServletResponse response, String value) {
        Cookie cookie = new Cookie(ACCESS_TOKEN_COOKIE, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
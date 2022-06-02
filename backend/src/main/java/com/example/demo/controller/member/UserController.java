package com.example.demo.controller.member;

import com.example.demo.entity.member.Role;
import com.example.demo.entity.member.Code;
import com.example.demo.entity.member.User;
import com.example.demo.service.member.CustomOAuth2UserService;
import com.example.demo.service.member.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;

    @GetMapping
    public String index(Authentication authentication){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("**********" +oAuth2User.getAttributes());

        return "세션정보확인";
    }

    @GetMapping("/listall")
    public List<User> login(){
        return userService.listAll();
    }

    @PostMapping("/register")
    public void register(){
        User user = new User("admin", "admin@gmail.com", "password");
        Role role = new Role("ADMIN");
        userService.addUser(user);

        userService.addRoleToUser(user, role);
    }

    @GetMapping("/kakaoLogin")
    public String kakaoLogin(String code){

        RestTemplate restTemplate = new RestTemplate();

        Code authorizeCode  = new Code(code);

        String result = restTemplate.postForObject(
                "http://localhost:5000/kakao-login",
                authorizeCode,
                String.class
        );

        log.info("result = " + result);

        return result;
    }

    /*private static final String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
    private final ClientRegistrationRepository clientRegistrationRepository;
    // Lombok 아닌 경우 (@RequiredArgsConstructor 없는 경우)
    // @Autowired private ClientRegistrationRepository clientRegistrationRepository;
    @SuppressWarnings("unchecked")
    @GetMapping("/login")
    public String getLoginPage(Model model) throws Exception {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        assert clientRegistrations != null;
        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);

        return "auth/oauth-login";
    }*/



}
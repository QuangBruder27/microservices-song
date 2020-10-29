package htwb.ai.nguyenkbe.authservice.Controller;


import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import htwb.ai.nguyenkbe.authservice.Entity.User;
import htwb.ai.nguyenkbe.authservice.Handler.IUserHandler;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping(value="/auth")
public class UserController {

    private IUserHandler iUserHandler;

    public UserController(IUserHandler handler) {
        this.iUserHandler = handler;
    }

    @GetMapping("/checktoken/{token}")
    public String checkToken(@PathVariable("token") String token) {
        System.out.println("GET checkToken by Auth Service");

        keyStore.put("default token","nguyen");

        System.out.println(keyStore.toString());
        if (keyStore.containsKey(token)){
            return keyStore.get(token);
        } else {
            return "invaild";
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity getToken(@RequestBody User payloadUser) {
        if (null == payloadUser) {
            return ResponseEntity.badRequest().body("Wrong song format");
        }
        User dbUser = iUserHandler.getUser(payloadUser.getUserId());
        if (null == dbUser || !dbUser.getPassword().equals(payloadUser.getPassword()) ) {
            return ResponseEntity.status(401).body("User cannot be authenticated");
        }   else {
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(generateAuthToken(payloadUser));
        }
    }

    public static BiMap<String, String> keyStore = HashBiMap.create();
    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateAuthToken(User user) {
        if(!keyStore.containsKey(user)) {
            keyStore.remove(user);
        }
        String jws = Jwts.builder()
                .setSubject(user.getUserId())
                //.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(10).toInstant()))
                .signWith(key).compact();
        keyStore.put(jws, user.getUserId());
        return  jws;
    }



}

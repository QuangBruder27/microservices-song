package htwb.ai.nguyenkbe.lyricservice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import htwb.ai.nguyenkbe.lyricservice.Entity.Lyric;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	//,properties = {"httpbin=http://localhost:8200"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LyricControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void getAll() {
        Lyric[] body = restTemplate.getForObject("/lyric", Lyric[].class);
        List<Lyric> lyrics = Arrays.asList(body);
        System.out.println("Number of Lyric: "+lyrics.size());
        assertTrue(lyrics.size()>0);
    }

    @Test
    @Order(2)
    void getLyricbyId() {
        Lyric body = restTemplate.getForObject("/lyric/1", Lyric.class);
        assertTrue(body.getTitle().equals("MacArthur Park"));
    }


    @Test
    @Order(3)
    void postLyric409(){
        Lyric lyric = initLyric();
        lyric.setSongId(2);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity =
                new HttpEntity<String>(asJsonString(lyric), headers);
        ResponseEntity<String> res =
                restTemplate.postForEntity("/lyric/", entity, String.class);
        assertTrue(res.getStatusCodeValue()== 409);
    }

    @Test
    @Order(4)
    void postLyricWrongFormat(){
        Lyric lyric = initLyric();
        lyric.setSongId(0);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity =
                new HttpEntity<String>(asJsonString(lyric), headers);
        ResponseEntity<String> res =
                restTemplate.postForEntity("/lyric/", entity, String.class);
        assertTrue(res.getBody().equals("Wrong lyric format"));
    }


    @Test
    @Order(5)
    void postLyricSucces(){
        Lyric lyric = initLyric();
        lyric.setSongId(11);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity =
                new HttpEntity<String>(asJsonString(lyric), headers);
        ResponseEntity<String> res =
                restTemplate.postForEntity("/lyric/", entity, String.class);
        assertTrue(res.getStatusCodeValue()==201);
    }


    @Test
    @Order(6)
    void putLyricSuccess() {
        Lyric lyric = initLyric();
        lyric.setSongId(11);
        HttpEntity<Lyric> entity = new HttpEntity<Lyric>(lyric);
        ResponseEntity responseEntity = restTemplate
                .exchange("/lyric/11", HttpMethod.PUT, entity, String.class);
        System.out.println(responseEntity);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Order(7)
    void putLyricwithMismatchId() {
        HttpEntity<Lyric> entity = new HttpEntity<Lyric>(initLyric());
        ResponseEntity responseEntity = restTemplate
                .exchange("/lyric/15", HttpMethod.PUT, entity, String.class);
        System.out.println(responseEntity);
        assertTrue(responseEntity.getBody().equals("ID mismatch"));
    }

    @Test
    @Order(8)
    void putLyric404() {
        Lyric lyric = initLyric();
        lyric.setSongId(20);
        HttpEntity<Lyric> entity = new HttpEntity<Lyric>(lyric);
        ResponseEntity responseEntity = restTemplate
                .exchange("/lyric/20", HttpMethod.PUT, entity, Lyric.class);
        System.out.println(responseEntity);
        assertTrue(responseEntity.getStatusCodeValue() == 404);
    }

    @Order(9)
    @Test
    void deleteLyric() {
        restTemplate.delete("/lyric/11");
    }




    Lyric initLyric(){
        return new Lyric(6,"Title for Lyric","Content for Lyric");
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
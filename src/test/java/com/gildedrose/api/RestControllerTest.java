package com.gildedrose.api;

import com.gildedrose.model.Item;
import com.gildedrose.JavaApplication;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JavaApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestControllerTest {

    @Value("${local.server.port}")
    int port;

    private static final String API_ROOT = "/items";

    private void createItemFromUri() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createItem())
                .post(API_ROOT);
    }

    private Map<String, Object> createItem() {
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("name", "test");
        item.put("quality", 10);
        item.put("sellIn", 10);

        return item;
    }

    private Map<String, Object> createWrongItem() {
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("name", "test");
        item.put("quality", -1);
        item.put("sellIn", 10);

        return item;
    }

    @Test
    public void whenGetAllItems_thenOK() {
        Response response = RestAssured.get(API_ROOT);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class).size() > 0);
    }

    @Test
    public void whenPostItems_thenCREATED() {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createItem())
                .post(API_ROOT);

        System.out.println(response.jsonPath().get("name").toString());

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertEquals("test", response.jsonPath().get("name"));
    }

    @Test
    public void whenPostWrongParamItems_thenBADREQUEST() {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createWrongItem())
                .post(API_ROOT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenPatchItems_thenOK() {
        createItemFromUri();

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createItem())
                .patch(API_ROOT + "/quality");

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue((Integer) response.jsonPath().get("updated") > 0);
    }

    @Test
    public void whenPatchItems_WithWrongParams_thenBASREQUEST() {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createWrongItem())
                .patch(API_ROOT + "/quality");

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

}
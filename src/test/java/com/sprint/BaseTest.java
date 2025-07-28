package com.sprint;

import org.junit.Before;

import io.restassured.RestAssured;

public abstract class BaseTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = ListOfConstants.URL;
    }
}

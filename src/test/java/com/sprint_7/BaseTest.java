package com.sprint_7;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import io.restassured.RestAssured;

public abstract class BaseTest {
        public List<CourierCreate> deleteCourierList = new ArrayList<>();

    @Before
    public void setUp() {
        RestAssured.baseURI = ListOfConstants.URL;
    }
}

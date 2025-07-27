package com.sprint;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;

import com.google.gson.Gson;

import io.qameta.allure.Attachment;

public abstract class CourierBaseTest extends BaseTest{
    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    CourierCreateApi api;
    
    //Список курьеров на удаление
    public List<CourierCreateModel> deleteCourierList = new ArrayList<>();

    @Attachment(value = "Данные курьера: {caseInfo}", type="text/*")
    public String courierDataAssert(CourierCreateModel model, String caseInfo){
        Gson gson = new Gson();
        return gson.toJson(model);
    }

    @Before
    public void initApi(){
        api = new CourierCreateApi();
    }
        
    @After
    public void deleteCouriers() {        

        if (deleteCourierList != null && !deleteCourierList.isEmpty())
        for (CourierCreateModel courier : deleteCourierList) {            
            api.deleteCourier(courier);
        }
        deleteCourierList.clear();
    }

}

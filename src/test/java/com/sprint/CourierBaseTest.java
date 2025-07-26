package com.sprint;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;

public abstract class CourierBaseTest extends BaseTest{
    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();
    
    public List<CourierCreateModel> deleteCourierList = new ArrayList<>();
        
    @After
    public void deleteDate() {
        CourierCreateApi apiCourier = new CourierCreateApi();

        if (deleteCourierList != null && !deleteCourierList.isEmpty())
        for (CourierCreateModel courier : deleteCourierList) {            
            apiCourier.setModel(courier);
            apiCourier.deleteCourier();
        }
        deleteCourierList.clear();
    }

}

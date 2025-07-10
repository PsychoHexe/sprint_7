package com.sprint_7;

import org.junit.After;

public abstract class CourierBaseTest extends BaseTest{

    @After
    public void deleteDate() {

        if (deleteCourierList != null && !deleteCourierList.isEmpty())
        for (CourierCreate courier : deleteCourierList) {
            courier.deleteCourier();
        }
        deleteCourierList.clear();
    }

}

package com.sprint_7.data;

import java.util.Arrays;
import java.util.List;

import com.sprint_7.CourierCreate;

public class CourierData {

        public static final List<CourierCreate> NEGATIVE_CREATE_LIST = Arrays.asList(
                        new CourierCreate("", "1113", "Margo"),
                        new CourierCreate("Wasd", "", "Margo"));

        public static final List<CourierCreate> NEGATIVE_LOGIN_LIST = Arrays.asList(
                        new CourierCreate("", "1113", ""),
                        new CourierCreate("Wasd", "", ""),
                        new CourierCreate("Wasda", "1113", ""),
                        new CourierCreate("Wasd", "1112", ""));
                        
        public static final List<CourierCreate> POSITIV_CREATE_LIST = Arrays.asList(
                        new CourierCreate("Wasd", "1113", "Margo"),
                        new CourierCreate("Wasd", "1113", "MargoN"));
}

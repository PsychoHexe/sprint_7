package com.sprint.data;

import java.util.Arrays;
import java.util.List;

import com.sprint.CourierCreateModel;

public class CourierData {

        public static final List<CourierCreateModel> NEGATIVE_CREATE_LIST = Arrays.asList(
                        new CourierCreateModel("", "1113", "Margo"),
                        new CourierCreateModel("Wasd", "", "Margo"));

        public static final List<CourierCreateModel> NEGATIVE_LOGIN_LIST = Arrays.asList(
                        new CourierCreateModel("", "1113", ""),
                        new CourierCreateModel("Wasd", "", ""),
                        new CourierCreateModel("Wasdaasd", "1113", ""),
                        new CourierCreateModel("Wasd", "1112", ""));

        public static final List<CourierCreateModel> POSITIV_CREATE_LIST = Arrays.asList(
                        new CourierCreateModel("Wasd", "1113", "Margo"),
                        new CourierCreateModel("Wasd", "1113", "MargoN"));
}

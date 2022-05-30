package com.demo.BaristaBucks.Util;

public interface SuccessMessages {

    interface User {
        String USER_ADDED_SUCCESSFULLY = "User Added Successfully";
        String USER_LOGGED_IN = "User logged in successfully";
        String LOGOUT_SUCCESS = "You logged out successfully.";

    }

    interface Coffee{
        String COFFEE_ADDED_SUCCESSFULLY = "Coffee Added Successfully";
        String COFFEE_FEATURED_SUCCESSFULLY = "Coffee featured Successfully";
        String COFFEE_UN_FEATURED_SUCCESSFULLY = "Coffee unfeatured Successfully";
        String COFFEE_LIST_FETCHED_SUCCESSFULLY = "Coffee list fetched Successfully";
        String FEATURED_COFFEE_LIST_FETCHED_SUCCESSFULLY = "Featured Coffee list fetched Successfully";
    }

    interface Cart{
        String ITEM_ADDED_SUCCESSFULLY = "Item Added Successfully";
        String ITEM_REMOVED_SUCCESSFULLY = "Item Removed Successfully";
    }

    interface Coupons {
        String COUPON_ADDED_SUCCESSFULLY = "Coupon Added Successfully";
        String COUPON_APPLIED_SUCCESSFULLY = "Coupon Applied Successfully";
        String COUPON_FETCHED_SUCCESSFULLY = "Coupon Fetched Successfully";
    }

    interface Order {
        String ORDER_CREATED_SUCCESSFULLY = "Order Created Successfully";
    }

    interface Rating{
        String RATING_ADDED_SUCCESSFULLY = "Rating Added Successfully";
    }
}

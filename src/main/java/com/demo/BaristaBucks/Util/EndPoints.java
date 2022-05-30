package com.demo.BaristaBucks.Util;

public class EndPoints {

    public interface User {
        String USER = "/user";
        String ADD_USER = USER + "/addUser";
        String LOGOUT = USER + "/logout";
        String LOGIN = USER + "/login";
        String REFRESH_TOKEN = USER + "/refreshToken";
    }

    public interface Role{
        String ROLE = "/role";
        String ADD_ROLE = ROLE + "/addRole";
    }

    public interface Coffee{
        String COFFEE = "/coffee";
        String ADD_UPDATE_COFFEE = COFFEE + "/addUpdateCoffee";
        String FEATURE_UN_FEATURE_COFFEE = COFFEE + "/featureUnFeatureCoffee";
        String GET_ALL_COFFEE_LIST = COFFEE + "/getListOfCoffee";
        String GET_LIST_OF_FEATURED_COFFEE = COFFEE + "/getListOfFeaturedCoffee";
        String GET_LIST_OF_COFFEE_BY_PAST_ORDER = COFFEE + "/getListOfCoffeeByPastOrder/{userId}";
    }

    public interface Cart{
        String CART = "/cart";
        String ADD_TO_CART = CART + "/addToCart";
        String ADD_OR_REMOVE_FROM_CART = CART + "/addOrRemoveFromCart";
    }

    public interface Coupon{
        String COUPON = "/coupon";
        String ADD_COUPON = COUPON + "/addCoupon";
        String APPLY_COUPON = COUPON + "/applyCoupon";
        String GET_ALL_COUPONS = COUPON + "/getAllCouponsByUserId/{userId}";
    }

    public interface Order{
        String ORDER = "/order";
        String CREATE_ORDER = ORDER + "/createOrder";
    }

    public interface Rating{
        String RATING = "/rating";
        String ADD_RATING = RATING + "/addRating";
    }
}

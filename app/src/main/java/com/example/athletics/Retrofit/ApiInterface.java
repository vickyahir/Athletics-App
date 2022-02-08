package com.example.athletics.Retrofit;


import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

//    @FormUrlEncoded
//    @POST("auth/login")
//    Call<SignInResponse> GetLogin(@Field("email") String email,
//                                  @Field("password") String password,
//                                  @Field("otp") String otp,
//                                  @Field("lat") String lat,
//                                  @Field("lng") String lng
//    );
//
//    @FormUrlEncoded
//    @POST("otp-for-login")
//    Call<DefaultApiResponse> RequestOTPApi(@Field("phone") String phone
//    );
//
//
//    @FormUrlEncoded
//    @POST("auth/signup")
//    Call<SignUpResponse> GetSignup(@Field("email_or_phone") String email_or_phone,
//                                   @Field("register_by") String register_by);
//
//    @FormUrlEncoded
//    @POST("profile/update")
//    Call<ProfileUpdateResponse> GetProfileUpdate(@Field("id") String id,
//                                                 @Field("name") String name,
//                                                 @Field("email") String email,
//                                                 @Field("password") String password);
//
//
//    @FormUrlEncoded
//    @POST("profile/update")
//    Call<DefaultApiResponse> ProfileUpdatewithDob(@Field("id") String id,
//                                                  @Field("name") String name,
//                                                  @Field("email") String email,
//                                                  @Field("dob") String dob);
//
//    @FormUrlEncoded
//    @POST("profile/password-update")
//    Call<DefaultApiResponse> ChangePasswordApi(@Field("password") String password,
//                                               @Field("new_password") String new_password);
//
//
//    @FormUrlEncoded
//    @POST("profile/update-image")
//    Call<ProfileImageResponse> ProfileImageUpdate(@Field("id") String id,
//                                                  @Field("filename") String filename,
//                                                  @Field("image") String image);
//
//
//    @FormUrlEncoded
//    @POST("auth/confirm_code")
//    Call<OTPVerifyResponse> GetConfirmOTP(@Field("user_id") String user_id,
//                                          @Field("verification_code") String verification_code,
//                                          @Field("lat") String lat,
//                                          @Field("lng") String lng
//    );
//
//    @FormUrlEncoded
//    @POST("auth/resend_code")
//    Call<ResendCodeResponse> GetResendRegistrationOTP(@Field("user_id") String user_id,
//                                                      @Field("register_by") String register_by);
//
//
//    @FormUrlEncoded
//    @POST("auth/password/forget_request")
//    Call<DefaultApiResponse> GetForgotPassword(@Field("email_or_phone") String email_or_phone,
//                                               @Field("send_code_by") String send_code_by);
//
//    @FormUrlEncoded
//    @POST("auth/password/resend_code")
//    Call<ResendCodeResponse> GetForgotResendOTP(@Field("email_or_phone") String email_or_phone,
//                                                @Field("verify_by") String verify_by);
//
//
//    @FormUrlEncoded
//    @POST("auth/password/confirm_reset")
//    Call<DefaultApiResponse> ForgotResetPasswordApi(@Field("verification_code") String verification_code,
//                                                    @Field("password") String password);
//
//
//    @FormUrlEncoded
//    @POST("auth/password/confirm_reset")
//    Call<DefaultApiResponse> ForgotConfirmApi(@Field("verification_code") String verification_code);
//
//
//    @GET("home")
//    Call<HomePageResponse> GetHomePageApiList();
//
//
//    @GET("products/{id}")
//    Call<ProductDetailsApiResponse> GetProductDetailsApi(@Path(value = "id") String id);
//
//
//    @GET("categories")
//    Call<ProductCategoryApiResponse> GetProductCategoryApi();
//
//
//    @GET("products/category/{id}")
//    Call<ProductListApiResponse> GetProductListApi(@Path(value = "id") String id);
//
//
//    @GET("products/suggestion")
//    Call<ProductListApiResponse> GetProductSuggestedListApi();
//
//    @GET("auth/logout")
//    Call<DefaultApiResponse> GetLogoutApi();
//
//    @GET("category/featured")
//    Call<ProductCategoryApiResponse> GetFeaturedProductListApi();
//
//    @GET("products/featured")
//    Call<ProductListApiResponse> GetProductFeaturedListApi();
//
//
//    @FormUrlEncoded
//    @POST("carts/add")
//    Call<DefaultApiResponse> AddToCartApi(@Field("id") String id,
//                                          @Field("variant") String variant,
//                                          @Field("user_id") String user_id,
//                                          @Field("quantity") String quantity
//    );
//
//
//    @FormUrlEncoded
//    @POST("carts/change-quantity")
//    Call<DefaultApiResponse> ChangeQuantity(@Field("id") String id,
//                                            @Field("quantity") String quantity
//    );
//
//
//    @POST("carts/{id}")
//    Call<CartApiResponse> GetAllCartList(@Path(value = "id") String id);
//
//
//    @DELETE("carts/{id}")
//    Call<DefaultApiResponse> DeleteFromCartList(@Path(value = "id") String id);
//
//
//    @GET("cart-summary/{id}")
//    Call<SummyApiResponse> GetTotalSummaryApi(@Path(value = "id") String id);
//
//    @GET("user/info")
//    Call<ProfileDataResponse> GetProfileInfoApi();
//
//    @GET("profile/avatar-original-remove")
//    Call<DefaultApiResponse> RemoveUserProfile();
//
//
//    @FormUrlEncoded
//    @POST("wishlists")
//    Call<DefaultApiResponse> GetSaveToWishlistProduct(@Field("user_id") String user_id,
//                                                      @Field("product_id") String product_id);
//
//
//    @GET("wishlists/{id}")
//    Call<WishlistResponse> GetAllWishlistApi(@Path(value = "id") String id);
//
//
//    @DELETE("wishlists/{id}")
//    Call<DefaultApiResponse> DeleteFromWishList(@Path(value = "id") String id);
//
//
//    @GET("profile/counters/{id}")
//    Call<CounterApiResponse> GetCounterApi(@Path(value = "id") String id);
//
//    //firebase token send api
//    @FormUrlEncoded
//    @POST("profile/update-device-token")
//    Call<DefaultApiResponse> SendFirebaseToken(@Field("device_token") String device_token);
//
//
//    @GET("notification")
//    Call<NotificationListResponse> GetNotificationListApi();
//
//    @FormUrlEncoded
//    @POST("notification/action")
//    Call<NotificationCounterRemoveResponse> RemoveNotificationCounter(@Field("remove_counter") String remove_counter);
//
//    @POST("notification/action")
//    Call<NotificationCounterRemoveResponse> RemoveNotificationList();
//
//    //ticket related all api here
//
//    @GET("support")
//    Call<TicketApiResponse> GetTicketListApi();
//
//    @GET("support/{id}")
//    Call<TicketDetailsResponse> GetTicketDetailsApi(@Path(value = "id") String id);
//
//
//    @FormUrlEncoded
//    @POST("support/create")
//    Call<CancelOrderResponse> CreateNewTicketApi(@Field("subject") String subject,
//                                                 @Field("details") String details);
//
//
//    @FormUrlEncoded
//    @POST("support/send-replay")
//    Call<CancelOrderResponse> SendTicketReplyApi(@Field("ticket_id") String ticket_id,
//                                                 @Field("reply") String reply);
//
//
//    //--------------------------
//
//    @GET("wishlists-check-product")
//    Call<CheckWishlistResponse> CheckIfIsWishlistApi(@Query("product_id") String product_id,
//                                                     @Query("user_id") String user_id
//    );
//
//
//    @GET("reviews/product/{id}")
//    Call<ReviewApiResponse> ReviewRatingApi(@Path(value = "id") String id);
//
//    @FormUrlEncoded
//    @POST("reviews/submit")
//    Call<DefaultApiResponse> AddReviewRatingProduct(@Field("product_id") String product_id,
//                                                    @Field("user_id") String user_id,
//                                                    @Field("rating") String rating,
//                                                    @Field("comment") String comment
//    );
//
//
//    @GET("countries")
//    Call<CountryApiResponse> GetCountryApi();
//
//
//    @GET("states-by-country/{id}")
//    Call<StateApiResponse> GetStateByCountryApi(@Path(value = "id") String id);
//
//    @GET("cities-by-state/{id}")
//    Call<CityApiResponse> GetCityByStateApi(@Path(value = "id") String id);
//
//    @GET("customer-location")
//    Call<AreaCodeApiResponse> GetAreaCodeApi();
//
//    @FormUrlEncoded
//    @POST("user/shipping/create")
//    Call<DefaultApiResponse> AddShippingAddressApi(@Field("user_id") String user_id,
//                                                   @Field("address") String address,
//                                                   @Field("name") String name,
//                                                   @Field("country_id") String country_id,
//                                                   @Field("state_id") String state_id,
//                                                   @Field("city_id") String city_id,
//                                                   @Field("customer_location") String customer_location,
//                                                   @Field("postal_code") String postal_code,
//                                                   @Field("phone") String phone
//    );
//
//
//    @GET("user/shipping/address/{id}")
//    Call<GetAddressApiResponse> GetAddressList(@Path(value = "id") String id);
//
//
//    @GET("user/shipping/delete/{id}")
//    Call<DefaultApiResponse> DeleteAddressList(@Path(value = "id") String id);
//
//    @FormUrlEncoded
//    @POST("user/shipping/make_default")
//    Call<DefaultApiResponse> MakeAddressDefaultApi(@Field("user_id") String user_id,
//                                                   @Field("id") String id
//    );
//
//
//    @FormUrlEncoded
//    @POST("user/shipping/update")
//    Call<DefaultApiResponse> EditShippingAddressApi(@Field("id") String id,
//                                                    @Field("address") String address,
//                                                    @Field("name") String name,
//                                                    @Field("country_id") String country_id,
//                                                    @Field("state_id") String state_id,
//                                                    @Field("city_id") String city_id,
//                                                    @Field("customer_location") String customer_location,
//                                                    @Field("postal_code") String postal_code,
//                                                    @Field("phone") String phone
//    );
//
//    @FormUrlEncoded
//    @POST("update-address-in-cart")
//    Call<DefaultApiResponse> UpdateAddressInCart(@Field("user_id") String user_id,
//                                                 @Field("address_id") String address_id);
//
//
//    @GET("payment-types")
//    Call<List<PaymentTypeResponseItem>> GetPaymentTypeApi();
//
//
//    @FormUrlEncoded
//    @POST("order/store")
//    Call<PlaceOrderResponse> OrderPlaceApiResponse(@Field("owner_id") String owner_id,
//                                                   @Field("user_id") String user_id,
//                                                   @Field("payment_type") String payment_type
//    );
//
//
//    @GET("purchase-history/{id}")
//    Call<OrderHistoryApiResponse> OrderHistoryApiList(@Path(value = "id") String id,
//                                                      @Query("date") String date);
//
//    @GET("purchase-history-details/{id}")
//    Call<OrderDetailsResponse> OrderHistoryDetailsApiList(@Path(value = "id") String id);
//
//    @FormUrlEncoded
//    @POST("order/cancel/{id}")
//    Call<CancelOrderResponse> CancelOrderApi(@Path(value = "id") String id,
//                                             @Field("otp") String otp);
//
//    @GET("otp-for-cancel")
//    Call<DefaultApiResponse> CancelOrderWithOTPApi();
//
//    @GET("products/search?name=")
//    Call<SearchListApiResponse> GetSearchProductList(@Query("name") String name);
//
//    @FormUrlEncoded
//    @POST("products/search")
//    Call<SearchListApiResponse> RecentlySearchApi(@Field("products") String products);

}

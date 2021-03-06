package com.example.athletics.Retrofit;


import com.example.athletics.Model.AthleteCategoryPositionApiResponse;
import com.example.athletics.Model.AthleteInformationApiResponse;
import com.example.athletics.Model.AthleteProfileResponse;
import com.example.athletics.Model.AthleteReqDataResponse;
import com.example.athletics.Model.CategoryPositionResponse;
import com.example.athletics.Model.CoachCategoryResponse;
import com.example.athletics.Model.CoachInformationApiResponse;
import com.example.athletics.Model.CoachProfileApiResponse;
import com.example.athletics.Model.DefaultApiResponse;
import com.example.athletics.Model.FollowingApiResponse;
import com.example.athletics.Model.GetAthleteFollowUnFollowResponse;
import com.example.athletics.Model.HomeAthleteApiResponse;
import com.example.athletics.Model.HomeCoachApiResponse;
import com.example.athletics.Model.HomeExploreApiResponse;
import com.example.athletics.Model.LikeVideoApiResponse;
import com.example.athletics.Model.MyVideoApiResponse;
import com.example.athletics.Model.PackageDetailApiResponse;
import com.example.athletics.Model.PaymentDetailsApiResponse;
import com.example.athletics.Model.ProfileUpdateApiResponse;
import com.example.athletics.Model.SignInData;
import com.example.athletics.Model.StateListApiResponse;
import com.example.athletics.Model.SuitablePlanApiResponse;
import com.example.athletics.Model.UserLikeVideoApiResponse;
import com.example.athletics.Model.VideoCountIncrementResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> GetLogin(@Field("email") String email,
                                @Field("password") String password);


    @GET("user")
    Call<SignInData> GetProfileInfoApi();


    @FormUrlEncoded
    @POST("password")
    Call<ResponseBody> ChangePassword(@Field("password") String password,
                                      @Field("old_password") String old_password,
                                      @Field("password_confirmation") String password_confirmation
    );

    @FormUrlEncoded
    @POST("profile")
    Call<ProfileUpdateApiResponse> UpdateProfileDetails(@Field("name") String name);


    @Multipart
    @POST("profile")
    Call<ProfileUpdateApiResponse> UpdateUserProfile(@Part MultipartBody.Part image);


    //profile verified api


    @GET("profile")
    Call<ResponseBody> GetProfileVerifyOrNotApi();


    @GET("like-video")
    Call<UserLikeVideoApiResponse> GetUserLikeVideoApi();

    @GET("my-video")
    Call<MyVideoApiResponse> GetMyVideoApi();


    @FormUrlEncoded
    @POST("athletes")
    Call<HomeAthleteApiResponse> HomeAthleteApiResponse(@Field("limit") String limit);


    @FormUrlEncoded
    @POST("athletes")
    Call<HomeAthleteApiResponse> AthleteSearchApiResponse(@Field("excludes") String excludes,
                                                          @Field("q") String q,
                                                          @Field("from") String from,
                                                          @Field("cate") String cate);

    @POST("explore")
    Call<HomeExploreApiResponse> HomeExploreApiResponse();

    @POST("coaches")
    Call<HomeCoachApiResponse> HomeCoachApiResponse();

    @FormUrlEncoded
    @POST("coaches")
    Call<HomeCoachApiResponse> CoachSearchApiResponse(@Field("excludes") String excludes,
                                                      @Field("q") String q,
                                                      @Field("from") String from,
                                                      @Field("cate") String cate);


    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> GetSignup(@Field("first_name") String first_name,
                                 @Field("last_name") String last_name,
                                 @Field("email") String email,
                                 @Field("password") String password,
                                 @Field("role") String role
    );

    @GET("my-following")
    Call<FollowingApiResponse> GetMyFollowingApi();

    @GET("my-followers")
    Call<FollowingApiResponse> GetMyFollowerApi();

    @GET("athlete/{id}")
    Call<AthleteProfileResponse> GetAthleteProfileApiResponse(@Path(value = "id") String id);

    @GET("athlete/{id}/follow")
    Call<GetAthleteFollowUnFollowResponse> GetAthleteFollowUnFollow(@Path(value = "id") String id);

    @GET("video/{id}/increment")
    Call<VideoCountIncrementResponse> GetVideoIncrementCount(@Path(value = "id") String id);

    @GET("coach/{id}")
    Call<CoachProfileApiResponse> GetCoachProfileApiResponse(@Path(value = "id") String id);

    @GET("video/{id}/like")
    Call<LikeVideoApiResponse> GetLikeUnlikeVideo(@Path(value = "id") String id);

    @GET("subscription")
    Call<PackageDetailApiResponse> GetSubscriptionPackageApi();

    @GET("suitable-plans")
    Call<SuitablePlanApiResponse> GetSuitablePlanPackageApi();

    @GET("subscription-purchased")
    Call<PaymentDetailsApiResponse> GetPaymentDetailsApi();

    @FormUrlEncoded
    @POST("validate-code")
    Call<ResponseBody> GetValidationCode(@Field("code") String code);


    @GET("delete-video/{id}")
    Call<DefaultApiResponse> GetDeleteMyVideo(@Path(value = "id") String id);

    @GET("coach-information")
    Call<CoachInformationApiResponse> GetCoachInformationApi();

    @GET("athlete-information")
    Call<AthleteInformationApiResponse> GetAthleteInformationApi();

    @GET("category")
    Call<CoachCategoryResponse> GetCoachCategoryApi();

    @FormUrlEncoded
    @POST("categorys/positions")
    Call<CategoryPositionResponse> GetCoachCategoryPositionApi(@Field("ids") String ids);

    @Multipart
    @POST("coach/profile-update")
    Call<DefaultApiResponse> CoachProfileUpdateApiResponse(@Part("sports") RequestBody sports,
                                                           @Part("position") RequestBody position,
                                                           @Part("details") RequestBody details,
                                                           @Part MultipartBody.Part image,
                                                           @Part MultipartBody.Part profile_video,
                                                           @Part MultipartBody.Part resume
    );


    @Multipart
    @POST("athlete/video/post")
    Call<DefaultApiResponse> GetAthleteVideoUpload(@Part("title") RequestBody title,
                                                   @Part MultipartBody.Part thumb,
                                                   @Part MultipartBody.Part video);


    @GET("athlete-reqdata")
    Call<AthleteReqDataResponse> GetAthleteReqDataApi();

    @FormUrlEncoded
    @POST("categorys/positions-sports")
    Call<AthleteCategoryPositionApiResponse> GetCategoryPositionSportsApi(@Field("ids") String ids);

    @GET("country/{id}/states")
    Call<StateListApiResponse> GetStateListApi(@Path(value = "id") String id);

    @GET("resend-verify-email")
    Call<DefaultApiResponse> GetEmailVerifyApiResponse();


    @Multipart
    @POST("athlete/profile-update")
    Call<AthleteInformationApiResponse> AthleteProfileUpdateApiResponse(
            @Part("position") RequestBody position,
            @Part("state") RequestBody state,
            @Part("university_id") RequestBody university_id,
            @Part("state_id") RequestBody state_id,
            @Part("country_id") RequestBody country_id,
            @Part("school") RequestBody school,
            @Part("speed") RequestBody speed,
            @Part("year_complete") RequestBody year_complete,
            @Part("major") RequestBody major,
            @Part("gpa") RequestBody gpa,
            @Part("age") RequestBody age,
            @Part("year") RequestBody year,
            @Part("height") RequestBody height,
            @Part("team") RequestBody team,
            @Part("weight") RequestBody weight,
            @Part("playing_weight") RequestBody playing_weight,
            @Part("category_id") RequestBody category_id,
            @Part("references") RequestBody references,
            @Part("gender") RequestBody gender,
            @Part MultipartBody.Part image,
            @Part MultipartBody.Part profile_video
    );
}

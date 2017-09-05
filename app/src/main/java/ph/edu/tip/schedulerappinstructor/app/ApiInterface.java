package ph.edu.tip.schedulerappinstructor.app;


import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ph.edu.tip.schedulerappinstructor.model.data.Admin;
import ph.edu.tip.schedulerappinstructor.model.data.CityList;
import ph.edu.tip.schedulerappinstructor.model.data.Company;
import ph.edu.tip.schedulerappinstructor.model.data.Event;
import ph.edu.tip.schedulerappinstructor.model.data.EventDaily;
import ph.edu.tip.schedulerappinstructor.model.data.Promo;
import ph.edu.tip.schedulerappinstructor.model.data.Province;
import ph.edu.tip.schedulerappinstructor.model.data.Seat;
import ph.edu.tip.schedulerappinstructor.model.data.SecurityQuestion;
import ph.edu.tip.schedulerappinstructor.model.data.SecurityQuestion2;
import ph.edu.tip.schedulerappinstructor.model.data.TransactionEvent;
import ph.edu.tip.schedulerappinstructor.model.response.BasicResponse;
import ph.edu.tip.schedulerappinstructor.model.response.LoginResponse;
import ph.edu.tip.schedulerappinstructor.model.response.UploadProfileImageResponse;

//TODO: Delete Unused APIs
public interface ApiInterface {


    //Credentials
    @FormUrlEncoded
    @POST(Endpoints.LOGIN)
    Call<LoginResponse> login(@Field(Constants.EMAIL_ADD) String emailAddress,
                              @Field(Constants.PASSWORD) String password);

    @GET(Endpoints.FORGOT_PASSWORD)
    Call<BasicResponse> forgotPassword(@Path("email_address") String email);

    @FormUrlEncoded
    @POST(Endpoints.REGISTER)
    Call<BasicResponse> register(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @PUT(Endpoints.CHANGE_PASSWORD)
    Call<BasicResponse> changePassword(@Header(Constants.AUTHORIZATION) String authorization,
                                       @Field(Constants.EMAIL_ADD) String emailAdd,
                                       @Field(Constants.OLD_PASSWORD) String oldPassword,
                                       @Field(Constants.PASSWORD) String password);

    @POST(Endpoints.LOGOUT)
    Call<BasicResponse> deleteGCMToken(@Header(Constants.AUTHORIZATION) String authorization, @Header(Constants.ACCEPT) String json);


    //Profile
    @GET(Endpoints.PROVINCE)
    Call<List<Province>> getProvince();

    @GET(Endpoints.CITIES)
    Call<List<CityList>> getCities(@Path("province_id") Integer province_id);


    @GET(Endpoints.SECURITY_QUESTION)
    Call<List<SecurityQuestion>> getSecurityQuestions(@Query("set_number") Integer set_number);

    @GET(Endpoints.SECURITY_QUESTION2)
    Call<List<SecurityQuestion2>> getSecurityQuestions2(@Query("set_number") Integer set_number);

    @FormUrlEncoded
    @POST(Endpoints.UPDATE_ACCOUNT)
    Call<LoginResponse> updateAccount(@Header(Constants.AUTHORIZATION) String authorization,
                                      @FieldMap Map<String, String> params, @Header(Constants.ACCEPT) String json);

    @Multipart
    @POST(Endpoints.UPLOAD_IMG)
    Call<UploadProfileImageResponse> upload(@Header(Constants.AUTHORIZATION) String authorization, @Header(Constants.ACCEPT) String json
            , @Part MultipartBody.Part file);


    //Promo
    @GET(Endpoints.PROMO_LIST)
    Call<List<Promo>> getPromoList(@Header(Constants.AUTHORIZATION) String authorization, @Header(Constants.ACCEPT) String json);


    @GET(Endpoints.PROMO_DETAIL)
    Call<Promo> getPromoDetail(@Header(Constants.AUTHORIZATION) String authorization,
                               @Path("id") int promoId, @Header(Constants.ACCEPT) String json);


    //Company
    @GET(Endpoints.COMPANY_DETAIL)
    Call<Company> getCompanyDetail(@Header(Constants.AUTHORIZATION) String authorization,
                                   @Path("id") String merchantId, @Header(Constants.ACCEPT) String json);

    @GET(Endpoints.COMPANY_LIST)
    Call<List<Company>> getCompanyList(@Header(Constants.AUTHORIZATION) String authorization, @Header(Constants.ACCEPT) String json);


    //Events
    @GET(Endpoints.EVENT_DETAIL)
    Call<Event> getEventDetail(@Header(Constants.AUTHORIZATION) String authorization,
                               @Path("id") String eventId, @Header(Constants.ACCEPT) String json);

    @GET(Endpoints.EVENT_LIST)
    Call<List<Event>> getEventList(@Header(Constants.AUTHORIZATION) String authorization, @Header(Constants.ACCEPT) String json);


    //Daily
    @GET(Endpoints.DAILY_DETAIL)
    Call<EventDaily> getDailyDetail(@Header(Constants.AUTHORIZATION) String authorization,
                                    @Path("id") String eventId, @Header(Constants.ACCEPT) String json);

    @GET(Endpoints.DAILY_LIST)
    Call<List<EventDaily>> getDailyList(@Header(Constants.AUTHORIZATION) String authorization, @Header(Constants.ACCEPT) String json);


    //Admin
    @GET(Endpoints.ADMIN_LIST)
    Call<List<Admin>> getAdminList(@Header(Constants.AUTHORIZATION) String authorization, @Path("id") String eventId, @Header(Constants.ACCEPT) String json);


    @GET(Endpoints.ADMIN_DETAIL)
    Call<Admin> getAdminDetail(@Header(Constants.AUTHORIZATION) String authorization,
                               @Path("id") int userId, @Header(Constants.ACCEPT) String json);


    //Transactions
    @GET(Endpoints.TRANSACTIONS)
    Call<List<TransactionEvent>> transactions(@Header(Constants.AUTHORIZATION) String authorization, @Header(Constants.ACCEPT) String json);

    @GET(Endpoints.TRANSACTIONS_DETAIL)
    Call<TransactionEvent> getTransactionEventDetail(@Header(Constants.AUTHORIZATION) String authorization,
                                                     @Path("id") String transactionEventId, @Header(Constants.ACCEPT) String json);

    @GET(Endpoints.EVENT_SEAT)
    Call<List<Seat>> getSeatList(@Header(Constants.AUTHORIZATION) String authorization, @Path("id") String eventId, @Header(Constants.ACCEPT) String json);


    @FormUrlEncoded
    @POST(Endpoints.TRANSACTIONS_RESERVE)
    Call<BasicResponse> reserveEvent(@Header(Constants.AUTHORIZATION) String authorization,
                                     @Field(Constants.CATEGORY_ID) String eventid,
                                     @Field(Constants.SEAT_NUM) String seatnum,
                                     @Header(Constants.ACCEPT) String json);

    //////////////////// end of uly api //////////////////////////


    //scheduled events

    @Multipart
    @POST(Endpoints.CREATE_SCHEDULED_EVENT)
    Call<BasicResponse> createEvent(@Header(Constants.AUTHORIZATION) String authorization,
                                    @Part(Constants.ApiParameters.ScheduledEvents.SCHEDULED_EVENT_NAME) RequestBody name,
                                    @Part(Constants.ApiParameters.ScheduledEvents.SCHEDULED_EVENT_DESC) RequestBody desc,
                                    @Part(Constants.ApiParameters.ScheduledEvents.SCHEDULED_EVENT_LOC) RequestBody location,
                                    @Part(Constants.ApiParameters.ScheduledEvents.SCHEDULED_EVENT_ADD) RequestBody address,
                                    @Part(Constants.ApiParameters.ScheduledEvents.SCHEDULED_EVENT_LAT) RequestBody lat,
                                    @Part(Constants.ApiParameters.ScheduledEvents.SCHEDULED_EVENT_LNG) RequestBody lng,
                                    @Part(Constants.ApiParameters.ScheduledEvents.SCHEDULED_EVENT_TYPE) RequestBody type,
                                    @Part(Constants.ApiParameters.ScheduledEvents.SCHEDULED_EVENT_TAGS) RequestBody tags,
                                    @Part MultipartBody.Part image,
                                    @Header(Constants.ACCEPT) String json);

    @Multipart
    @POST(Endpoints.CREATE_SCHEDULED_EVENT_SLOT_CATEGORY)
    Call<BasicResponse> createEventSlotCategory(@Header(Constants.AUTHORIZATION) String authorization,
                                                @Part(Constants.ApiParameters.ScheduledEventSlotCategory.SCHEDULED_EVENT_ID) RequestBody scheduledEventId,
                                                @Part(Constants.ApiParameters.ScheduledEventSlotCategory.NAME) RequestBody name,
                                                @Part(Constants.ApiParameters.ScheduledEventSlotCategory.SLOT_ALOTTED) RequestBody slotAlotted,
                                                @Part(Constants.ApiParameters.ScheduledEventSlotCategory.PRICE) RequestBody price,
                                                @Part(Constants.ApiParameters.ScheduledEventSlotCategory.SELECT_SEAT_NUMBER) RequestBody selectSeatNumber,
                                                @Part MultipartBody.Part image,
                                                @Header(Constants.ACCEPT) String json);

}

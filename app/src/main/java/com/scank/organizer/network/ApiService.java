package com.scank.organizer.network;


import com.scank.organizer.model.CheckInResponse;
import com.scank.organizer.model.EventTicketResponse;
import com.scank.organizer.model.EventsResponse;
import com.scank.organizer.model.GuestApiResponse;
import com.scank.organizer.model.LoginRequest;
import com.scank.organizer.model.LoginResponse;
import com.scank.organizer.model.ReportApiResponse;
import com.scank.organizer.model.RequestModelDeviceCode;
import com.scank.organizer.model.ResponseWalkthroughModel;
import com.scank.organizer.model.StaticModel;
import com.scank.organizer.model.SubmitTicketResponse;
import com.scank.organizer.model.TicketRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiService {

    // login
    @Headers({
            "Accept: */*",
            "Content-Type: application/json"
    })
    @POST("api/organizer/login")
    Call<LoginResponse> login(@Body LoginRequest paramsMap);

    @POST("Organizer/UpdateDeviceToken")
    Call<StaticModel> updateDeviceCode(@Body RequestModelDeviceCode model);

    @POST("api/User/ForgetPassword")
    Call<StaticModel> forgotPassword(@Query("email") String email);

    // Logout
    @Headers({
            "Accept: */*",
            "Content-Type: application/json"
    })
    @POST("api/user/Logout")
    Call<StaticModel> logout(@Header("accesstoken") String content_type, @Query("userId") Integer userId);

    /**
     *  getEventList
     * @param content_type
     * @param userId
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @GET("api/organizer/GetUpcomingEvents")
    Call<EventsResponse> getEventsList(@Header("accesstoken") String content_type, @Query("userId") Integer userId);


    /**
     *  getEventList
     * @param content_type
     * @param eventId
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @GET("api/organizer/GetGuestList")
    Call<GuestApiResponse> getGuestList(@Header("accesstoken") String content_type, @Query("eventId") Integer eventId);


    /**
     *  getEventList
     * @param content_type
     * @param eventId
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @GET("api/organizer/GetEventReport")
    Call<ReportApiResponse> getEventReport(@Header("accesstoken") String content_type, @Query("eventId") Integer eventId);


    /**
     *  getEventList
     * @param content_type
     * @param ticketId
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @GET("api/organizer/CheckIn")
    Call<CheckInResponse> checkIn(@Header("accesstoken") String content_type, @Query("ticketId") String ticketId);


    @GET("api/AppSetting/GetWalkthroughList")
    Call<ResponseWalkthroughModel> getWalkthroughList();

    // get EventTicket List
    /**
     *  getEventList
     * @param content_type
     * @param userId
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @GET("api/organizer/GetEventTicketList")
    Call<EventTicketResponse> getEventTicketList(@Header("accesstoken") String content_type, @Query("userId") Integer userId);



    // update Ticket CheckedIn
    @Headers({"Content-Type: application/json"})
    @POST("api/organizer/UpdateTicketCheckIn")
    Call<SubmitTicketResponse> updateTicketCheckedIn(@Header("accesstoken") String content_type, @Body TicketRequest ticketRequest);

}




package me.hashcode.dawadeals.network;

import java.util.List;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

@SuppressWarnings("unused")
public interface APIRequests {
//    @FormUrlEncoded
//    @POST("api/auth/login")
//    Single<UserDetails> login(@Field("referee_username") String email,
//                              @Field("refree_password") String password);
//    @POST("api/auth/me")
//    Call<UserDetails> getUserDetails();
//     @GET("api/homeData")
//    Single<HomeDataResponse> startUp(@Query("referee_id") int user);
//    @POST("api/getQuestion")
//    Single<QuestionResponse> nexQuestion(@Body RequestBody requestBody);
//    @GET("api/getQuestion")
//    Single<QuestionResponse> nexQuestion(@Query("page") int page,
//                                         @Query("referee_id") int id,
//                                         @Query("exam_id") int exam_id,
//                                         @Query("is_answer") int hasAnswer,
//                                         @Query("question_id") int questionId,
//                                         @Query("question_type") int questionType,
//                                         @Query("option_id[]") List<Integer> options,
//                                         @Query("option_text") String answer2,
//                                         @Query("text_option") String answer
//    );
//    @GET("api/finishExam")
//    Single<QuestionResponse> finishExam(
//            @Query("referee_id") int id,
//            @Query("exam_id") int exam_id,
//            @Query("is_answer") int hasAnswer,
//            @Query("question_id") int questionId,
//            @Query("question_type") int questionType,
//            @Query("option_id[]") List<Integer> options,
//            @Query("option_text") String answer2,
//            @Query("text_option") String answer
//    );
////    @GET("getQuestion")
////    Single<QuestionResponse> prevQuestion(@Body RequestBody requestBody);
//     @GET("api/finishExam")
//    Single<QuestionResponse> finishExam(@Query("referee_id") int user,
//                                        @Query("exam_id") int exam_id,
//                                        @Query("is_answer") int hasAnswer);
//    //@FormUrlEncoded
//    @GET("api/getNews")
//    Single<NewsResponse> getNews();
}

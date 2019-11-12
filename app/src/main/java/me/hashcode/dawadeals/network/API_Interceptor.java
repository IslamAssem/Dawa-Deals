package me.hashcode.dawadeals.network;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import me.hashcode.dawadeals.utils.Utils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by muneeb.vectorcoder@gmail.com on 30-Jan-18.
 */

public class API_Interceptor implements Interceptor {
    /*
    private static final String ECOMMERCE_CONSUMER_KEY = "consumer-key";
    private static final String ECOMMERCE_CONSUMER_SECRET = "consumer-secret";
    private static final String ECOMMERCE_CONSUMER_NONCE = "consumer-nonce";
    private static final String ECOMMERCE_CONSUMER_DEVICE_ID = "consumer-device-id";
    
    private String consumerKey;
    private String consumerSecret;
    private String consumerNonce;
    private String consumerDeviceID;
    */
    
    private API_Interceptor() {
    }
    
    
    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        HttpUrl url = originalHttpUrl.newBuilder().build();
        
        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
               .addHeader("Accept", "application/json")
               .addHeader("Content-Type", "application/json")
               .addHeader("Authorization", Utils.getAuthorization())
                .url(url);
        
        
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
    

    
    
    public static final class Builder {
        
        public API_Interceptor build() {
            return new API_Interceptor();
        }
    }
    
    public String urlEncoded(String url) {
        String encodedURL = "";
        try {
            encodedURL = URLEncoder.encode(url, "UTF-8");
            Log.e("encodedURL", encodedURL);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        return encodedURL;
    }
}

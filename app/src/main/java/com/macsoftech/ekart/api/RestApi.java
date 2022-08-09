package com.macsoftech.ekart.api;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.macsoftech.ekart.BuildConfig;
import com.macsoftech.ekart.app.BaseApp;
import com.macsoftech.ekart.model.LoginResponse;
import com.macsoftech.ekart.model.LoginRootResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Ramesh on 11/06/21.
 */

public class RestApi {

    private static final String CACHE_CONTROL = "Cache-Control";


    public static String BASE_URL = "http://43.204.146.144:4000/";
    private static RestApi mRestApi;


    public static synchronized RestApi getInstance() {
        if (mRestApi == null) {
            mRestApi = new RestApi();
        }
        return mRestApi;
    }

    public MyService getService() {
        return buildAdapter(BASE_URL, buildOkHttpClient(), MyService.class);
    }


    private OkHttpClient buildOkHttpClient() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient defaultHttpClient = builder
                .addInterceptor(interceptor)
//                .addInterceptor(provideOfflineCacheInterceptor())
//                .addNetworkInterceptor(provideCacheInterceptor())
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        if (BaseApp.getInstance() != null) {
                            LocalBroadcastManager.getInstance(BaseApp.getInstance())
                                    .sendBroadcast(new Intent("LOGOUT"));
                        }
                        return null;
                    }
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
//                .protocols(CollectionUtils.listOf(Protocol.HTTP_1_1))
                .protocols(Arrays.asList(Protocol.HTTP_1_1))
                .build();
        return defaultHttpClient;
    }

    <T> T buildAdapter(String baseUrl, OkHttpClient defaultHttpClient, Class<T> clazz) {
        //Build Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(defaultHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    //
    private static Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(BaseApp.getInstance().getCacheDir(), "http-cache"),
                    10 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
//            Timber.e(e, "Could not create Cache!");
        }
        return cache;
    }

//    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
//        HttpLoggingInterceptor httpLoggingInterceptor =
//                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//                    @Override
//                    public void log(String message) {
//                        Timber.d(message);
//                    }
//                });
//        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HEADERS : NONE);
//        return httpLoggingInterceptor;
//    }

    public static Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(2, TimeUnit.MINUTES)
                        .build();

                return response.newBuilder()
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }


    public static Map<String, RequestBody> prepareBodyPart(Map<String, String> map) {
        // add another part within the multipart request
        Map<String, RequestBody> partMap = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            partMap.put(entry.getKey(),
                    RequestBody.create(MediaType.parse("text/plain"),
                            entry.getValue()));

        }
        return partMap;

    }

    @Nullable
    public static MultipartBody.Part prepareFilePart(String partName, String filePath, UploadCallbacks callbacks) {
        try {
            ProgressRequestBody fileBody = new ProgressRequestBody(new File(filePath), callbacks);
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }
            if (callbacks == null) {
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                return MultipartBody.Part.createFormData(partName, file.getName(), reqFile);
            } else {
                return MultipartBody.Part.createFormData(partName, file.getName(), fileBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public interface MyService {

        @GET
        Call<ResponseBody> callDynamicUrl(@Url String url);

        //        {
//            "emailId":"gowthami@gmail.com",
//                "password":"gfdsdf"
//        }
        @POST("users/login")
        Call<LoginRootResponse> login(@Body Map<String, String> body);

        @Multipart
        @POST("users/register")
        Call<ResponseBody> register();

    }

    //

    public interface UploadCallbacks {
        void onProgressUpdate(int percentage);

        void onError();

        void onFinish();
    }

    public static class ProgressRequestBody extends RequestBody {
        private File mFile;
        private String mPath;
        private UploadCallbacks mListener;

        private static final int DEFAULT_BUFFER_SIZE = 2048;


        public ProgressRequestBody(final File file, final UploadCallbacks listener) {
            mFile = file;
            mListener = listener;
        }

        @Override
        public MediaType contentType() {
            // i want to upload only images
            return MediaType.parse("image/*");
        }

        @Override
        public long contentLength() throws IOException {
            return mFile.length();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            long fileLength = mFile.length();
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            FileInputStream in = new FileInputStream(mFile);
            long uploaded = 0;

            try {
                int read;
                Handler handler = new Handler(Looper.getMainLooper());
                while ((read = in.read(buffer)) != -1) {
                    uploaded += read;
                    sink.write(buffer, 0, read);
                    // update progress on UI thread
                    handler.post(new ProgressUpdater(uploaded, fileLength));
                }
            } finally {
                in.close();
            }
        }

        private class ProgressUpdater implements Runnable {
            private long mUploaded;
            private long mTotal;

            public ProgressUpdater(long uploaded, long total) {
                mUploaded = uploaded;
                mTotal = total;
            }

            @Override
            public void run() {
                mListener.onProgressUpdate((int) (100 * mUploaded / mTotal));
            }
        }
    }
    //

}

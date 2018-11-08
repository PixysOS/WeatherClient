package com.pixys.weather.client;

import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.pixys.weather.client.Constants.DEBUG;

class SunriseSunsetRestApi {

    public static int RESULT_FAILED = -1;
    public static int RESULT_DAY = 0;
    public static int RESULT_NIGHT = 1;
    private static String TAG = "SunriseSunsetRestApi";

    public static int queryApi(String latitude, String longitude) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        try {
            Response response = httpClient.newCall(new Request.Builder()
                    .tag("SunsetSunriseApi")
                    .url("https://api.sunrise-sunset.org/json?formatted=0&lat=" + latitude + "&lng=" + longitude)
                    .build()).execute();
            if (response.body() != null && response.isSuccessful()) {
                String result = response.body().string();
                if (DEBUG) Log.d(TAG, "Json result: " + result);
                SunriseSunsetJsonResult jsonResult = new Gson().fromJson(result, SunriseSunsetJsonResult.class);
                if (jsonResult.getStatus().equals(SunriseSunsetJsonResult.STATUS_OK)) {
                    if (DEBUG) Log.d(TAG, "Json result ok");
                    Calendar currentCalendar = GregorianCalendar.getInstance();
                    String iso8601Pattern = "yyyy-MM-dd'T'HH:mm:ssZ";
                    Calendar sunriseTime = GregorianCalendar.getInstance();
                    sunriseTime.setTime(new SimpleDateFormat(iso8601Pattern, Locale.getDefault()).parse(jsonResult.getResults().getSunrise()));
                    Calendar sunsetTime = GregorianCalendar.getInstance();
                    sunsetTime.setTime(new SimpleDateFormat(iso8601Pattern, Locale.getDefault()).parse(jsonResult.getResults().getSunset()));
                    if (DEBUG){
                        Log.d(TAG, "Current time is: " + new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(currentCalendar.getTime()));
                        Log.d(TAG, "Sunrise time is: " + new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(sunriseTime.getTime()));
                        Log.d(TAG, "Sunset time is: " + new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(sunsetTime.getTime()));
                    }
                    if (currentCalendar.getTimeInMillis() >= sunriseTime.getTimeInMillis() && currentCalendar.getTimeInMillis() < sunsetTime.getTimeInMillis()) {
                        if (DEBUG) Log.d(TAG, "It's day");
                        return RESULT_DAY;
                    } else {
                        if (DEBUG) Log.d(TAG, "It's night");
                        return RESULT_NIGHT;
                    }
                }
            }
        } catch (Exception e) {
            if (DEBUG) Log.e(TAG, "Exception", e);
        }
        return RESULT_FAILED;
    }

    private class SunriseSunsetJsonResult {

        private static final String STATUS_OK = "OK";

        @SerializedName("results")
        @Expose
        private Results results;
        @SerializedName("status")
        @Expose
        private String status;

        public Results getResults() {
            return results;
        }

        public void setResults(Results results) {
            this.results = results;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public class Results {

            @SerializedName("sunrise")
            @Expose
            private String sunrise;
            @SerializedName("sunset")
            @Expose
            private String sunset;
            @SerializedName("solar_noon")
            @Expose
            private String solarNoon;
            @SerializedName("day_length")
            @Expose
            private String dayLength;
            @SerializedName("civil_twilight_begin")
            @Expose
            private String civilTwilightBegin;
            @SerializedName("civil_twilight_end")
            @Expose
            private String civilTwilightEnd;
            @SerializedName("nautical_twilight_begin")
            @Expose
            private String nauticalTwilightBegin;
            @SerializedName("nautical_twilight_end")
            @Expose
            private String nauticalTwilightEnd;
            @SerializedName("astronomical_twilight_begin")
            @Expose
            private String astronomicalTwilightBegin;
            @SerializedName("astronomical_twilight_end")
            @Expose
            private String astronomicalTwilightEnd;

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public String getSolarNoon() {
                return solarNoon;
            }

            public void setSolarNoon(String solarNoon) {
                this.solarNoon = solarNoon;
            }

            public String getDayLength() {
                return dayLength;
            }

            public void setDayLength(String dayLength) {
                this.dayLength = dayLength;
            }

            public String getCivilTwilightBegin() {
                return civilTwilightBegin;
            }

            public void setCivilTwilightBegin(String civilTwilightBegin) {
                this.civilTwilightBegin = civilTwilightBegin;
            }

            public String getCivilTwilightEnd() {
                return civilTwilightEnd;
            }

            public void setCivilTwilightEnd(String civilTwilightEnd) {
                this.civilTwilightEnd = civilTwilightEnd;
            }

            public String getNauticalTwilightBegin() {
                return nauticalTwilightBegin;
            }

            public void setNauticalTwilightBegin(String nauticalTwilightBegin) {
                this.nauticalTwilightBegin = nauticalTwilightBegin;
            }

            public String getNauticalTwilightEnd() {
                return nauticalTwilightEnd;
            }

            public void setNauticalTwilightEnd(String nauticalTwilightEnd) {
                this.nauticalTwilightEnd = nauticalTwilightEnd;
            }

            public String getAstronomicalTwilightBegin() {
                return astronomicalTwilightBegin;
            }

            public void setAstronomicalTwilightBegin(String astronomicalTwilightBegin) {
                this.astronomicalTwilightBegin = astronomicalTwilightBegin;
            }

            public String getAstronomicalTwilightEnd() {
                return astronomicalTwilightEnd;
            }

            public void setAstronomicalTwilightEnd(String astronomicalTwilightEnd) {
                this.astronomicalTwilightEnd = astronomicalTwilightEnd;
            }

        }
    }
}
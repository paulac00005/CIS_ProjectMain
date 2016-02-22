package com.example.paulac.cis_project;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by paulac on 2/22/16.
 */
public class ServerRequest {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://10.4.101.11/sbs/";

    public void ServerRequest(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait . . .");
    }

    public void storeUsersDataInBackground(User user, GetUserCallBack callBack){
        progressDialog.show();
        new ServerReqTask(user, callBack).execute();
    }

    public void fetrchUserDataInBackground(User user, GetUserCallBack callBack){
        progressDialog.show();
        new ServerReqTask.fetchUserDataTask(user, callBack).execute();
    }


    public class ServerReqTask extends AsyncTask<Void, Void, Void> {

        User user;
        GetUserCallBack userCallBack;

        public ServerReqTask(User user, GetUserCallBack callBack) {
            this.user = user;
            this.userCallBack = callBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallBack.done(null);
            super.onPostExecute(aVoid);
        }


        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));
            dataToSend.add(new BasicNameValuePair("key", user.key));

            HttpParams httpParams = (HttpParams) new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient((cz.msebera.android.httpclient.params.HttpParams) httpParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "login.php");


            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        public class fetchUserDataTask extends AsyncTask<Void, Void, User> {

            User user;
            GetUserCallBack userCallBack;

            public fetchUserDataTask(User user, GetUserCallBack callBack) {
                this.user = user;
                this.userCallBack = callBack;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(User returnedUser) {
                progressDialog.dismiss();
                userCallBack.done(returnedUser);
                super.onPostExecute(returnedUser);
            }

            @Override
            protected User doInBackground(Void... params) {

                ArrayList<NameValuePair> dataToSend = new ArrayList<>();
                dataToSend.add(new BasicNameValuePair("username", user.username));
                dataToSend.add(new BasicNameValuePair("password", user.password));

                HttpParams httpParams = (HttpParams) new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
                HttpConnectionParams.setSoTimeout(httpParams, CONNECTION_TIMEOUT);

                HttpClient client = new DefaultHttpClient((cz.msebera.android.httpclient.params.HttpParams) httpParams);
                HttpPost post = new HttpPost(SERVER_ADDRESS + "login.php");


                User returnedUser = null;

                try {
                    post.setEntity(new UrlEncodedFormEntity(dataToSend));
                    HttpResponse response = client.execute(post);

                    HttpEntity entity = response.getEntity();
                    String result = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(result);

                    if(jsonObject.length() == 0){
                        returnedUser = null;
                    }else{
                        String username = jsonObject.getString("username");

                        returnedUser = new User(user.username, user.password);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;

            }

        }

    }}

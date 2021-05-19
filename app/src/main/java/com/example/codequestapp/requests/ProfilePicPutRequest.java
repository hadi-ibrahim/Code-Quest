package com.example.codequestapp.requests;

import android.content.ContentResolver;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.lifecycle.LiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.example.codequestapp.responses.ErrorResponseListener;
import com.example.codequestapp.responses.ResponseMessage;
import com.example.codequestapp.responses.UpdateProfileResponseListener;
import com.example.codequestapp.utils.AppContext;
import com.example.codequestapp.utils.TokenEncryptedSharedPreferences;

import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.entity.mime.StringBody;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ProfilePicPutRequest extends Request<String> {

    private HttpEntity entity;
    private LiveData<ResponseMessage> data;
    private Map<String, String> header = new HashMap<String, String>();
    private static final String FILE_PART_NAME = "image";

    private final UpdateProfileResponseListener listener;
    private File file;

    public ProfilePicPutRequest() {
        this(RequestUtil.BASE_URL + "api/user/changeProfilePicture", new ErrorResponseListener(), new UpdateProfileResponseListener());
    }

    public ProfilePicPutRequest(String url, Response.ErrorListener errorListener, Response.Listener<String> listener) {
        super(Request.Method.PUT, url, errorListener);
        this.listener = (UpdateProfileResponseListener) listener;
        this.data = ((UpdateProfileResponseListener) listener).getData();
        this.header.put("auth-token", TokenEncryptedSharedPreferences.getInstance().getAuthToken());
    }

    public void setFile(File file) {
        this.file = file;
        buildMultipartEntity();
    }

    private void buildMultipartEntity() {
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        String mimeType = null;
        Uri uri = Uri.fromFile(file);
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver cr = AppContext.getContext().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        ContentType type;
        System.out.println(mimeType + "--------------------------");
        entityBuilder.addPart(FILE_PART_NAME, new FileBody(file,ContentType.getByMimeType(mimeType)));
        entity = entityBuilder.build();
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return Response.success(new String(response.data), getCacheEntry());
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return this.header;
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }

    public LiveData<ResponseMessage> getData() {
        return data;
    }
}
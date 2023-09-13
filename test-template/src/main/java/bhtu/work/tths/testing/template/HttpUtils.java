package bhtu.work.tths.testing.template;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import vht.testing.example1.testcase.HttpUtil;

import java.util.Collections;
import java.util.Map;

public class HttpUtils {
    public static String post2(String url, String data, Map<String, String> headers, Map<String, String> urlParams) throws Exception {
        try (CloseableHttpClient httpclient = HttpUtil.makeHttpsClientUnsafe()){
            HttpPost post = new HttpPost(addUrlParam(url, urlParams));
            post.setHeader("Content-Type", "application/json");
            headers.forEach(post::addHeader);
            HttpEntity entity = new StringEntity(data, "UTF-8");
            post.setEntity(entity);
            //post.setConfig(dtr.C_rawl.getDefaultTimedOutRequestConfig(10000));
            CloseableHttpResponse response = httpclient.execute(post);
            return HttpUtil.getResponceString(response, post);
        }
    }

    public static String put2(String url, String data, Map<String, String> headers, Map<String, String> urlParams) throws Exception {
        try (CloseableHttpClient httpclient = HttpUtil.makeHttpsClientUnsafe()){
            var put = new HttpPut(addUrlParam(url, urlParams));
            put.setHeader("Content-Type", "application/json");
            headers.forEach(put::addHeader);
            HttpEntity entity = new StringEntity(data, "UTF-8");
            put.setEntity(entity);
            //post.setConfig(dtr.C_rawl.getDefaultTimedOutRequestConfig(10000));
            CloseableHttpResponse response = httpclient.execute(put);
            return HttpUtil.getResponceString(response, put);
        }

    }

    public static String get2(String url, Map<String, String> headers, Map<String, String> urlParams) throws Exception {
        try (CloseableHttpClient httpclient = HttpUtil.makeHttpsClientUnsafe()){
            var get = new HttpGet(addUrlParam(url, urlParams));
            get.setHeader("Content-Type", "application/json");
            headers.forEach(get::addHeader);
            // HttpEntity entity = new StringEntity(data, "UTF-8");
            // put.setEntity(entity);
            //post.setConfig(dtr.C_rawl.getDefaultTimedOutRequestConfig(10000));
            CloseableHttpResponse response = httpclient.execute(get);
            return HttpUtil.getResponceString(response, get);
        }
    }

    private static String addUrlParam(String url, Map<String, String> urlParams) {
        var ite = urlParams.entrySet().iterator();
        StringBuilder urlBuilder = new StringBuilder(url);
        if (ite.hasNext()) urlBuilder.append("?");
        while (ite.hasNext()) {
            var key_val = ite.next();
            urlBuilder.append(key_val.getKey()).append("=").append(key_val.getValue());
            if (ite.hasNext()) urlBuilder.append("&");
        }
        return urlBuilder.toString();
    }
}

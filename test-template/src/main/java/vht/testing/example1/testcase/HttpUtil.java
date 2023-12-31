package vht.testing.example1.testcase;

import io.micrometer.core.instrument.util.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtil {
    public static PoolingHttpClientConnectionManager makePoolingHttpClientConnectionManager() throws NoSuchAlgorithmException, KeyManagementException {
        return makePoolingHttpClientConnectionManager(false);
    }

    public static PoolingHttpClientConnectionManager makePoolingHttpClientConnectionManager(boolean socksEnable) throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] certs = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }
                }
        };

        HostnameVerifier allHostnameVerifier = (String s, SSLSession sslSession) -> true;

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, certs, new SecureRandom());

        Registry registry = null;
        if (socksEnable) {
            registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    //.register("https", new SockConnectionSocketFactory(sslContext, allHostnameVerifier)) // force
                    .build();
        } else {
            registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    //.register("https", new SSLConnectionSocketFactory(sslContext, allHostnameVerifier)) // force
                    .build();
        }
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);

        httpClientConnectionManager.setMaxTotal(2000000);
        httpClientConnectionManager.setDefaultMaxPerRoute(2000000);
        return httpClientConnectionManager;

    }

    public static CloseableHttpClient makeHttpsClientUnsafe() throws Exception {
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
        return HttpClients.custom().setSSLSocketFactory(sslsf)
                .setConnectionManager(HttpUtil.makePoolingHttpClientConnectionManager())
                .setConnectionTimeToLive(60, TimeUnit.SECONDS)
                .build();
    }


    public static String inputStream2String(InputStream inputStream) throws IOException {
        return IOUtils.toString(inputStream, Charset.forName("UTF-8"));
    }

    public static String getResponceString(CloseableHttpResponse response, HttpRequestBase httpRequestBase) throws IOException {
        try {
            String html =  inputStream2String(response.getEntity().getContent());
            return html;
        } finally {
            try {
                httpRequestBase.releaseConnection();
            } catch (Exception e) {
                //dtr.S_ystem.printStackTree(e);
            }
            try {
                response.close();
            } catch (Exception e) {
                //dtr.S_ystem.printStackTree(e);
            }
        }
    }

    public static String post2(String url, String data) throws Exception {
        try (CloseableHttpClient httpclient = HttpUtil.makeHttpsClientUnsafe()){
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            HttpEntity entity = new StringEntity(data, "UTF-8");
            post.setEntity(entity);
            //post.setConfig(dtr.C_rawl.getDefaultTimedOutRequestConfig(10000));
            CloseableHttpResponse response = httpclient.execute(post);
            return HttpUtil.getResponceString(response, post);
        }
    }
}

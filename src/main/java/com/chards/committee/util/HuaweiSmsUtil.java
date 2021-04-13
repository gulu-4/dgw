package com.chards.committee.util;

import lombok.Data;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
//如果JDK版本是1.8,可使用原生Base64类
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * @author LiuSu
 * @create 2021/4/13 14:59
 */
@Data
@ConfigurationProperties(prefix = "huawei.sms")
@Component
public class HuaweiSmsUtil {

    //无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    //无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";

    private String url;
    private String appKey;
    private String appSecret;
    private String sender;
    private String templateId;
//    @Value("${huawei.sms.signature}")
    private String signature;

    public void sendSms(String phone,String location, String time) throws Exception{
        //短信接收人号码
        String receiver = "+86" + phone;

        //选填,短信状态报告接收地址,推荐使用域名,为空或者不填表示不接收状态报告
        String statusCallBack = "";

        // {1}场地名称 ｛2｝时间  时间如果是日期2021-04-12 时间08:00:00
        if (location.length() > 20) {
            location = location.substring(0,20);
        }
        String templateParas = "[\""+location+"\",\""+time+"\"]";

        //请求Body,不携带签名名称时,signature请填null
        String body = buildRequestBody(sender, receiver, templateId, templateParas, statusCallBack, signature);
        if (null == body || body.isEmpty()) {
            System.out.println("body is null.");
            return;
        }

        //请求Headers中的X-WSSE参数值
        String wsseHeader = buildWsseHeader(appKey, appSecret);
        if (null == wsseHeader || wsseHeader.isEmpty()) {
            System.out.println("wsse header is null.");
            return;
        }
        //如果JDK版本是1.8,可使用如下代码
        CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null,
                        (x509CertChain, authType) -> true).build())
                .setSSLHostnameVerifier(new DefaultHostnameVerifier())
                .build();

        HttpResponse response = client.execute(RequestBuilder.create("POST")//请求方法POST
                .setUri(url)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE)
                .addHeader("X-WSSE", wsseHeader)
                .setEntity(new StringEntity(body)).build());

        System.out.println(response.toString()); //打印响应头域信息
        System.out.println(EntityUtils.toString(response.getEntity())); //打印响应消息实体
    }

    /**
     * 构造请求Body体
     * @param sender
     * @param receiver
     * @param templateId
     * @param templateParas
     * @param statusCallbackUrl
     * @param signature | 签名名称,使用国内短信通用模板时填写
     * @return
     */
    static String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
                                   String statusCallbackUrl, String signature) {
        if (null == sender || null == receiver || null == templateId || sender.isEmpty() || receiver.isEmpty()
                || templateId.isEmpty()) {
            System.out.println("buildRequestBody(): sender, receiver or templateId is null.");
            return null;
        }
        List<NameValuePair> keyValues = new ArrayList<NameValuePair>();

        keyValues.add(new BasicNameValuePair("from", sender));
        keyValues.add(new BasicNameValuePair("to", receiver));
        keyValues.add(new BasicNameValuePair("templateId", templateId));
        if (null != templateParas && !templateParas.isEmpty()) {
            keyValues.add(new BasicNameValuePair("templateParas", templateParas));
        }
        if (null != statusCallbackUrl && !statusCallbackUrl.isEmpty()) {
            keyValues.add(new BasicNameValuePair("statusCallback", statusCallbackUrl));
        }
        if (null != signature && !signature.isEmpty()) {
            keyValues.add(new BasicNameValuePair("signature", signature));
        }

        return URLEncodedUtils.format(keyValues, Charset.forName("UTF-8"));
    }

    /**
     * 构造X-WSSE参数值
     * @param appKey
     * @param appSecret
     * @return
     */
    static String buildWsseHeader(String appKey, String appSecret) {
        if (null == appKey || null == appSecret || appKey.isEmpty() || appSecret.isEmpty()) {
            System.out.println("buildWsseHeader(): appKey or appSecret is null.");
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = sdf.format(new Date()); //Created
        String nonce = UUID.randomUUID().toString().replace("-", ""); //Nonce

        byte[] passwordDigest = DigestUtils.sha256(nonce + time + appSecret);
        String hexDigest = Hex.encodeHexString(passwordDigest);

        //如果JDK版本是1.8,请加载原生Base64类,并使用如下代码
        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(hexDigest.getBytes()); //PasswordDigest

        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }

    public String transformLocalTime(LocalDateTime startTime) {
        String hour = toTwo(startTime.getHour());
        String minutes = toTwo(startTime.getMinute());
        String second = toTwo(startTime.getSecond());

        return hour + ":" + minutes + ":" + second;
    }

    static String toTwo(Integer integer){
        if (integer < 10) {
            return "0" + integer;
        }
        return "" + integer;
    }
}

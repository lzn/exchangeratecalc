package com.lzn.exchangerates.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzn.exchangerates.model.NBPTable;
import com.lzn.exchangerates.model.Rate;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.commons.io.IOUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "rates")
public class NBPRatesService {

    @Cacheable(condition = "true")
    public HashMap<String, Rate> getRates() throws URISyntaxException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        HashMap<String, Rate> rates = new HashMap<String, Rate>();

        HttpEntity tableA = getHttpEntity(getUri("http", "api.nbp.pl", "/api/exchangerates/tables/A/"));
        HttpEntity tableB = getHttpEntity(getUri("http", "api.nbp.pl", "/api/exchangerates/tables/B/"));
       // HttpEntity tableC = getHttpEntity(getUri("http", "api.nbp.pl", "/api/exchangerates/tables/C/"));

        rates.putAll(convertToHashMap(tableA));
        rates.putAll(convertToHashMap(tableB));
       // rates.putAll(convertToHashMap(tableC));



        return rates;
    }

    private HashMap<String, Rate> convertToHashMap(HttpEntity tableA) throws IOException {
        Gson gson = new Gson();

        InputStream is = tableA.getContent();
        StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer, null);
        List<NBPTable> nbpTable = gson.fromJson(writer.toString(),  new TypeToken<ArrayList<NBPTable>>(){}.getType());

        HashMap<String, Rate> rates = new HashMap<String, Rate>();
        for (NBPTable t:nbpTable) {
            log.info(t.toString());

            HashMap<String, Rate> hm = new HashMap<String, Rate>();
            for (Rate r : t.getRates()){
                hm.put(r.getCode(), r);
            }
            rates.putAll(hm);
        }
        log.info(nbpTable.toString());

        return rates;
    }


    private HttpEntity getHttpEntity(URI uri) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        HttpGet httpget = new HttpGet(uri);

        log.info(httpget.toString());
        System.out.println(httpget.toString());


        CloseableHttpClient httpclient = prepareHttpClient();
        CloseableHttpResponse response = httpclient.execute(httpget);

        HttpEntity entity = response.getEntity();
        Header[] responseHeaders = response.getAllHeaders();

        StatusLine statusLine = response.getStatusLine();

        if(statusLine.getStatusCode()!= 200 ) {
            log.info(statusLine.toString());
            for (Header h : responseHeaders) {
                log.info(h.toString());
            }
        }

        return entity;
    }

    private  URI getUri(String scheme, String host, String path) throws URISyntaxException {
        return new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(path)
                .build();
    }

    public  CloseableHttpClient prepareHttpClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return HttpClients.custom()
                .build();
    }

}

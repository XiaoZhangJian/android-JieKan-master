package com.kims.jiekan;

import com.kims.jiekan.model.GankIO;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zhangjian on 2017/5/6.
 */

public interface GankApi {

    @GET("data/{type}/{count}/{page}")
    Observable<GankIO> GankIO(@Path("type") String type, @Path("count") int count, @Path("page") int page);

}

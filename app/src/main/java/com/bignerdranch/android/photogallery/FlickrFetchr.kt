package com.bignerdranch.android.photogallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.photogallery.api.FlickrApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "FlickrFetchr"

class FlickrFetchr {

    private val flickrApi: FlickrApi

    /**
     * Создаём экземпляр Retrofit
     * с базовым URL и
     * addConverterFactory, который используется для преобразования JSON-ответа в объекты Java.
     */
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Cоздается экземпляр интерфейса FlickrApi, который определяет методы для взаимодействия с API Flickr.
        //Retrofit использует аннотацию GET у FlickrApi интерфейса для генерации кода, который выполняет HTTP-запросы и преобразует ответы в объекты Java.
        flickrApi = retrofit.create(FlickrApi::class.java)
    }

    /**
     * Создаём асинхронный запрос .enqueue
     * В случаи если запрос не прошел - onFailuer и логируем ошибку.
     * В случаи успешного запроса - onResponce и обрабатываем ответ от сервера и заполняется LiveData с результатом запроса.
     * Резуьтат работы фун-ции возвращает LiveData с результатом запроса.
     */
    fun fetchPhotos(): LiveData<List<GalleryItem>> {
        val responseLiveData: MutableLiveData<List<GalleryItem>> = MutableLiveData()
        val flickrRequest: Call<FlickrResponse> = flickrApi.fetchPhotos()

        flickrRequest.enqueue(object : Callback<FlickrResponse> {

            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }

            override fun onResponse(
                call: Call<FlickrResponse>,
                response: Response<FlickrResponse>,
            ) {
                Log.d(TAG, "Response received")
                val flickrResponse: FlickrResponse? = response.body()
                val photoResponse: PhotoResponse? = flickrResponse?.photos
                var galleryItems: List<GalleryItem> = photoResponse?.galleryItems
                    ?: mutableListOf()
                galleryItems = galleryItems.filterNot {
                    it.url.isBlank()
                }
                responseLiveData.value = galleryItems
            }
        })

        return responseLiveData
    }
}
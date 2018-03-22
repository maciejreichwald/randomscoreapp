package com.rudearts.randomscore.extentions

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.threadToAndroid(): Observable<T> {
    return this
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
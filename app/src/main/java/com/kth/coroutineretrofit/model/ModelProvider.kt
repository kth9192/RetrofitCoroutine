package com.kth.coroutineretrofit.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StarWars(
    var list: List<People>
) : Parcelable

@Parcelize
data class People(
    var name: String,
    var height: String,
    var mass: String,
    var birthYear: String,
    var gender: String,
    var homeworld: String,
    var filmsfilms: List<String>

) : Parcelable

@Parcelize
data class Film(
    var title: String?,
    var director: String?
) : Parcelable



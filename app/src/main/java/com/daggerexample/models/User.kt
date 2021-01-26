package com.daggerexample.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User (
    @SerializedName("id")
    @Expose
    var id: Int?,

    @SerializedName("username")
    @Expose
    var username: String?,

    @SerializedName("email")
    @Expose
    var email: String?,

    @SerializedName("website")
    @Expose
    var website: String?,
)
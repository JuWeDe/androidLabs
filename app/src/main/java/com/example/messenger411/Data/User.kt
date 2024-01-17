package com.example.messenger411.Data

import android.os.Parcel
import android.os.Parcelable


class User(val userName: String, val email: String, val password: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""

    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userName)
        parcel.writeString(email)
        parcel.writeString(password)

    }

    companion object CREATOR :
        Parcelable.Creator<User> {
        override fun createFromParcel(p0: Parcel): User {
            return User(p0)
        }

        override fun newArray(p0: Int): Array<User?> {
            return arrayOfNulls(0)
        }
    }


}
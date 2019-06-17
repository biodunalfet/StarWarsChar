package com.hf.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonListItemView (val name : String?,
                               val height : String?,
                               val birth_year : String?,
                               val species : List<String>?,
                               val homeworld : String?,
                               val films : List<String>?,
                               val url : String) :  Parcelable
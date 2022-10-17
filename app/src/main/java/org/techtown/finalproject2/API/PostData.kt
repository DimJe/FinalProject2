package org.techtown.finalproject2.API

import java.io.Serializable

data class PostData(
    val img : Int,
    val title : String,
    val content : String,
    val type : String,
    val date : String,
    val where : String
) : Serializable
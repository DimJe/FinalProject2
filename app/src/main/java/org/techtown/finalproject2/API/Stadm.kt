package org.techtown.finalproject2.API

import java.io.Serializable

data class Stadm(
    val stadm_NO_PK : Int,
    val stadm_NM : String,
    val stadm_ADDR : String,
    val stadm_TELNO : String,
    val stadm_LAT : Float, //위도
    val stadm_LONG : Float // 경도
) : Serializable

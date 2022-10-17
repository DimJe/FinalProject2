package org.techtown.finalproject2.API

import java.io.Serializable

data class User(val user_NO_PK : Int,
                val user_NM : String,
                val user_NCKNM : String,
                val user_TELNO : String,
                val user_GNDR : String,
                val user_AGE : Int,
                val user_HGT : Float,
                val user_WGT : Float,
                val user_RGN : String,
                val club_NO_FK : Int,
                val user_MNR_SCR : Float,
                val user_SKL_SCR : Float,
                val user_EML_ADDR : String
) : Serializable
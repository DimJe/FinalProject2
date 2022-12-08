package org.techtown.finalproject2.API

import java.io.Serializable

data class ClubData(
    val club_MAX_NOP : Int,
    val club_NM : String,
    val club_RGN : String,
    val spt_EVT : String,
    val club_NO_PK : Int,
    val ldr_EMLADDR_FK : String
) : Serializable

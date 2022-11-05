package org.techtown.finalproject2.API

import java.io.Serializable

data class User(val user_NO_PK : Int, // 유저번호
                val user_NM : String, //유저 이름
                val user_NCKNM : String, // 유저 닉네임
                val user_TELNO : String, // 유저 전화번호
                val user_GNDR : String, // 유저 성별
                val user_AGE : Int, // 나이
                val user_HGT : Float, // 키
                val user_WGT : Float, // 몸무게
                val user_RGN : String, // 지역
                val club_NO_FK : Int, // 가입 클럽 번호
                val user_MNR_SCR : Float, // 매너 점수
                val user_SKL_SCR : Float, // 실력 점수
                val user_EML_ADDR : String // 이메일
) : Serializable
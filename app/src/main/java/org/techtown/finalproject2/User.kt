package org.techtown.finalproject2

data class User(val userName : String, val userNickName : String,val birth : String,
                val userPhoneNumber : String, val gender : Char, val length : Int,
                val weight : Int, val region : String, val type : String,
                val sports : ArrayList<String>, val team : String, val career : String
) {


}
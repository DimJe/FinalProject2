package org.techtown.finalproject2.API

import java.io.Serializable

data class PostData(
    val pst_NO_PK : Int, //게시글 번호
    val stadm_NO_FK : Int, //경기장 번호
    val pst_NM : String, //게시글 이름
    val pst_CN : String, //게시글 내용
    val match_EVT : String, // 경기 종류
    val match_DT : String, // 경기 일자
    val match_NOP : Int, // 경기 인원수
    val pstuser_NO_FK : Int // 게시글 작성자 유저 번호
) : Serializable
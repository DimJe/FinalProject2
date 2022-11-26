package org.techtown.finalproject2.Chat

class ChatData{
    var user : String = ""
    var msg : String = ""
    var timeStamp : Long = 0L
    constructor(){}
    constructor(user : String,msg : String,timeStamp : Long){
        this.user = user
        this.msg = msg
        this.timeStamp = timeStamp
    }
}
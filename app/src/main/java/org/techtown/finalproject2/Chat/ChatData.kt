package org.techtown.finalproject2.Chat

class ChatData{
    var user : String = ""
    var msg : String = ""
    var timeStamp : String = ""
    constructor(){}
    constructor(user : String,msg : String,timeStamp : String){
        this.user = user
        this.msg = msg
        this.timeStamp = timeStamp
    }
}
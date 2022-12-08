package org.techtown.finalproject2.Chat

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable


@IgnoreExtraProperties
class ChatRoomData : Serializable{
    var lastMessage : String? = null
    var chatId : String = ""
    var chatName : String = ""
    var timeStamp : Double = 0.0
    var me : String = ""
    var other : String = ""
    var meCheck : Boolean = false
    var otherCheck : Boolean = false
    var meEstimate : Boolean = false
    var otherEstimate : Boolean = false
    constructor() {}
    constructor(lastMessage : String, chatId : String,chatName : String, timeStamp : Double,Me : String, Other : String){
        this.lastMessage = lastMessage
        this.chatName = chatName
        this.chatId = chatId
        this.timeStamp = timeStamp
        this.me = Me
        this.other = Other
        this.meCheck = false
        this.meEstimate = false
        this.otherCheck = false
        this.otherEstimate = false
    }
}

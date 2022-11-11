package org.techtown.finalproject2.Chat

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable


@IgnoreExtraProperties
class ChatRoomData : Serializable{
    var lastMessage : String? = null
    var chatId : String = ""
    var chatName : String = ""
    var timeStamp : String? = null
    var me : String = ""
    var other : String = ""
    constructor() {}
    constructor(lastMessage : String,chatId : String,chatName : String, timeStamp : String,Me : String, Other : String){
        this.lastMessage = lastMessage
        this.chatName = chatName
        this.chatId = chatId
        this.timeStamp = timeStamp
        this.me = Me
        this.other = Other
    }
}

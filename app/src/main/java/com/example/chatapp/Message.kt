package com.example.chatapp

class Message {
     var message:String? = null
     var senderId:String? = null
    constructor(){}
    constructor(message: String?,userId:String?){
        this.message = message
        this.senderId = userId
    }
}
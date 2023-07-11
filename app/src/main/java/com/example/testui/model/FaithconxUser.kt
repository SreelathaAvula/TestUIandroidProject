package com.example.testui.model

data class FaithconxUser(val title:String,
val subTitle:String,
val picture:ProfilePicture,
val detailsData:List<ListData>,val connectionCount:String,val count:String) {

}

class ListData (val firstData:String,
                val secondData:String,
                val thirdData:String,
                val fourData:String
){

}
data class ProfilePicture ( val first: String,
                            val Second: String,){

}
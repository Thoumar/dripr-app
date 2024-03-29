package com.dripr.dripr.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    var id: String,
    var cover: String,
    var title: String,
    var snippet: String,
    var body: String,
    var likes: Int,
    var authorName: String,
    var authorPicture: String,
    var timeRead: Int,
    var publicationDate: String
) : Parcelable {
    constructor() : this(
        "test",
        "https://i2.wp.com/lourdesactu.fr/wp-content/uploads/2020/04/placeholder.png?fit=1200%2C800",
        "Nouvel article",
        "test",
        "test",
        0,
        "Thomas Oumar",
        "https://media-exp1.licdn.com/dms/image/C5603AQGA13ywxDxivw/profile-displayphoto-shrink_200_200/0?e=1606348800&v=beta&t=GwzPphTNCQOG3ErAITEJy6SlLp_7BSq61Cq3HNfPoqo",
        5,
        "20-09-2020"
    )
}
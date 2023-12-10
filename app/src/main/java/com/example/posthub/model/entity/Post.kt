package com.example.posthub.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Entity(
    tableName = "posts"
)
@Parcelize
data class Post(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var photo: String,
    var comment: String,
    var createDate: LocalDate,
    var color: Int,
    var editDate: LocalDate?
) : Parcelable

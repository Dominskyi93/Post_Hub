package com.example.posthub.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date

@Entity(
    tableName = "posts"
)
data class Post(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var photo: String,
    var comment: String,
    var date: LocalDate,
    var color: Int
)

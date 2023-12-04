package com.lixoten.fido.feature_notes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lixoten.fido.ui.theme.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val title: String,
    val content: String,
    val color: Int,
    @ColumnInfo(name = "checked")
    val isChecked: Boolean = false,
    @ColumnInfo(name = "pined")
    val isPinned: Boolean,
    @ColumnInfo(name = "created_date")
    val createdDate: Long = 0L,
    @ColumnInfo(name = "updated_date")
    val lastMaintDate: Long = 0L,
) {
    companion object {
        val noteColors = listOf(
            RedOrange,
            LightGreen,
            Violet,
            BabyBlue,
            RedPink
        )
    }
}
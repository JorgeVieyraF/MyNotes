package com.lixoten.fido.feature_notes.data

import com.lixoten.fido.feature_notes.model.Note

object InitialData {

    fun getNotes() =
        List(6) {
            Note(it,
                title = "Note # $it",
                content = "",
                color = 0,
                isChecked = false,
                isPinned = false,
            )
        }
}
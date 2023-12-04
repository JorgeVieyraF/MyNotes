package com.lixoten.fido.feature_notes.domain.use_case

class ValidateNoteTitleUseCase {

    fun execute(title: String) : Result {
        if (title.length < 2) {
            return Result(
                successful = false,
                errorMessage = "TOO SMALL"
            )
        }
        return Result(
            successful = true,
        )
    }

    data class Result(
        val successful: Boolean,
        val errorMessage: String? = null,
    )
}
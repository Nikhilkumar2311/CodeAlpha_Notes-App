package com.example.notesapp

import java.io.Serializable

class Firebasemodel(
    var title: String? = null,
    var content: String? = null
) : Serializable {
    constructor() : this("title", "content")

    companion object {
        fun createDefaultInstance(): Firebasemodel {
            return Firebasemodel(title = "title", content = "content")
        }
    }
}


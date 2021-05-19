package com.example.cookbook

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class RecipeData {
    var title: String? = null
    var description: String? = null

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    constructor(title: String?, description: String?) {
        this.title = title
        this.description = description
    }
}


package com.jetbrains.handson.mpp.mobile

class Station(id: String, name: String) {
    private val name = name
    private val id = id

    fun getId() : String {
        return id
    }

    fun getName() : String {
        return name
    }
}
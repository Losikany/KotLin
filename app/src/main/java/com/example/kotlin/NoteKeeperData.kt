package com.example.kotlin

data class CourseInfo(val courseId: String, val title:String) {
    override fun toString(): String {
        return courseId
    }
}

data class NoteInfo(var course:CourseInfo ?=null, var title: String?=null, var text:String?=null)
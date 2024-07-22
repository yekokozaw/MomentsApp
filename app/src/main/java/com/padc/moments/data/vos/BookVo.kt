package com.padc.moments.data.vos

data class BookVo(
    var bookTitle : String = "",
    var bookId : Long = 0L,
    var fileUrl : String = "",
    var selectedYear : String = ""
)
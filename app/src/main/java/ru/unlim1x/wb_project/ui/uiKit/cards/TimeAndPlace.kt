package ru.unlim1x.wb_project.ui.uiKit.cards

import java.security.InvalidParameterException
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone

class TimeAndPlace(
    var place: String,
    private var date: Int,
    private var month: Int,
    private var year: Int,
    private var hours: Int,
    private var minutes: Int,
) {
    val calendar =
        GregorianCalendar(TimeZone.getTimeZone("Europe/Moscow")) //На всякий случай, мало ли потом будет фильтр по дате
    private lateinit var stringDate: String
    private lateinit var stringMonth: String
    private lateinit var stringYear: String

    init {
        if (month > 12 || month <= 0)
            throw (InvalidParameterException("month должен быть меньше 13 или больше 0"))
        if (date > 31 || date <= 0)
            throw (InvalidParameterException("date должен быть меньше 32 или больше 0"))
        if (hours < 0 || hours > 23)
            throw (InvalidParameterException("hours должен быть меньше 24 или больше 0"))
        if (minutes < 0 || minutes > 59)
            throw (InvalidParameterException("minutes должен быть меньше 60 или больше 0"))
        if (year < 0)
            throw (InvalidParameterException("year должен быть больше 0"))
        if (year < 1900)
            year = 1900

        stringDate = if (date < 10)
            "0$date"
        else
            "$date"
        stringMonth = if (month < 10)
            "0$month"
        else
            "$month"
        stringYear = "$year"
    }

    companion object {
        private const val UNKNOWN_PLACE = "Unknown"
    }


    constructor(place: String, date: Int, month: Int, year: Int) : this(
        place = place,
        date = date,
        month = month,
        year = year,
        hours = 0,
        minutes = 0
    )

    constructor() : this(
        place = UNKNOWN_PLACE,
        date = 1,
        month = 1,
        year = 1900,
        hours = 0,
        minutes = 0
    )

    val dateString: String get() = stringDate + stringMonth + stringYear
    val dateAndPlaceString: String get() = "$stringDate.$stringMonth.$stringYear — $place"
    val timeDate: Date get() = calendar.time

    fun setDate(date: Int, month: Int, year: Int) {
        calendar.set(year, month, date)
    }

    fun setDate(hours: Int, minutes: Int, date: Int, month: Int, year: Int) {
        calendar.set(year, month, date, hours, minutes)
    }


}

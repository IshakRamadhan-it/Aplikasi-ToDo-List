package id.ac.unhas.todolist.ui

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField

class Converter {
    companion object{
        fun dateToInt(time: ZonedDateTime): Int{
            return time.toInstant().epochSecond.toInt()
        }

        fun stringDateToInt(date: String): Int{
            val formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")
            return LocalDate.parse(date, formatter).toEpochDay().toInt()
        }

        fun stringTimeToInt(time: String): Int{
            return LocalTime.parse(time).getLong(ChronoField.MILLI_OF_DAY).toInt()
        }
    }
}
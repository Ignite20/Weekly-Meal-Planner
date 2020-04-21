package com.emberestudio.project.ui.domain.model

enum class WeekDays(var order: Int, var nName : String) {
    MONDAY(0, "Monday"),
    TUESDAY(1, "Tuesday"),
    WEDNESDAY(2, "Wednesday"),
    THURSDAY(3, "Thursday"),
    FRIDAY(4, "Friday"),
    SATURDAY(5, "Saturday"),
    SUNDAY(6, "Sunday");

    companion object{
        fun toList() : HashMap<Int, String>{
            var map = HashMap<Int, String>()
            values().forEach {
                map[it.order] = it.nName
            }
            return map
        }

        fun findByOrder(order: Int) : WeekDays? {
            return values().find { it.order == order }
        }

        fun findByName(name : String) : WeekDays? {
            return values().find { it.nName == name }
        }
    }
}
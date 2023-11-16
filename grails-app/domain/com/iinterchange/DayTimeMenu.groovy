package com.iinterchange

class DayTimeMenu {
    String date
    Integer item_id 
    String item_name 
    Double price 
    Integer unit_sold 
    static constraints = {
        date blank: false
        item_id unique: true, blank: false
        item_name blank: false
        price blank: false, range:0.0..1000.00
        unit_sold range: 50..1000
    }
}

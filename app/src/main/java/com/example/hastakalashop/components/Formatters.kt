package com.example.hastakalashop.components

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val rupeeFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
private val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

fun Double.asRupees(): String = rupeeFormat.format(this)

fun Long.asSaleDate(): String = dateFormat.format(Date(this))

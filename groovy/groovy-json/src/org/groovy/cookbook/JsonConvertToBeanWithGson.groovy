package org.groovy.cookbook

@Grab('com.google.code.gson:gson:2.2.2')
import com.google.gson.Gson
import org.groovy.cookbook.Vehicle

def gson = new Gson()

def reader = new File('vehicle.json').newReader()

println gson.fromJson(new FileReader('vehicle.json'), Vehicle)
package com.example.testfire.HealthCenterClasses

data class pri(var priorityname:String="",var priorityweight:Int=0)
data class QueueDetailsforHealthCenters (var Avgqueuetime:String="0",var QueueCapacity:Int=0,var QueueName:String="NULL",var currentnumber:Int=0,var QueueVisiblity:Boolean=false,var PriorityElement:List<pri> = listOf(pri()),var idunique:String="n")
package com.example.hope

class RequestTemplate {
    var patient_Name:String?= null
    var blood_grp:String?= null
    var contact:String?= null
    var req_raiser:String?= null
    var location:String?= null
    constructor(patient_Name:String,blood_grp:String,contact:String,req_raiser:String,location:String){

        this.patient_Name = patient_Name
        this.blood_grp = blood_grp
        this.contact = contact
        this.req_raiser = req_raiser
        this.location = location
    }
    }
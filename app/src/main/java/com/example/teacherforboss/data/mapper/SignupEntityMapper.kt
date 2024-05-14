package com.example.teacherforboss.data.mapper

import com.example.teacherforboss.data.model.request.signup.RequestSignupDto
import com.example.teacherforboss.domain.model.SignupEntity


//domain entity-> data dto(api)
fun SignupEntity.toRequestSignupDto()= RequestSignupDto(
    email=this.email,
    password=this.password,
    rePassword=this.rePassword,
    name=this.name,
    gender=this.gender,
    birthDate=this.birthDate,
    phone=this.phone,
    emailAuthId=this.emailAuthId,
    phoneAuthId=this.phoneAuthId,
    agreementUsage=this.agreementUsage,
    agreementInfo=this.agreementInfo,
    agreementAge=this.agreementAge,
    agreementSms=this.agreementSms,
    agreementEmail=this.agreementEmail,
    agreementLocation=this.agreementLocation

)
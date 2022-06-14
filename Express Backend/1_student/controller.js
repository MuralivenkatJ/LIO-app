const con = require("../0_main/app")
const mongoose = require("mongoose")
const Student = require("./model")
const Course = require("../5_course/model")
const Institute = require("../3_institute/model")

function register(req, res)
{
    var {name, email, password, institute} = req.body
    if(req.file)
        var image = req.file.filename
    
    var flag = 0
    Institute.findOne({i_name: institute}, (err, document) => {
        if(err)
            console.log("error")

        if(document == null)
            document = {_id: undefined}
        
        Student.create({
            s_name: name,
            email: email,
            password: password,
            image: image,
            institute: document._id
        })
    })

    res.send("The student is registered")
}

function login(req, res)
{
    res.send("You are logged in as a student")
}

function logout(req, res)
{
    res.send("You are logged out as a student")
}


async function mycourses(req, res)
{
    var enrolled_courses = await Student.findById(req.params.s_id)
        .select({enrolled: 1})
        .populate({path: "enrolled.course"})
        .catch( (err) => {
            console.log(err)
        })

    res.json(enrolled_courses)
}

async function wishlist(req, res)
{
    var wishlist = await Student.findById(req.params.s_id)
        .select({wishlist: 1})
        .populate({path: "wishlist"})
        .catch( (err) => {
            console.log(err)
        })


    res.json(wishlist)
}


module.exports = {register, login, logout, mycourses, wishlist}
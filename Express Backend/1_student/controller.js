const mongoose = require("mongoose")
const Student = require("./model")
const Course = require("../5_course/model")
const Institute = require("../3_institute/model")

const ip = "http:\\\\127.0.0.1:3000\\"
const c_folder = ip + "\\course_images\\"

function register(req, res)
{
    var {name, email, password, institute} = req.body
    if(req.file)
        var image = req.file.filename
    
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


async function payment(req, res)
{
    var {s_id, c_id, utrid} = req.body
    var screenshot = req.file.filename

    var payment = {"student": s_id, "course": c_id, "utrid": utrid, "screenshot": screenshot}

    var course = await Course.findById(c_id)
        .select({faculty: 1})
        .populate({path: "faculty", select: {"institute": 1}})
        .catch( (err) => {
            console.log(err)
        })

    var i_id = course.faculty.institute
    var i = await Institute.findByIdAndUpdate(i_id, {$push: {payment: payment}}, {new: true})

    res.send("Payment screenshot is uploaded")
}


async function mycourses(req, res)
{
    var enrolled_courses = await Student.findById(req.params.s_id)
        .select({enrolled: 1})
        .populate({path: "enrolled.course"})
        .catch( (err) => {
            console.log(err)
        })

    enrolled_courses.enrolled.forEach( (document) => {
        document.course.image = c_folder + document.course.image
    })

    res.json(enrolled_courses)
}

async function wishlist(req, res)
{
    var wishlist_courses = await Student.findById(req.params.s_id)
        .select({wishlist: 1})
        .populate({path: "wishlist"})
        .catch( (err) => {
            console.log(err)
        })

    wishlist_courses.wishlist.forEach( (document) => {
        document.image = c_folder + document.image
    })

    res.json(wishlist_courses)
}


module.exports = {register, login, logout, payment, mycourses, wishlist}
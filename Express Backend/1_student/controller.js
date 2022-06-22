const mongoose = require("mongoose")
const Student = require("./model")
const Course = require("../5_course/model")
const Institute = require("../3_institute/model")
const { isEnrolled } = require("../helpers/course_verification")

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

async function review(req, res)
{
    var {s_id, c_id, rate, desc} = req.body
    var review = {"student": s_id, "rate": rate, "desc": desc}


    //Check if the student is enrolled for the course
    if( await ! isEnrolled(s_id, c_id) )
        return res.send("You are not enrolled for this course. So you cannot review this course.")

    //Check if the student has already given the review (in that case update it)
    var isReviewed = await Course.findById(c_id)
        .select({reviews: 1})
        .where({"reviews.student": s_id})
        .catch( (err) => {
            console.log(err)
        })
    
    if(isReviewed)
    {
        var c = await Course.findById(c_id)
            .select({reviews: 1, rating: 1})
            .where()
            .catch( (err) => {
                console.log(err)
            })
        
        var n = parseFloat(c.reviews.length)
        var rating = parseFloat(c.rating)
        var old_rate = 0.0
        for(let i=0; i<n; i++)
            if(c.reviews[i].student.toString() == s_id)
            {
                old_rate = parseFloat(c.reviews[i].rate)
                break;
            }
        
        var new_rating = ( rating * n - old_rate + parseFloat(rate) ) / n;
        console.log(new_rating)

        //Update the existing review
        c = await Course.updateOne({_id: c_id, "reviews.student": s_id},
            {$set: {"reviews.$.rate": rate, "reviews.$.desc": desc, "reviews.$.date": Date.now(), rating: new_rating}})
    }
    else
    {
        var c = await Course.findByIdAndUpdate(c_id, {$push: {"reviews": review}}, {new: true})

        //finding the number of reviews 
        var n = parseFloat(c.reviews.length)
        var rating = parseFloat(c.rating)
        var new_rating = ( rating * parseFloat((n-1))  + parseFloat(rate) ) / n;

        c = await Course.findByIdAndUpdate(c_id, {"rating": new_rating}, {new: true})
        console.log(c)
    }

    res.send("Review is given for the course")
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


module.exports = {register, login, logout, payment, review, mycourses, wishlist}
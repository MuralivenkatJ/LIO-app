const con = require("../0_main/app")
const mongoose = require("mongoose")
const Student = require("./model")
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


function mycourses(req, res)
{
    var enrolled_courses = []
    var s_id = 6

    res.send("This is my course page")
}

function wishlist(req, res)
{
    var wishlist = []
    var s_id = 5


    res.send("This is wishlist page")
}


module.exports = {register, login, logout, mycourses, wishlist}
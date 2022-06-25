const Institute = require("../3_institute/model")
const Faculty = require("./model")

const ip = "http:\\\\127.0.0.1:3000\\"
const c_folder = ip + "\\course_images\\"

//EMAIL
const nodemailer = require("nodemailer")

const transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
        user: "liolearnitonline@gmail.com",
        pass: "bjsmzsktvkkaqkgn"
    }
})

async function register(req, res)
{
    var {name, email, password, qualification, phone, institute} = req.body
    var image = req.file.filename

    var i = await Institute.findOne({i_name: institute})
            .catch( (err) => {
                console.log(err)
            })

    var f = new Faculty({
        f_name: name,
        qualification: qualification,
        image: image,
        email: email,
        password: password,
        phone: phone,
        institute: (i)?i._id : undefined
    })

    f.save( (err, result) => {
        if(err)
            console.log(err)
        else
        {
            var mailoptions = {
                from: "liolearnitonline@gmail.com",
                to: email,
                subject: "Welcome to LIO",
                text: `Hello ${name}, \n\n\tLIO is an online educational platform to bring students and teachers all over the world together. \n\nYour registration was successful with the following details: \nName: ${name} \nPhone: ${phone} \nEmail Id: ${email} \nQualification: ${qualification} \nInstitute: ${institute} \n\nBest Wishes, \nTeam LIO`
            }

            transporter.sendMail(mailoptions, (err, info) => {
                if(err)
                    console.log(err)
                // else
                //     console.log(info)
            })
        }
    })

    res.send("You are registered as a faculty")
}

function login(req, res)
{
    res.send("You are logged in as a faculty")
}

function logout(req, res)
{
    res.send("You are logged out as a faculty")
}


async function mycourses(req, res)
{
    var uploaded_courses = await Faculty.findById(req.params.f_id)
        .select({courses: 1})
        .populate({path: "courses"})
        .catch( (err) => {
            console.log(err)
        })
    
    uploaded_courses.courses.forEach( (document) => {
        document.image = c_folder + document.image
    })

    res.json(uploaded_courses)
}


module.exports = {register, login, logout, mycourses}
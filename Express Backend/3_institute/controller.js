const Institute = require("./model")
const Student = require("../1_student/model")
const Faculty = require("../2_faculty/model")

const ip = "http:\\\\127.0.0.1:3000\\"
const f_folder = ip + "\\faculty_images\\"
const screenshot_folder = ip + "\\payment_screenshots\\"

function register(req, res)
{
    var {name, email, password, website, ac_name, ac_no, ifsc} = req.body
    var image = req.file.filename

    var i = new Institute({
        i_name: name,
        email: email,
        password: password,
        image: image,
        website: website,
        payment_details: {ac_name: ac_name, ac_no: ac_no, ifsc: ifsc}
    })

    i.save( (err, result) => {
        if(err)
            console.log(err)
        // console.log(result)
    })

    res.send("You are registered as an institute")
}

function login(req, res)
{
    res.send("You are logged in as an institute")
}

function logout(req, res)
{
    res.send("You are logged out as an institute")
}


async function approvals(req, res)
{
    var student_verification = await Student.find()
        .select({s_name: 1, email: 1})
        .where({$and: [{institute: req.params.i_id}, {isVerified: false}]})
        .catch( (err) => {
            console.log(err)
        })

    var faculty_verification = await Faculty.find()
        .select({f_name: 1, qualification: 1, image: 1, email: 1, phone: 1})
        .where({$and: [{institute: req.params.i_id}, {isVerified: false}]})
        .catch( (err) => {
            console.log(err)
        })

    faculty_verification.forEach( (document) => {
        document.image = f_folder + document.image
    })

    var payment_verification = await Institute.findById(req.params.i_id)
        .select({payment: 1})
        .catch( (err) => {
            console.log(err)
        })
    
    payment_verification = payment_verification.payment
    payment_verification.forEach( (document) => {
        document.screenshot = screenshot_folder + document.screenshot
    })
    
    var approvals = {"student_verification": student_verification, "faculty_verification": faculty_verification, "payment_verification": payment_verification}

    res.json(approvals)
}


module.exports = {register, login, logout, approvals}
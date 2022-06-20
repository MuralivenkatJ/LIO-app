const Institute = require("../3_institute/model")
const Faculty = require("./model")

const ip = "http:\\\\127.0.0.1:3000\\"
const c_folder = ip + "\\course_images\\"

function register(req, res)
{
    var {name, email, password, qualification, phone, institute} = req.body
    var image = req.file.filename

    Institute.findOne({i_name: institute}, (err, document) => {
        if(err)
            console.log(err)
        
        if(document == null)
            document = {_id: undefined}

        Faculty.create({
            f_name: name,
            qualification: qualification,
            image: image,
            email: email,
            password: password,
            phone: phone,
            institute: document._id
        })
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
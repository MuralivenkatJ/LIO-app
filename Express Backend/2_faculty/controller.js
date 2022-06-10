const Institute = require("../3_institute/model")
const Faculty = require("./model")

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


function mycourses(req, res)
{
    

    res.send("This is my courses page(faculty)")
}


module.exports = {register, login, logout, mycourses}
const Institute = require("./model")

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
        console.log(result)
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


function approvals(req, res)
{
    

    res.send("This is my approvals page")
}


module.exports = {register, login, logout, approvals}
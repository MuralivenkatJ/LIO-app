function register(req, res){
    res.send("You are registered as an institute")
}

function login(req, res){
    res.send("You are logged in as an institute")
}

function logout(req, res){
    res.send("You are logged out as an institute")
}


function approvals(req, res){
    res.send("This is my approvals page")
}


module.exports = {register, login, logout, approvals}
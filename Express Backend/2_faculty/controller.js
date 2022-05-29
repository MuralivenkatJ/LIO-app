function register(req, res){
    res.send("You are registered as a faculty")
}

function login(req, res){
    res.send("You are logged in as a faculty")
}

function logout(req, res){
    res.send("You are logged out as a faculty")
}


function mycourses(req, res){
    res.send("This is my courses page(faculty)")
}


module.exports = {register, login, logout, mycourses}
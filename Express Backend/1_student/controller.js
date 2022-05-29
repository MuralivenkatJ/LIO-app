function register(req, res){
    res.send("You are registered as a student")
}

function login(req, res){
    res.send("You are logged in as a student")
}

function logout(req, res){
    res.send("You are logged out as a student")
}


function mycourses(req, res){
    res.send("This is my courses page(student)")
}

function wishlist(req, res){
    res.send("This is wishlist page")
}


module.exports = {register, login, logout, mycourses, wishlist}
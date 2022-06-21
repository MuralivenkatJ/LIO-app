function isStudentLoggedIn(req, res, next)
{
    var answer = true

    if(answer)
        next()
    else
        res.send("You are not logged in")
}

function isFacultyLoggedIn(req, res, next)
{
    var answer = true

    if(answer)
        next()
    else
        res.send("You are not logged in")
}

function isInstituteLoggedIn(req, res, next)
{
    var answer = true

    if(answer)
        next()
    else
        res.send("You are not logged in")
}

module.exports = {isStudentLoggedIn, isFacultyLoggedIn, isInstituteLoggedIn}
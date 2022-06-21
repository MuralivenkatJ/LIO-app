function isStudentVerified(req, res, next)
{
    var answer = true

    if(answer)
        next()
    else
        res.send("You are not yet verified by the institute")
}

function isFacultyVerified(req, res, next)
{
    var answer = true

    if(answer)
        next()
    else
        res.send("You are not yet verified by the institute")
}

module.exports = {isStudentVerified, isFacultyVerified}
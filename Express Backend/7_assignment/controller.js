function assignment(req, res)
{
    res.send("This is assignment page")
}

function upload(req, res)
{
    res.send("This assignment is uploaded")
}

function submit(req, res)
{
    res.send("This assignment is submitted")
}

function grade(req, res)
{
    res.send("This assignment is graded")
}


module.exports = {assignment, upload, submit, grade}
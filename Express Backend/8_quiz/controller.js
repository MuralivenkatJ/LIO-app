function quiz(req, res)
{
    res.send("This is the Quiz page")
}

function upload(req, res)
{
    res.send("This quiz is uploaded")
}

function submit(req, res)
{
    res.send("This quiz is submitted")
}


module.exports = {quiz, upload, submit}
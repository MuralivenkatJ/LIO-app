

function studentVerification(req, res)
{
    

    res.send("This is student verification")
}

function facultyVerification(req, res)
{
    

    res.send("This is faculty verification")
}

function paymentVerification(req, res)
{
    

    res.send("This is payment verification")
}

module.exports = {studentVerification, facultyVerification, paymentVerification}
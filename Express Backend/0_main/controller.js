function explore(req, res)
{
    res.send("This is explore page")
}

function about(req, res)
{
    res.send("This is about page")
}

module.exports = {explore, about}
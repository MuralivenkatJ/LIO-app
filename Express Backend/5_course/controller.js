function unenrolled(req, res)
{
    res.send("This is the course details")
}

function addToWishlist(req, res)
{
    res.send("This course has been added to the wishlist")
}

function removeFromWishlist(req, res)
{
    res.send("This course has been removed from the wishlist")
}

function enroll(req, res)
{
    res.send("This course has been enrolled")
}

function upload(req, res)
{
    res.send("This course has been uploaded")
}



function enrolled(req, res)
{
    res.send("This is the player page")
}

function watched(req, res)
{
    res.send("This will update the video completion")
}


module.exports = {unenrolled, addToWishlist, removeFromWishlist, enroll, upload, enrolled, watched}
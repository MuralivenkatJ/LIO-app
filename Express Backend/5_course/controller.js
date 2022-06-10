const Faculty = require("../2_faculty/model")
const Course = require("./model")

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
    var {id, name, desc, spec, price, level, guided_project, playlist, skills} = req.body
    var image = req.file.filename
    skills = skills.split(",")
    var date = Date.now(), duration = "30:00", n_videos = 40

    Faculty.findById(id, (err, document) => {
        if(err)
            console.log(err)
        else{
            const c = new Course({
                c_name: name,
                image: image,
                description: desc,
                faculty: document._id,
                specialization: spec,
                price: price,
                level: level,
                guided_project: guided_project,
                playlistId: playlist,
                date: date,
                duration: duration,
                no_of_videos: n_videos,
                skills: skills
            })

            c.save( (err, newDoc) => {
                if(err)
                    console.log(err)
                else{
                    document.courses.push(newDoc._id)
                    document.save()
                }
            })
        }
    })

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
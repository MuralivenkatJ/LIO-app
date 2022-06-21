const Student = require("../1_student/model")
const Faculty = require("../2_faculty/model")
const Course = require("./model")
const path = require("path")

// const ip = require("../0_main/app").getIp
const ip = "http:\\\\127.0.0.1:3000\\"

const c_folder = ip + "\\course_images\\"
const f_folder = ip + "\\faculty_images\\"
const i_folder = ip + "\\institute_images\\"


async function unenrolled(req, res)
{
    var course_details = await Course.findById(req.params.c_id)
        .select({assignment_questions: 0, quiz_questions: 0, playlistId: 0})
        .populate({path: "faculty", populate: {path: "institute"}})
        .catch( (err) => {
            console.log(err)
        })
    
    course_details.image = c_folder + course_details.image
    course_details.faculty.image = f_folder + course_details.faculty.image
    course_details.faculty.institute.image = i_folder + course_details.faculty.institute.image

    res.json(course_details)
}

async function addToWishlist(req, res)
{
    var course = await Course.findById(req.params.c_id)
        .catch( (err) => {
            console.log(err)
        })

    var updated = await Student.findByIdAndUpdate(req.params.s_id, 
        {$push: {wishlist: course._id}}, {new: true})
        .catch( (err) => {
            console.log(err)
        })
    
    // console.log(updated)

    res.send("This course has been added to the wishlist")
}

async function removeFromWishlist(req, res)
{
    var course = await Course.findById(req.params.c_id)
        .catch( (err) => {
            console.log(err)
        })

    var updated = await Student.findByIdAndUpdate(req.params.s_id, 
        {$pull: {wishlist: course._id}}, {new: true})
        .catch( (err) => {
            console.log(err)
        })
    
    // console.log(updated)

    res.send("This course has been removed from the wishlist")
}

async function enroll(req, res)
{
    var course = await Course.findById(req.params.c_id)
        .catch( (err) => {
            console.log(err)
        })

    //Checking if the student is already enrolled or not
    var student = await Student.findOne()
        .where({$and: [ {'_id': req.params.s_id}, 
                        {'enrolled.course': course._id}]
                })

    if(student != null)
        return res.send("Already enrolled")

    //enrolling
    var enrolling_course = {"course": course._id}

    var updated = await Student.findByIdAndUpdate(req.params.s_id, 
        {$push: {'enrolled': enrolling_course}}, {new: true})
        .catch( (err) => {
            console.log(err)
        })
    
    // console.log(updated)

    res.send("This course has been enrolled")
}

function upload(req, res)
{
    var {f_id, name, desc, spec, price, level, guided_project, playlist, skills} = req.body
    var image = req.file.filename
    skills = skills.split(",")
    var duration = "30:00", n_videos = 40

    Faculty.findById(f_id, (err, document) => {
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
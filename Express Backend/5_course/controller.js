const Student = require("../1_student/model")
const Faculty = require("../2_faculty/model")
const Course = require("./model")
const path = require("path")

// const ip = require("../0_main/app").getIp
const ip = "http:\\\\127.0.0.1:3000\\"

const s_folder = ip + "\\student_images\\"
const c_folder = ip + "\\course_images\\"
const f_folder = ip + "\\faculty_images\\"
const i_folder = ip + "\\institute_images\\"

//EMAIL
const nodemailer = require("nodemailer")

const transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
        user: "liolearnitonline@gmail.com",
        pass: "bjsmzsktvkkaqkgn"
    }
})

async function unenrolled(req, res)
{
    var course_details = await Course.findById(req.params.c_id)
        .select({assignment_questions: 0, quiz_questions: 0, playlistId: 0})
        .populate({path: "faculty", 
                        select: {password: 0, isVerified: 0, courses: 0}, populate: {path: "institute", 
                            select: {payment_details: 0, password: 0, payment: 0}}})
        .populate({path: "reviews.student", select: {s_name: 1, c_name: 1, image: 1, email: 1}})
        .catch( (err) => {
            console.log(err)
        })
    
    course_details.image = c_folder + course_details.image
    course_details.faculty.image = f_folder + course_details.faculty.image
    course_details.faculty.institute.image = i_folder + course_details.faculty.institute.image
    course_details.reviews.forEach( (review) => {
        review.student.image = s_folder + review.student.image
    })

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
    //enrolling
    var enrolling_course = {"course": req.params.c_id}

    var updated = await Student.findByIdAndUpdate(req.params.s_id, 
        {$push: {'enrolled': enrolling_course}}, {new: true})
        .catch( (err) => {
            console.log(err)
        })
    
    if(updated)
    {
        //send mail
        var s = await Student.findById(req.params.s_id)
                .select({s_name: 1, email: 1})

        var c = await Course.findById(req.params.c_id)
                .select({c_name: 1})

        var mailoptions = {
            from: "liolearnitonline@gmail.com",
            to: s.email,
            subject: `Enrolled for the course ${c.c_name}`,
            text: `Hello ${s.s_name}, \n\n\tCongratulations, You are enrolled for ${c.c_name}. Happy learning. \n\nBest Wishes, \nTeam LIO`
        }

        transporter.sendMail(mailoptions, (err, info) => {
            if(err)
                console.log(err)
            // else
            //     console.log(info)
        })
    }

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

                    //send mail
                    var mailoptions = {
                        from: "liolearnitonline@gmail.com",
                        to: document.email,
                        subject: "Course Uploaded",
                        text: `Hello ${document.f_name}, \n\n\tYour course ${newDoc.c_name} has been uploaded to our website. The details of the course ${newDoc.c_nae} are as follows : \n\nTitle: ${newDoc.c_name} \nSpecialization: ${newDoc.specialization} \nLevel: ${newDoc.level} \nPlaylist Id: ${newDoc.playlistId} \nDescription: ${newDoc.description} \nPrice: ${newDoc.price} \n\nBest Wishes, \nTeam LIO`
                    }

                    transporter.sendMail(mailoptions, (err, info) => {
                        if(err)
                            console.log(err)
                        // else
                        //     console.log(info)
                    })
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
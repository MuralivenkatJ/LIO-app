const Course = require("../5_course/model")
const Student = require("../1_student/model")
const Faculty = require("../2_faculty/model")
const Institute = require("../3_institute/model")

const c_v = require("../helpers/course_verification")
const course_controller = require("../5_course/controller")

const fs = require("fs")
const path = require("path")

//EMAIL
const nodemailer = require("nodemailer")

const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: "liolearnitonline@gmail.com",
        pass: "bjsmzsktvkkaqkgn"
    }
})

function studentVerification(req, res)
{
    var s = Student.findByIdAndUpdate(req.params.s_id, {isVerified: true}, {new: true}, async (err, document) => {
        if(!document)
            console.log(err)
        else
        {
            var i = await Institute.findById(document.institute)

            //send email
            var mailoptions = {
                from: "liolearnitonline@gmail.com",
                to: document.email,
                subject: "Institute Verification",
                text: `Hello ${document.s_name}, \n\n\tYou are verified by your institution ${i.i_name}. Now you can enroll all the courses of ${i.i_name} for free. \n\nBest Wishes, \nTeam LIO`
            }

            transporter.sendMail(mailoptions, (err, info) => {
                if(err)
                    console.log(err)
                // else
                //     console.log(info)
            })
        }
    })
            

    res.send("This student is verified")
}

async function facultyVerification(req, res)
{
    var f = await Faculty.findByIdAndUpdate(req.params.f_id, {isVerified: true}, {new: true})

    if(f)
    {
        var i = await Institute.findById(f.institute)
                .select({i_name: 1})

        //send mail
        var mailoptions = {
            from: "liolearnitonline@gmail.com",
            to: f.email,
            subject: "Institute Verification",
            text: `Hello ${f.f_name}, \n\n\tYou are verified by your institution ${i.i_name}. Now you can upload the courses. \n\nBest Wishes, \nTeam LIO`
        }

        transporter.sendMail(mailoptions, (err, info) => {
            if(err)
                console.log(err)
            // else
            //     console.log(info)
        })
    }

    res.send("This faculty is verified")
}

async function paymentVerification(req, res)
{
    if( await c_v.isEnrolled(req.params.s_id, req.params.c_id) )
        return res.send("This student is already enrolled for this course")
    
    //get the institute id
    var course = await Course.findById(req.params.c_id)
        .select({faculty: 1})
        .populate({path: "faculty", select: {institute: 1}})
        .catch( (err) => {
            console.log(err)
        })

    //get the screenshot image name
    var i = await Institute.findById(course.faculty.institute)
        .select({"payment.screenshot": 1})
        .where({$and: [{student: req.params.s_id}, {course: req.params.c_id}]})
        .catch( (err) => {
            console.log(err)
        })
    
    //get the path of the image
    var file_path = path.dirname(__dirname) + "\\public\\payment_screenshots\\" + i.payment[0].screenshot

    //delete the image
    fs.unlink(file_path, (err) => {
        if(err)
            console.log(err)
        // console.log("File is deleted")
    })
    
    //pull the object from the array in institute collection
    var i = await Institute.findByIdAndUpdate(course.faculty.institute, 
        {$pull: {payment: 
                        {$and: [
                                {student: req.params.s_id}, 
                                {course: req.params.c_id}
                            ]
                        }
                }
        },
        {new: false})

    //enroll the course
    return course_controller.enroll(req, res)

    res.send("This payment is verified")
}

module.exports = {studentVerification, facultyVerification, paymentVerification}
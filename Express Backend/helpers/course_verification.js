const Course = require("../5_course/model")
const Faculty = require("../2_faculty/model")
const Student = require("../1_student/model")
const Institute = require("../3_institute/model")

async function isEnrolled(s_id, c_id)
{
    var student = await Student.findById(s_id)
        .select({})
        .where({"enrolled.course": c_id})
        .catch( (err) => {
            console.log(err)
        })

    if(student != null)
        return true
    return false
}

function isUploaded(f_id, c_id)
{
    
}

async function isTheCourseFree(req, res, next)
{
    //Check if the student has already enrolled for this course
    if( await isEnrolled(req.params.s_id, req.params.c_id) )
        return res.send("You are already enrolled for this course")

    //institute of the course
    var course = await Course.findById(req.params.c_id)
        .select({"faculty": 1, c_name: 1, price: 1})
        .populate({path: "faculty", select: "institute"})
        .catch( (err) => {
            console.log(err)
        })

    var i_id = course.faculty.institute

    //institute of the student
    var student = await Student.findById(req.params.s_id)
        .select({enrolled: 0, wishlist: 0, image: 0, password: 0})
        .catch( (err) => {
            console.log(err)
        })
    
    //If they are same then we are going to enroll the course for free
    if(student.institute == i_id  &&  student.isVerified == true)
        return next()
    
    //else
    //Now we have to send the payment details to the student
    var institute = await Institute.findById(i_id)
        .select({payment_details: 1})
        .catch( (err) => {
            console.log(err)
        })
    
    
    return res.json({"course": course, "institute": institute})
}

module.exports = {isEnrolled, isUploaded, isTheCourseFree}
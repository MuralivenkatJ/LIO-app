const Student = require("../1_student/model")
const Course = require("../5_course/model")
const AssignmentQuestion = require("./model").AssignmentQuestion
const SubmittedAssignment = require("./model").SubmittedAssignment

async function assignment(req, res)
{
    var assignments = await Course.findById(req.params.c_id)
        .select({assignment_questions: 1})
        .populate({path: "assignment_questions"})
        .catch( (err) => {
            console.log(err)
        })

    res.json(assignments)
}

async function upload(req, res)
{
    var {c_id, pos, question} = req.body

    var course = await Course.findById(c_id)

    var assignment = new AssignmentQuestion({
        course: course._id,
        position: pos,
        question: question
    })

    assignment.save( (err, result) => {
        if(err)
            console.log(err)
        
        // console.log(result)
    })

    var updated = await Course.findByIdAndUpdate(c_id, 
        {$push: {assignment_questions: assignment._id}}, {new: true})
        .catch( (err) => {
            console.log(err)
        })
    

    res.send("This assignment is uploaded")
}

async function submit(req, res)
{
    var {s_id, a_id} = req.body
    var assignment = req.file.filename

    var s = await Student.findById(s_id)
    var a_q = await AssignmentQuestion.findById(a_id)

    var isEnrolled = s.enrolled.some(function (course_object) {
        return course_object.course.equals(a_q.course)
    })

    if( !isEnrolled )
        return res.send("You are not enrolled for this course")

    var a_ans = new SubmittedAssignment({
        student: s._id,
        question: a_q._id,
        assignment: assignment        
    })

    a_ans.save( (err, result) => {
        if(err)
            console.log(err)
        
        // console.log(result)
    })

    res.send("This assignment is submitted")
}

function grade(req, res)
{
    res.send("This assignment is graded")
}


module.exports = {assignment, upload, submit, grade}
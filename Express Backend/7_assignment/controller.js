const Student = require("../1_student/model")
const Course = require("../5_course/model")
const AssignmentQuestion = require("./model").AssignmentQuestion
const SubmittedAssignment = require("./model").SubmittedAssignment

const fs = require("fs")
const path = require("path")

const ip = "http:\\\\127.0.0.1:3000\\"
const a_folder = ip + "\\assignment_files\\"

//Student view
async function questions(req, res)
{
    var assignments = await Course.findById(req.params.c_id)
        .select({assignment_questions: 1})
        .populate({path: "assignment_questions"})
        .catch( (err) => {
            console.log(err)
        })

    res.json(assignments)
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

//Faculty view
async function answers(req, res)
{
    var answers = await SubmittedAssignment.find()
        .select({})
        .where({question: req.params.a_id})

    answers.forEach( (document) => {
        document.assignment = a_folder + document.assignment
    })

    res.json(answers)
}

async function grade(req, res)
{
    var {s_id, aq_id, marks} = req.body

    var assignment_q = await AssignmentQuestion.findById(aq_id)
    if(marks > assignment_q.max_marks)
        return res.send("Marks cannot be greater than maximum marks")

    //updating the marks in the student collection
    var assignment_marks = {"assignment_question": assignment_q._id, 
                            "marks": marks}
    
    await Student.updateOne({'_id': s_id, 'enrolled.course': assignment_q.course},
            {"$push": {"enrolled.$.assignment_marks": assignment_marks}})

    
    //deleting the file and document in SubmittedAssignment
    var assignment_a = await SubmittedAssignment.findOne({'student': s_id, 'question': aq_id})

    var file_path = path.dirname(__dirname) + "\\public\\assignment_files\\" +  assignment_a.assignment

    fs.unlink(file_path, (err) => {
        if(err)
            console.log(err)
        // console.log("File is deleted")
    })

    SubmittedAssignment.findByIdAndDelete(assignment_a._id)
        .catch( (err) => {
            console.log(err)
        })

    res.send("This assignment is graded")
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




module.exports = {questions, answers, upload, submit, grade}
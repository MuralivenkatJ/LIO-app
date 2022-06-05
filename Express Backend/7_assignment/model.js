const mongoose = require("mongoose")
const Schema = mongoose.Schema

const Course = require("../5_course/model")
const student = require("../1_student/model")

const AssignmentQuestionSchema = new Schema(
    {
        course: {
            type: Schema.Types.ObjectId,
            ref: Course
        },
        position: Number,
        question: String,
        date: Date.now
    }
)


var AssignmentQuestion = mongoose.model("AssignmentQuestion", AssignmentQuestionSchema)




const SubmittedAssignmentSchema = new Schema(
    {
        student: {
            type: Schema.Types.ObjectId,
            ref: Student
        },
        question: {
            type: Schema.Types.ObjectId,
            ref: AssignmentQuestion
        },
        assignment: String,
        date: Date.now
    }
)

var SubmittedAssignment = mongoose.model("SubmittedAssignment", SubmittedAssignmentSchema)

module.exports = {AssignmentQuestion, SubmittedAssignment}
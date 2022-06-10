const mongoose = require("mongoose")
const Schema = mongoose.Schema


const AssignmentQuestionSchema = new Schema(
    {
        course: {
            type: Schema.Types.ObjectId,
            ref: 'Course'
        },
        position: Number,
        question: String,
        date: Date
    }
)


var AssignmentQuestion = mongoose.model("AssignmentQuestion", AssignmentQuestionSchema)




const SubmittedAssignmentSchema = new Schema(
    {
        student: {
            type: Schema.Types.ObjectId,
            ref: 'Student'
        },
        question: {
            type: Schema.Types.ObjectId,
            ref: 'AssignmentQuestion'
        },
        assignment: String,
        date: Date
    }
)

var SubmittedAssignment = mongoose.model("SubmittedAssignment", SubmittedAssignmentSchema)

module.exports = {AssignmentQuestion, SubmittedAssignment}
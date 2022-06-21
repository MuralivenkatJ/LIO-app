const mongoose = require("mongoose")
const Schema = mongoose.Schema


const AssignmentQuestionSchema = new Schema(
    {
        course: {
            type: Schema.Types.ObjectId,
            ref: 'Course'
        },
        position: Number,
        question: {
            type: String,
            required: true
        },
        max_marks: {
            type: Number,
            default: 10
        },
        date: {
            type: Date,
            default: Date.now
        }
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
        date: {
            type: Date,
            default: Date.now
        }
    }
)

var SubmittedAssignment = mongoose.model("SubmittedAssignment", SubmittedAssignmentSchema)

module.exports = {AssignmentQuestion, SubmittedAssignment}
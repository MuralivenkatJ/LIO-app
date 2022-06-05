const mongoose = require('mongoose')
const Schema = mongoose.Schema


const Institute = require("../3_institute/model")
const Course    = require("../5_course/model")
const QuizQuestion = require("../8_quiz/model")
const AssignmentQuestion = require("../7_assignment/model").AssignmentQuestion

const StudentSchema = new Schema(
    {
        s_name: {
            type: String,
            required: true
        },
        email: {
            type: String,
            required: true,
            unique: true
        },
        password: {
            type: String,
            required: true
        },
        isVerified: {
            type: Boolean,
            default: false
        },
        image: String,
        institute: {
            type: Schema.Types.ObjectId,
            ref: Institute
        },

        enrolled: [
            {
                course: {
                    type: Schema.Types.ObjectId,
                    ref: Course
                },
                date: Date,
                status: {
                    type: String,
                    default: 'inprogress'
                },
                watched: [{type: Number}],
                quiz_marks: [
                    {
                        quiz_questions: {
                            type: Schema.Types.ObjectId,
                            ref: QuizQuestion
                        },
                        marks: Number
                    }
                ],
                assignment_marks: [
                    {
                        assignment_question: {
                            type: Schema.Types.ObjectId,
                            ref: AssignmentQuestion
                        },
                        marks: Number
                    }
                ]
            }
        ],

        wishlist: [
            {
                type: Schema.Types.ObjectId,
                ref: Course
            }
        ]
    }
)


var Student = mongoose.model('Student', StudentSchema)

module.exports = Student
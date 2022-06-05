const mongoose = require('mongoose')
const Schema = mongoose.Schema

const Faculty = require("../2_faculty/model")
const Student = require("../1_student/model")
const AssignmentQuestion = require("../7_assignment/model").AssignmentQuestion
const QuizQuestion = require("../8_quiz/model")

const CourseSchema = new Schema(
    {
        c_name: {
            type: String,
            required: true
        },
        image: {
            type: String,
            required: true
        },
        description: {
            type: String,
            required: true
        },
        faculty: {
            type: Schema.Types.ObjectId,
            ref: Faculty
        },
        specialization: {
            type: String,
            required: true
        },
        price: {
            type: Number,
            required: true
        },
        level: {
            type: String,
            required: true
        },
        guided_project: {
            type: Boolean,
            default: false
        },
        playlistId: {
            type: String,
            required: true
        },
        date: {
            type: Date,
            default: Date.now
        },
        duration: {
            type: Number,
            required: true
        },
        no_of_videos: {
            type: Number,
            required: true
        },
        views: {
            type: Number,
            default: 0
        },

        skills: [String],

        reviews: [
            {
                student: {
                    type: Schema.Types.ObjectId,
                    ref: Student
                }, 
                rate: Number, 
                desc: String, 
                date: Date.now
            }
        ],

        assignment_questions: [
            {
                type: Schema.Types.ObjectId,
                ref: AssignmentQuestion
            }
        ],

        quiz_questions: [
            {
                type: Schema.Types.ObjectId,
                ref: QuizQuestion
            }
        ]
    }
)


var Course = mongoose.model('Course', CourseSchema)

module.exports = Course
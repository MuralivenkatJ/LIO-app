const mongoose = require('mongoose')
const Schema = mongoose.Schema

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
            ref: 'Faculty'
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
            type: String,
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
        rating: {
            type: Number,
            default: 0.0
        },

        skills: [String],

        reviews: [
            {
                student: {
                    type: Schema.Types.ObjectId,
                    ref: 'Student'
                }, 
                rate: Number, 
                desc: String, 
                date: {
                    type: Date,
                    default: Date.now
                }
            }
        ],

        assignment_questions: [
            {
                type: Schema.Types.ObjectId,
                ref: 'AssignmentQuestion'
            }
        ],

        quiz_questions: [
            {
                type: Schema.Types.ObjectId,
                ref: 'QuizQuestion'
            }
        ]
    }
)


var Course = mongoose.model('Course', CourseSchema)

module.exports = Course
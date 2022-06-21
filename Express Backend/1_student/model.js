const mongoose = require('mongoose')
const Schema = mongoose.Schema


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
        image: {
            type: String,
            default: 'default.jpg'
        },
        institute: {
            type: Schema.Types.ObjectId,
            ref: 'Institute',
            required: false
        },

        enrolled: [
            {
                course: {
                    type: Schema.Types.ObjectId,
                    ref: 'Course'
                },
                date: {
                    type: Date,
                    default: Date.now
                },
                status: {
                    type: String,
                    default: 'inprogress'
                },
                watched: [{type: Number}],
                quiz_marks: [
                    {
                        quiz_questions: {
                            type: Schema.Types.ObjectId,
                            ref: 'QuizQuestion'
                        },
                        marks: Number
                    }
                ],
                assignment_marks: [
                    {
                        assignment_question: {
                            type: Schema.Types.ObjectId,
                            ref: 'AssignmentQuestion'
                        },
                        marks: Number
                    }
                ]
            }
        ],

        wishlist: [
            {
                type: Schema.Types.ObjectId,
                ref: 'Course'
            }
        ]
    }
)


var Student = mongoose.model('Student', StudentSchema)

module.exports = Student
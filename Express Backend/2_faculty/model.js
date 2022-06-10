const mongoose = require('mongoose')
const Schema = mongoose.Schema


const FacultySchema = new Schema(
    {
        f_name: {
            type: String,
            required: true
        },
        qualification: {
            type: String,
            required: true
        },
        image: {
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
        phone: {
            type: String,
            required: true
        },
        isVerified: {
            type: Boolean,
            default: false
        },
        institute: {
            type: Schema.Types.ObjectId,
            ref: 'Institute'
        },

        courses: [
            {
                type: Schema.Types.ObjectId,
                ref: 'Course'
            }
        ]
    }
)


var Faculty = mongoose.model('Faculty', FacultySchema)

module.exports = Faculty
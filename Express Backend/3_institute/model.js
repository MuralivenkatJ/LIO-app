const mongoose = require('mongoose')
const Schema = mongoose.Schema


const InstituteSchema = new Schema(
    {
        i_name: {
            type: String,
            required: true,
            unique: true
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
        image: {
            type: String,
            required: true
        },
        website: {
            type: String,
            required: true
        },
        payment_details: {
            ac_name: {
                type: String,
                required: true
            },
            ac_no: {
                type: String,
                required: true
            },
            ifsc: {
                type: String,
                required: true
            }
        },
        

        payment: [
            {
                student: {
                    type: Schema.Types.ObjectId,
                    ref: 'Student'
                },
                course: {
                    type: Schema.Types.ObjectId,
                    ref: 'Course'
                },
                utrid: String,
                screenshot: String,
                date: {
                    type: Date,
                    default: Date.now
                }
            }
        ]
    }
)


var Institute = mongoose.model('Institute', InstituteSchema)

module.exports = Institute
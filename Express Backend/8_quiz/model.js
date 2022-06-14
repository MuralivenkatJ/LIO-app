const mongoose = require("mongoose")
const Schema = mongoose.Schema


const QuizQuestionSchema = new Schema(
    {
        course: {
            type: Schema.Types.ObjectId,
            ref: 'Course'
        },
        position: Number,
        questionslist: [
            {  
                question: String,
                options: [String],
                answer: String
            }
        ],
        date: {
            type: Date,
            default: Date.now
        }
    }
)


var QuizQuestion = mongoose.model("QuizQuestion", QuizQuestionSchema)


module.exports = QuizQuestion
const QuizQuestion = require("./model")
const Course = require("../5_course/model")
const Student = require("../1_student/model")

//student view
async function quiz(req, res)
{
    var quizes = await Course.findById(req.params.c_id)
        .select({c_name: 1, quiz_questions: 1})
        .populate({path: "quiz_questions"})
        .catch( (err) => {
            console.log(err)
        })

    res.json(quizes)
}

async function quizQuestions(req, res)
{
    var questions = await QuizQuestion.findById(req.params.q_id)
        .select({"questionslist.answer": 0, "questionslist._id": 0})
        .catch( (err) => {
            if(err)
                console.log(err)
        })

    console.log(questions)

    res.json(questions)
}

async function submit(req, res)
{
    var {s_id, q_id, answers} = req.body
    
    var correct_answers = await QuizQuestion.findById(q_id)
        .select({"questionslist.answer": 1, _id: 0, course: 1})
        .catch( (err) => {
            console.log(err)
        })
    
    var marks = 0, max_marks = answers.length
    for(let i=0; i<answers.length; i++)
    {
        if(answers[i] == correct_answers.questionslist[i].answer)
            marks++
    }

    console.log(marks + " out of " + max_marks)
    
    var quiz_marks = {"quiz_questions": q_id, "marks": marks}
    var s = await Student.findOneAndUpdate(
        {"_id": s_id, "enrolled.course": correct_answers.course}, 
        {"$push": {"enrolled.$.quiz_marks": quiz_marks}}, 
        {new: true})
    
    // console.log(s)

    res.send("This quiz is submitted. You got " + marks.toString() + " correct answers out of " + max_marks.toString() + ".")
}

//faculty view
async function upload(req, res)
{
    var obj = req.body

    var questionlist = []
    obj.questionlist.forEach( (question) => {
        questionlist.push(question)
    })


    var quiz = new QuizQuestion({
        course: obj.course,
        position: obj.position,
        questionslist: JSON.parse(JSON.stringify(questionlist))
    })

    quiz.save( (err, result) => {
        if(err)
            console.log(err)
        // console.log(result)
    })

    var c = await Course.findByIdAndUpdate(obj.course, 
            {$push: {'quiz_questions': quiz._id}}, {new: true})


    res.send("The Quiz has been uploaded")
}


module.exports = {quiz, quizQuestions, upload, submit}
const express = require("express")
const bodyParser = require("body-parser")

//creating the router
const router = express.Router()
router.use(bodyParser.json())

//importing the controller
const controller = require("./controller")


//URLs or ROUTEs
router.get("/:c_id", controller.quiz)
router.get("/questions/:q_id", controller.quizQuestions)
router.post("/upload/", controller.upload)           //POST
router.post("/submit/", controller.submit)


//exporting the router
module.exports = router
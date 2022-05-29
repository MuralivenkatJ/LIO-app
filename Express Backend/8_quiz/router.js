const express = require("express")

//creating the router
const router = express.Router()

//importing the controller
const controller = require("./controller")


//URLs or ROUTEs
router.get("/", controller.quiz)
router.get("/upload/", controller.upload)           //POST
router.get("/submit/", controller.submit)


//exporting the router
module.exports = router
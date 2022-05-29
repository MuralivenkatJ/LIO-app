const express = require("express")

//creating the router
const router = express.Router()

//importing the controller
const controller = require("./controller")


// URLs or ROUTEs
router.get("/", controller.assignment)
router.get("/upload/", controller.upload)
router.get("/submit/", controller.submit)
router.get("/grade/", controller.grade)


//exporting the router
module.exports = router
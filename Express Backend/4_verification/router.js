const express = require("express")

//creating the router
const router = express.Router()

//IMPORTing the controllers
const controller = require("./controller")

//URLs or ROUTEs
router.get("/student/", controller.studentVerification)
router.get("/faculty/", controller.facultyVerification)
router.get("/payment/", controller.paymentVerification)


//EXPORTing the router
module.exports = router
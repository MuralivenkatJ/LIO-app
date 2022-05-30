const express = require("express")

//creating the router
const router = express.Router()

//IMPORTing the controllers
const controller = require("./controller")

//URLs or ROUTEs
router.get("/student/:s_id", controller.studentVerification)
router.get("/faculty/:f_id", controller.facultyVerification)
router.get("/payment/:s_id/:c_id", controller.paymentVerification)


//EXPORTing the router
module.exports = router
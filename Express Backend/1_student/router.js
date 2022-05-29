const express = require("express")


//creating the router
const router = express.Router()

//importing the controller
const controller = require("./controller")


//URLs or ROUTEs
router.get("/Register/", controller.register)           // All these
router.get("/login/", controller.login)                 // should be
router.get("/logout/", controller.logout)               // POST method

router.get("/mycourses/", controller.mycourses)
router.get("/wishlist/", controller.wishlist)



//Exporting router
module.exports = router
const express = require("express")


//creating the router
const router = express.Router()

//importing the controller
const controller = require("./controller")


//URLs or ROUTEs
router.get("/unenrolled/", controller.unenrolled)
router.get("/addToWishlist/", controller.addToWishlist)
router.get("/removeFromWishlist/", controller.removeFromWishlist)
router.get("/enroll/", controller.enroll)
router.get("/upload/", controller.upload)                           //POST
router.get("/enrolled/", controller.enrolled)
router.get("/watched/", controller.watched)


//exporting the router
module.exports = router
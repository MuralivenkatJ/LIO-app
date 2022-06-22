const express = require("express")
const multer = require("multer")
const path = require("path")


//creating the router
const router = express.Router()

//importing the controller
const controller = require("./controller")


//IMAGE UPLOADING 
const destinationAndFilename = multer.diskStorage({
    destination: "public/student_images/",
    filename: (req, file, cb) => {
        cb(null, "student_" + Date.now() + path.extname(file.originalname))
    }
})

const uploadImage = multer({storage: destinationAndFilename}).single("image")


//PAYMENT SCREENSHOT UPLOADING
const destinationAndFilename2 = multer.diskStorage({
    destination: "public/payment_screenshots/",
    filename: (req, file, cb) => {
        cb(null, "screenshot_" + Date.now() + path.extname(file.originalname))
    }
})

const uploadScreenshot = multer({storage: destinationAndFilename2}).single("screenshot")


//URLs or ROUTEs
router.post("/register/", uploadImage, controller.register)           // All these
router.get("/login/", controller.login)                 // should be
router.get("/logout/", controller.logout)               // POST method

router.post("/payment/", uploadScreenshot, controller.payment)
router.post("/review/", controller.review)

router.get("/mycourses/:s_id", controller.mycourses)
router.get("/wishlist/:s_id", controller.wishlist)



//Exporting router
module.exports = router
const express = require("express")
const multer = require("multer")
const path = require("path")


//creating the router
const router = express.Router()

//importing the controller
const controller = require("./controller")


//TO UPLOAD IMAGE
const destinationAndFilename = multer.diskStorage({
    destination: "public/course_images",
    filename: (req, file, cb) => {
        cb(null, "course_" + Date.now() + path.extname(file.originalname))
    }
})

const uploadImage = multer({storage: destinationAndFilename}).single("image")



//URLs or ROUTEs
router.get("/unenrolled/:c_id/", controller.unenrolled)
router.get("/addToWishlist/:s_id/:c_id/", controller.addToWishlist)
router.get("/removeFromWishlist/:s_id/:c_id/", controller.removeFromWishlist)
router.get("/enroll/:s_id/:c_id/", controller.enroll)
router.post("/upload/", uploadImage, controller.upload)                   //POST
router.get("/enrolled/", controller.enrolled)
router.get("/watched/", controller.watched)


//exporting the router
module.exports = router
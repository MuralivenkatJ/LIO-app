const express = require("express")
const multer = require("multer")
const path = require("path")


//creating the router
const router = express.Router()

//importing the controller
const controller = require("./controller")


//IMAGE UPLOADING
const destinationAndFilename = multer.diskStorage({
    destination: "public/institute_images/",
    filename: (req, file, cb) => {
        cb(null, "institute_" + Date.now() + path.extname(file.originalname))
    }
})

const uploadImage = multer({storage: destinationAndFilename}).single("image")



//URLs or ROUTEs
router.post("/register/", uploadImage, controller.register)           // All these
router.get("/login/", controller.login)                 // should be
router.get("/logout/", controller.logout)               // POST method

router.get("/approvals/:i_id", controller.approvals)



//Exporting router
module.exports = router
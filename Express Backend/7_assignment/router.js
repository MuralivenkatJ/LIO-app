const express = require("express")
const multer = require("multer")
const path = require("path")

//creating the router
const router = express.Router()

//importing the controller
const controller = require("./controller")


//TO UPLOAD FILES
const destinationAndFilename = multer.diskStorage({
    destination: "public/assignment_files",
    filename: (req, file, cb) => {
        cb(null, "assignment_" + Date.now() + path.extname(file.originalname))
    }
})

const uploadFile = multer({storage: destinationAndFilename}).single("assignment")


// URLs or ROUTEs
router.get("/questions/:c_id", controller.questions)
router.get("/answers/:a_id", controller.answers)
router.post("/grade/", controller.grade)
router.post("/upload/", controller.upload)
router.post("/submit/", uploadFile, controller.submit)


//exporting the router
module.exports = router
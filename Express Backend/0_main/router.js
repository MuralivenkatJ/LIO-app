const express = require("express")


//creating the router
const router = express.Router()


//importing the controllers
const controller = require("./controller")


// URLs or ROUTEs
router.get("/", controller.explore)
router.get("/about/", controller.about)




//EXPORTing the ROUTER
module.exports = router
const express = require("express")
const { append } = require("express/lib/response")
const bodyParser = require("body-parser")
const path = require("path")


//Main router which receives all the URLs and distributes
const app = express()

//This is required for extracting data from req(when http methode is POST)
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))

//making public folder as static
app.use(express.static(path.dirname(__dirname) + '\\public\\'))


//Connecting to the database
const mongoose = require("mongoose")
const url = "mongodb://localhost/LearnItOnline"

mongoose.connect(url, {useNewUrlParser: true})

const con = mongoose.connection
con.on("connected", ()=>{
    console.log("connected to MongoDB...")
})





//IMPORTing all the ROUTERS
const router_0 = require("./router")
const router_1 = require("../1_student/router")
const router_2 = require("../2_faculty/router")
const router_3 = require("../3_institute/router")
const router_4 = require("../4_verification/router")
const router_5 = require("../5_course/router")
const router_6 = require("../6_playlist_videos/router")
const router_7 = require("../7_assignment/router")
const router_8 = require("../8_quiz/router")


//DISTRIBUTing all the routes
app.use("/", router_0)
app.use("/student/", router_1)
app.use("/faculty/", router_2)
app.use("/institute/", router_3)
app.use("/verification/", router_4)
app.use("/course/", router_5)
app.use("/playlist/", router_6)
app.use("/assignment/", router_7)
app.use("/quiz/", router_8)







//Starting the server
module.exports.getIp = "http:\\\\127.0.0.1:3000\\"
// function abc() {
//     return {"ip": "http:\\\\127.0.0.1:3000\\"}
// }

app.listen(3000, () => {
    console.log("The server is running.....")
})

// console.log(module.exports.getIp)
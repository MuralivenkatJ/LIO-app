const Course = require("../5_course/model")

const ip = "http:\\\\127.0.0.1:3000\\"

const c_folder = ip + "\\course_images\\"

async function explore(req, res)
{
    // var result = await Course.find( {$and:  [ {faculty: "62a20f033b3689d6da36d14b"}, {price: {$gte: 1500}} ] } )
    //     .select({c_name: 1, price: 1, level: 1})
    //     .sort({price: 1, c_name: -1})
    //     .limit(10)
    //     .countDocuments()
    //     .then( (courses) => {
    //         console.log(courses)
    //     })
    //     .catch( (err) => {
    //         console.log(err)
    //     })
    
    var specialization = await Course.find({})
        .select({specialization: 1})
        .distinct("specialization")         //limit cannot be used with distinct
        .catch( (err) => {
            console.log(err)
        })
    
    var most_viewed = await Course.find({})
        .select({})
        .sort({views: -1})
        .limit(10)
        .catch( (err) => {
            console.log(err)
        })

    var recently_launched = await Course.find({})
        .select({})
        .sort({date: -1})
        .limit(10)
        .catch( (err) => {
            console.log(err)
        })

    var guided_project = await Course.find({})
        .select({})
        .where({guided_project: true})
        .limit(10)
        .catch( (err) => {
            console.log(err)
        })

    most_viewed.forEach( (document) => {
        document.image = c_folder + document.image
    })
    recently_launched.forEach( (document) => {
        document.image = c_folder + document.image
    })
    guided_project.forEach( (document) => {
        document.image = c_folder + document.image
    })

    var explore = {"specialization": specialization, "most_viewed": most_viewed, "recently_launched": recently_launched, "guided_project": guided_project}

    res.json(explore)
}

function about(req, res)
{
    res.send("This is about page")
}

module.exports = {explore, about}
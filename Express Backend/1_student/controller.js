// const con = require("../0_main/mysql")

function register(req, res)
{
    var output;

    // con.connect( function(err){
    //     if(err)
    //         console.log("Error while connecting to mySQL in student")   //throw err

    //     let sql = "SELECT * FROM student_student"

    //     con.query(sql, function(err, result){
    //         if(err)
    //             console.log("Error while executing query in student")   //throw err

    //         output = result
    //     })
    // })

    res.send("The student is registered")
}

function login(req, res)
{
    res.send("You are logged in as a student")
}

function logout(req, res)
{
    res.send("You are logged out as a student")
}


function mycourses(req, res)
{
    var enrolled_courses = []
    var s_id = 6

    // con.connect( function(err){
    //     if(err)
    //         console.log("Error while connecting to mySQL in student")   //throw err
        
    //     let sql = `SELECT *
    //                FROM course_course C
    //                JOIN student_enrolls E on E.c_id_id = C.c_id
    //                WHERE E.s_id_id = ${s_id};`

    //     con.query(sql, function(err, result){
    //         if(err)
    //             console.log("Error while executing query in student")   //throw err

    //         // Object.keys(result).forEach(
    //         //     function(key){
    //         //         let row = result[key]

    //         //         //PRINTING THE RESULT
    //         //         Object.keys(row).forEach(
    //         //             function(key){
    //         //                 console.log(key + " : " + row[key])
    //         //             }
    //         //         )
    //         //         console.log("\n\n\n")
    //         //     }
    //         // )
            
    //         enrolled_courses = result
    //     })
    // })


    // res.json(enrolled_courses)
    res.send("This is my course page")
}

function wishlist(req, res)
{
    var wishlist = []
    var s_id = 5
    
    // con.connect( function(err){
    //     if(err)
    //         console.log("Error while connecting to mySQL in student")   //throw err
        
    //     let sql = `SELECT *
    //                FROM course_course C
    //                JOIN student_wishlist W on W.c_id_id = C.c_id
    //                WHERE W.s_id_id = ${s_id};`

    //     con.query(sql, function(err, result){
    //         if(err)
    //             console.log("Error while executing query in student")   //throw err

    //         Object.keys(result).forEach(
    //             function(key){
    //                 let row = result[key]
    //                 wishlist[key] = row

    //                 //PRINTING THE RESULT
    //                 Object.keys(row).forEach(
    //                     function(key){
    //                         console.log(key + " : " + row[key])
    //                     }
    //                 )
    //                 console.log("\n\n\n")
    //             }
    //         )
    //     })
    // })

    res.send("This is wishlist page")
}


module.exports = {register, login, logout, mycourses, wishlist}
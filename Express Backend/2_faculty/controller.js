const con = require("../0_main/mysql")

function register(req, res)
{
    res.send("You are registered as a faculty")
}

function login(req, res)
{
    res.send("You are logged in as a faculty")
}

function logout(req, res)
{
    res.send("You are logged out as a faculty")
}


function mycourses(req, res)
{
    var uploaded_courses = []
    var f_id = 6

    con.connect( function(err){
        if(err)
            console.log("Error while connecting to mySQL in faculty")   //throw err

        let sql = `SELECT *
                   FROM course_course C
                   WHERE C.f_id_id = ${f_id};`
        
        con.query(sql, function(err, result){
            if(err)
                console.log("Error while executing query in faculty")   //throw err

            Object.keys(result).forEach(
                function(key){
                    let row = result[key]
                    uploaded_courses[key] = result

                    //PRINTING THE RESULT
                    Object.keys(row).forEach(
                        function(key){
                            console.log(key + " : " + row[key])
                        }
                    )
                    console.log("\n\n\n")
                }
            )
        })
        
    })

    res.send("This is my courses page(faculty)")
}


module.exports = {register, login, logout, mycourses}
const con = require("../0_main/mysql")

function register(req, res)
{
    res.send("You are registered as an institute")
}

function login(req, res)
{
    res.send("You are logged in as an institute")
}

function logout(req, res)
{
    res.send("You are logged out as an institute")
}


function approvals(req, res)
{
    var student = [], faculty = [], payment = []
    var i_id = 2

    con.connect( function(err){
        if(err)
            throw err
            // console.log("Error while connecting to mySQL in institute")   //

        let student_sql = `SELECT *
                           FROM student_student S
                           WHERE S.status = "Pending" AND S.i_id_id = ${i_id};`

        let faculty_sql = `SELECT *
                           FROM faculty_faculty F
                           WHERE F.status = "Pending" AND F.i_id_id = ${i_id};`

        let payment_sql = `SELECT S.s_name, C.c_name, P.utr_no, P.image, P.date, P.time
                           FROM student_pays P
                           JOIN student_student S on P.s_id_id = S.s_id
                           JOIN course_course C on P.c_id_id = C.c_id
                           JOIN faculty_faculty F on C.f_id_id = F.f_id
                           WHERE F.i_id_id = ${i_id};`

        con.query(student_sql, function(err, result){
            if(err)
                console.log("Error while executing query in institute")   //throw err

            
            Object.keys(result).forEach(
                function(key){
                    let row = result[key]
                    student[key] = row

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

        con.query(faculty_sql, function(err, result){
            if(err)
                console.log("Error while executing query in institute")   //throw err

            Object.keys(result).forEach(
                function(key){
                    let row = result[key]
                    faculty[key] = row

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

        con.query(payment_sql, function(err, result){
            if(err)
                console.log("Error while executing query in institute")   //throw err

            Object.keys(result).forEach(
                function(key){
                    let row = result[key]
                    payment[key] = row

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

    res.send("This is my approvals page")
}


module.exports = {register, login, logout, approvals}
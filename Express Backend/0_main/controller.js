const mysql = require("mysql")

//DATABASE CONNECTION
const con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "",
    database: "lio"
})



function explore(req, res)
{
    var specialization = [], most_viewed = [], recently_launched = [], guided_project = [];

    con.connect( function(err){
        if(err)
            console.log("Error while connecting to mySQL in main")   //throw err

        let specialization_sql = `SELECT DISTINCT C.specialization 
                                  FROM course_course C
                                  ORDER BY C.date ASC
                                  LIMIT 6;`

        let most_viewed_sql = `SELECT * 
                               FROM course_course C
                               ORDER BY C.total_views DESC
                               LIMIT 10;`

        let recently_launched_sql = `SELECT * 
                                     FROM course_course C
                                     ORDER BY C.date DESC
                                     LIMIT 10;`
        
        let guided_project_sql = `SELECT * 
                                  FROM course_course C
                                  WHERE C.guided_project = 1
                                  LIMIT 10;`
        
        con.query(specialization_sql, function(err, result){
            if(err)
                console.log("Error while executing query in main")   //throw err
            
            //PRINTING THE RESULT
            Object.keys(result).forEach(
                function(key){
                    let value = result[key]
                    specialization[key] = value.specialization
                    console.log(value.specialization)
                }
            )
        })

        con.query(most_viewed_sql, function(err, result){
            if(err)
                console.log("Error while executing query in main")   //throw err
            
            //PRINTING THE RESULT
            Object.keys(result).forEach(
                function(key){
                    let row = result[key]
                    most_viewed[key] = row

                    Object.keys(row).forEach(
                        function(key){
                            console.log(key + " : " + row[key])
                        }
                    )
                    console.log("\n\n\n")
                }
            )
        })

        con.query(recently_launched_sql, function(err, result){
            if(err)
                console.log("Error while executing query in main")   //throw err
            
            console.log("\n\n\n\n\n\n\n\n")
            //PRINTING THE RESULT
            Object.keys(result).forEach(
                function(key){
                    let row = result[key]
                    recently_launched[key] = row

                    Object.keys(row).forEach(
                        function(key){
                            console.log(key + " : " + row[key])
                        }
                    )
                    console.log("\n\n\n")
                }
            )
        })

        con.query(guided_project_sql, function(err, result){
            if(err)
                console.log("Error while executing query in main")   //throw err
            
            //PRINTING THE RESULT
            Object.keys(result).forEach(
                function(key){
                    let row = result[key]
                    guided_project[key] = row

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


    res.send("This is explore page")
}

function about(req, res)
{
    res.send("This is about page")
}

module.exports = {explore, about}
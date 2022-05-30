const con = require("../0_main/mysql")

function studentVerification(req, res)
{
    var s_id = req.params.s_id;

    //VERIFICATION should be done before updating (if the institute is actually logged in)

    //UPDATING
    con.connect(function(err){
        if(err)
            console.log("Error while connecting to mySQL in verification")   //throw err

        let sql = `UPDATE student_student S
                   SET S.status = "Verified"
                   WHERE S.s_id = ${s_id};`
        
        con.query(sql, function(err, result){
            if(err)
                console.log("Error while executing query in verification")   //throw err
        })

    })

    res.send("This is student verification")
}

function facultyVerification(req, res)
{
    var f_id = req.params.f_id;

    //VERIFICATION should be done before updating (if the institute is actually logged in)

    //UPDATING
    con.connect(function(err){
        if(err)
            console.log("Error while connecting to mySQL in verification")   //throw err

        let sql = `UPDATE faculty_faculty F
                   SET F.status = "Verified"
                   WHERE F.f_id = ${f_id};`
        
        con.query(sql, function(err, result){
            if(err)
                console.log("Error while executing query in verification")   //throw err
        })

    })

    res.send("This is faculty verification")
}

function paymentVerification(req, res)
{
    var s_id = req.params.s_id;
    var c_id = req.params.c_id;

    //VERIFICATION should be done before updating (if the institute is actually logged in)

    //INSERTING and DELETING
    con.connect(function(err){
        if(err)
            console.log("Error while connecting to mySQL in verification")   //throw err

        let insertSql = `INSERT INTO student_enrolls (status, c_id_id, s_id_id, watched)
                         VALUES ("inprogress", ${c_id}, ${s_id}, "");`

        let deleteSql = `DELETE FROM student_pays
                         WHERE c_id_id = ${c_id} AND s_id_id = ${s_id};`
        
        con.query(insertSql, function(err, result){
            if(err)
                console.log("Error while executing query in verification")   //throw err
        })

        con.query(deleteSql, function(err, result){
            if(err)
                console.log("Error while executing query in verification")   //throw err
        })

    })

    res.send("This is payment verification")
}

module.exports = {studentVerification, facultyVerification, paymentVerification}
const router = require("express").Router();
const db = require("../models");
const bcrypt = require('bcrypt');
const verify = require("./verifyToken")
const { QueryTypes } = require('sequelize');
const sequelize = require('sequelize');



const jwt = require("jsonwebtoken");

const saltRounds = 10;

User = db.User;
completed = db.Completed;
console.log(completed)
console.log();


router.post('/register', async (req,res) => {

    pass = req.body.password;
    console.log("pass: " + hashed);
    
    try {
        hashed = await bcrypt.hash(pass,saltRounds)
        savedUser = await User.create({
            username: req.body.username,
            email: req.body.email,  
            password: hashed,
            firstName: req.body.firstName,
            lastName: req.body.lastName,
            birthday: req.body.birthday
        }).then ((saved) => {
            res.send(saved);
        })
        
    } catch(err) {        
        res.status(400).send(err);
    }
})

router.post('/login', async (req,res) => {

    usernameOrEmail =  req.body.usernameOrEmail;
    pass = req.body.password;

    try {
        loggedIn = await User.findOne({
            where: {
                email: usernameOrEmail,
            }
        })
        if (loggedIn != null) {
            bcrypt.compare(pass,loggedIn.password, function (err,result) {
                if(result == true) {
                    const token = jwt.sign({id:loggedIn.id}, process.env.TOKEN_SECRET)
                    res.header('auth-token', token).send(token);
                }
                else 
                    res.status(401).send("Access denied: invalid credentials");
            })
        }
        else {
            loggedIn = await User.findOne({
                where: {
                    username: usernameOrEmail,
                }
            })
            if (loggedIn != null) {
                bcrypt.compare(pass,loggedIn.password, function (err,result) {
                    if(result == true){
                    const token = jwt.sign({id:loggedIn.id}, process.env.TOKEN_SECRET)
                    res.header('auth-token', token).send(token); 
                }
                    else 
                        res.status(401).send("Access denied: invalid credentials")
                })
            }
            else {
                res.status(400).send("Invalid username or email.")
            }
        }  
    }catch(err){
        res.status(400).send(err);        
    }
})

router.get('/completed', verify, async (req,res) => {
    token = req.header('auth-token');
    const decoded = jwt.verify(token, process.env.TOKEN_SECRET); 
    const id = decoded.id;

    try {
        result = await db.sequelize.query(
        'SELECT * FROM completed WHERE UserId = :userid',
        {
          replacements: { userid: id },
          type: QueryTypes.SELECT
        }
      );
      res.send(result);
    } catch (err) {
        res.status(400).send(err);
    }
})

module.exports = router;

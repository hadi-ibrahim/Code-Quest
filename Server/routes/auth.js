const router = require("express").Router();
const db = require("../models");
const bcrypt = require('bcrypt');


const jwt = require("jsonwebtoken");

const saltRounds = 10;

User = db.User;



router.post('/register', async (req,res) => {

    pass = req.body.password;
    hashed = await bcrypt.hash(pass,saltRounds)
    console.log("pass: " + hashed);
    
    try {
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

module.exports = router;

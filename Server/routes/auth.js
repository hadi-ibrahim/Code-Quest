const router = require("express").Router();
const db = require("../models");

User = db.User;



router.post('/register', async (req,res) => {

    try {
        savedUser = await User.create({
            username: req.body.username,
            email: req.body.email,  
            password: req.body.password,
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

    try {
        loggedIn = await User.findOne({
            where: {
                email: usernameOrEmail,
                password: req.body.password
            }
        })
        if (loggedIn != null) 
            res.send(loggedIn);
        else {
            loggedIn = await User.findOne({
                where: {
                    username: usernameOrEmail,
                    password: req.body.password
                }
            })
            if (loggedIn != null) 
                res.send(loggedIn);
            else 
                res.status(401).send("Access denied: username or password are incorrect");
        }  
    }catch(err){
        res.status(400).send("Username or email not specified");        
    }
})

module.exports = router;

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

router.post('/login', (req,res) => {
    res.send("Login!");
})

module.exports = router;

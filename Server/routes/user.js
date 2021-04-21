const router = require("express").Router();
const db = require("../models");
const bcrypt = require('bcrypt');
const verify = require("./verifyToken")
const { QueryTypes } = require('sequelize');
const sequelize = require('sequelize');
const fs = require("fs");
const multer = require("multer");
const path = require("path");




const jwt = require("jsonwebtoken");

const saltRounds = 10;

User = db.User;
Quest = db.Quest;

completed = db.Completed;
console.log(completed)
console.log();

router.get('/', verify, async (req, res) => {
    try {
        await User.findAll({
            attributes: ['username', 'firstName', 'lastName', 'birthday', 'email', 'imgPath']
        })
            .then((users) => res.send(users));
    } catch (err) {
        res.status(400).send(err);
    }
})

router.get('/completed', verify, async (req,res) => {
    token = req.header('auth-token');
    const decoded = jwt.verify(token, process.env.TOKEN_SECRET); 
    const id = decoded.id;

    try {
        results = await db.sequelize.query(
        'SELECT title, imgPath, trophy FROM quests INNER JOIN completed ON quests.id = completed.questId WHERE completed.UserId = :userid',
        {
          replacements: { userid: id },
          type: QueryTypes.SELECT
        }
      );
      res.send(results);
    } catch (err) {
        res.status(400).send(err);
    }
})

router.get('/stats')

router.post('/register', async (req,res) => {

    pass = req.body.password;
    
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
            saved.imgPath = "http://" + process.env.SERVER_IP +"/src/images/Users/" + saved.id +".png";
            saved.save();
            fs.copyFile('./src/temp/default.png', './src/images/Users/' + saved.id + ".png", (err) => {
                if (err) throw err;
              });
            res.send(saved);
        })
        
    } catch(err) {
        console.log(err);
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

router.get('/:userId', verify, async (req, res) => {
    id = req.params.userId;
    try {
        await User.findByPk(id, {
            attributes: ['username', 'firstName', 'lastName', 'birthday', 'email', 'imgPath']
        })
            .then((usr) => res.send(usr));
    } catch (err) {
        res.status(400).send(err);
    }

})

router.get('/stats/:userId', verify, async (req, res) => {
    id = req.params.userId;
    try {
        results = await db.sequelize.query(
            'SELECT categories.id, categories.title, SUM(CASE WHEN c.userId = :userid THEN quests.experience ELSE 0 END) as progress , SUM(quests.experience) as total FROM categories INNER JOIN categoriestotal on categoriestotal.id = categories.id INNER JOIN quests ON quests.categoryId = categories.id LEFT JOIN completed as c on c.QuestId = quests.id GROUP BY categoriestotal.id;',
            {
              replacements: { userid: id },
              type: QueryTypes.SELECT
            }
          );
          res.send(results);
    } catch (err) {
        res.status(400).send(err);
        console.log(err)
    }

})

const upload = multer({
    dest: "./src/temp"
  });

const handleError = (err, res) => {
    res
      .status(500)
      .contentType("text/plain")
      .end("Oops! Something went wrong!");
    };

router.put(
  "/changeProfile",
  upload.single("image" /* name attribute of <file> element in your form */),
  verify,
  async (req, res) => {
      try {
    token = req.header('auth-token');
    const decoded = jwt.verify(token, process.env.TOKEN_SECRET); 
    const id = decoded.id;

    const tempPath = req.file.path;
    const targetPath = path.join(__dirname, "../src/images/Users/" + id + ".png");

    if (path.extname(req.file.originalname).toLowerCase() === ".png") {
      fs.rename(tempPath, targetPath, err => {
        if (err) return handleError(err, res);

        res
          .status(200)
          .contentType("text/plain")
          .send("File uploaded!");
      });
    } else {
      fs.unlink(tempPath, err => {
        if (err) return handleError(err, res);

        res
          .status(403)
          .contentType("text/plain")
          .send("Only .png files are allowed!");
      });
    }
  }catch(err) {
      res.status(400).send(err);
      console.log(err)
  }
}
);
module.exports = router;

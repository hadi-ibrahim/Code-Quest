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
        token = req.header('auth-token');
        const decoded = jwt.verify(token, process.env.TOKEN_SECRET);
        const id = decoded.id;

        await User.findAll({
            attributes: ['username', 'fullName', 'birthday', 'email', 'imgPath'],
            where: { id: id }
        })
            .then((users) => res.send(users));
    } catch (err) {
        res.status(400).send(err);
    }
})

router.get("/emailExist", async (req, res) => {

    try {
        mail = req.query.email;

        await User.findAll({ where: { email: mail } })
            .then((usrs) => {
                res.send(usrs)
            })
    } catch (err) {
        res.status(400).send(err);
        console.log(err);
    }
})

router.get("/usernameExist", async (req, res) => {

    try {
        usrname = req.query.username;

        await User.findAll({ where: { username: usrname } })
            .then((usrs) => {
                res.send(usrs)
            })
    } catch (err) {
        res.status(400).send(err);
        console.log(err);
    }
})

router.get('/completed', verify, async (req, res) => {
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

router.post('/register', async (req, res) => {

    pass = req.body.password;

    try {
        hashed = await bcrypt.hash(pass, saltRounds)
        savedUser = await User.create({
            username: req.body.username,
            email: req.body.email,
            password: hashed,
            fullName: req.body.fullName,
            birthday: req.body.birthday
        }).then((saved) => {
            saved.imgPath = "http://" + process.env.SERVER_IP + "/src/images/Users/" + saved.id + ".png";
            saved.save();
            fs.copyFile('./src/temp/default.png', './src/images/Users/' + saved.id + ".png", (err) => {
                if (err) throw err;
            });
            const token = jwt.sign({ id: saved.id }, process.env.TOKEN_SECRET)
            res.send({
                "success": true,
                "message": token,
                "statusCode": "200"
            });
        })

    } catch (err) {
        console.log(err);
        res.send({
            "success": false,
            "message": "Check Username or Email",
            "statusCode": "200"
        });
    }
})

router.post('/login', async (req, res) => {

    usrname = req.body.username;
    pass = req.body.password;

    try {

        loggedIn = await User.findOne({
            where: {
                username: usrname,
            }
        })
        if (loggedIn != null) {
            bcrypt.compare(pass, loggedIn.password, function (err, result) {
                if (result == true) {
                    const token = jwt.sign({ id: loggedIn.id }, process.env.TOKEN_SECRET)
                    res.header('auth-token', token).send({
                        "success": true,
                        "message": token,
                        "statusCode": 200
                    });
                }
                else
                    res.send({
                        "success": false,
                        "message": "Invalid username or password",
                        "statusCode": 200
                    });
            })
        }
        else {
            res.send({
                "success": false,
                "message": "Invalid username or password",
                "statusCode": 200
            });
        }
    }
    catch (err) {
        res.status(400).send(err);
    }
})

router.get('/stats', verify, async (req, res) => {
    token = req.header('auth-token');
    const decoded = jwt.verify(token, process.env.TOKEN_SECRET);
    const id = decoded.id;
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

router.get('/:userId', verify, async (req, res) => {
    id = req.params.userId;
    try {
        await User.findByPk(id, {
            attributes: ['username', 'fullName', 'birthday', 'email', 'imgPath']
        })
            .then((usr) => res.send(usr));
    } catch (err) {
        res.status(400).send(err);
    }

})

const upload = multer({
    dest: "./src/temp"
});



router.put(
    "/changeProfilePicture",
    upload.single("image" /* name attribute of <file> element in your form */),
    verify,
    async (req, res) => {
        try {
            token = req.header('auth-token');
            const decoded = jwt.verify(token, process.env.TOKEN_SECRET);
            const id = decoded.id;

            // console.log(req);
            const tempPath = req.file.path;
            const type = req.file.mimetype.split("/").pop();
            let targetPath ;

            console.log("type issssss =========")
            console.log(type);
            if (type == "png" || type == "PNG")
                targetPath = path.join(__dirname, "../src/images/Users/" + id + ".png");
            else if (type == "jpeg")
                targetPath = path.join(__dirname, "../src/images/Users/" + id + ".jpeg");
            else if (type == "jpg")
                targetPath = path.join(__dirname, "../src/images/Users/" + id + ".jpg");
            else {
                fs.unlink(tempPath, err => {
                    if (err) return handleError(err, res);
                    targetPath = path.join(__dirname, "../src/images/Users/" + id + ".png");

                    // res
                    //     .status(403)
                    //     .contentType("text/plain")
                    //     .send("Only .png, .jpg, .jpeg files are allowed!");
                });
            }
            fs.rename(tempPath, targetPath, err => {
                // if (err) {
                //      res.send({
                //         "success": false,
                //         "message": "Error loading image - invalid type",
                //         "statusCode": 200
                //      });
                //      success = false;
                // }

            });

            await User.findByPk(id)
                .then(usr => {
                    const pathWithExtension = usr.imgPath.split(".");
                    console.log(pathWithExtension[pathWithExtension.length - 1])
                    success = true;
                    
                    if (pathWithExtension[pathWithExtension.length - 1] != type) {
                        fs.unlink("./src/images/Users/" + id + "." + pathWithExtension[pathWithExtension.length - 1], err => {
                            if (err)
                                success = false;
                        });

                        pathWithExtension.pop();
                        usr.imgPath = pathWithExtension.join(".") + "." + type;
                        usr.save();
                    }
                    if (success)
                        res.send({
                            "success": true,
                            "message": "Profile Picture Updated Successfully",
                            "statusCode": 200
                        });
                    else 
                        res.send({
                        "success": false,
                        "message": "Error occured",
                        "statusCode": 200
                    });;
                })

        } catch (err) {
            res.send({
                "success": false,
                "message": "Error occured",
                "statusCode": 200
            })
        }
    }
);

router.put("/", verify, async (req, res) => {

    try {
        token = req.header('auth-token');
        const decoded = jwt.verify(token, process.env.TOKEN_SECRET);
        const id = decoded.id;

        email = req.body.email;
        fullName = req.body.fullName;
        birthday = req.body.birthday;

        await User.findByPk(id)
            .then((usr) => {
                usr.email = email;
                usr.fullName = fullName
                usr.birthday = birthday
                usr.save();
                res.send({
                    "success": true,
                    "message": "Profile Updated Successfully",
                    "statusCode": 200
                });
            })
    } catch (err) {
        res.status(400).send(err);
        console.log(err);
    }
})


module.exports = router;

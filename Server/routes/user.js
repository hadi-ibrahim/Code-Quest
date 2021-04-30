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
            attributes: ['username', 'fullName', 'birthday', 'email', 'imgPath']
        })
            .then((users) => res.send(users));
    } catch (err) {
        res.status(400).send(err);
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
            fullname: req.body.fullName,
            birthday: req.body.birthday
        }).then((saved) => {
            saved.imgPath = "http://" + process.env.SERVER_IP + "/src/images/Users/" + saved.id + ".png";
            saved.save();
            fs.copyFile('./src/temp/default.png', './src/images/Users/' + saved.id + ".png", (err) => {
                if (err) throw err;
            });
            res.send(saved);
        })

    } catch (err) {
        console.log(err);
        res.status(400).send(err);
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
    catch (err) {
        res.status(400).send(err);
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
    "/changeProfilePicture",
    upload.single("image" /* name attribute of <file> element in your form */),
    verify,
    async (req, res) => {
        try {
            token = req.header('auth-token');
            const decoded = jwt.verify(token, process.env.TOKEN_SECRET);
            const id = decoded.id;

            const tempPath = req.file.path;
            const type = req.file.mimetype.split("/").pop();
            let targetPath;

            if (type == "png")
                targetPath = path.join(__dirname, "../src/images/Users/" + id + ".png");
            else if (type == "jpeg")
                targetPath = path.join(__dirname, "../src/images/Users/" + id + ".jpeg");
            else if (type == "jpg")
                targetPath = path.join(__dirname, "../src/images/Users/" + id + ".jpg");
            else {
                fs.unlink(tempPath, err => {
                    if (err) return handleError(err, res);

                    res
                        .status(403)
                        .contentType("text/plain")
                        .send("Only .png, .jpg, .jpeg files are allowed!");
                });
            }

            fs.rename(tempPath, targetPath, err => {
                if (err) return handleError(err, res);

            });

            await User.findByPk(id)
                .then(usr => {
                    const pathWithExtension = usr.imgPath.split(".");
                    let success = true;
                    console.log(pathWithExtension[pathWithExtension.length - 1])
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
                        res.send("File uploaded!");
                    else return handleError("Error occured", res);
                })

        } catch (err) {
            res.status(400).send(err);
            console.log(err)
        }
    }
);

router.put("/updateProfile", verify, async (req, res) => {

    try {
        token = req.header('auth-token');
        const decoded = jwt.verify(token, process.env.TOKEN_SECRET);
        const id = decoded.id;

        username = req.body.username;
        email = req.body.email;
        fullName = req.body.fullName;

        password = await bcrypt.hash(req.body.password, saltRounds);

        await User.findByPk(id)
            .then((usr) => {
                usr.email = email;
                usr.password = password;
                usr.username = username;
                usr.fullName = fullName
                usr.save();
                res.send("User updated successully!")
            })
    } catch (err) {
        res.status(400).send(err);
        console.log(err);
    }
})


module.exports = router;

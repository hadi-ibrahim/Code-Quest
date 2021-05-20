const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")

Questions = db.Question;
Quest = db.Quest;
Category = db.Category;


router.get("/", verify, async (req, res) => {
    try {
        await Questions.findAll({
            attributes: { exclude: ['solution'] }
        })
            .then((Questions) => res.send(Questions));
    } catch (err) {
        res.status(400).send(err);
    }
})

router.get("/:QuestionId", verify, async (req, res) => {
    id = req.params.QuestionId;
    try {
        await Questions.findByPk(id, { attributes: { exclude: [ 'createdAt', 'updatedAt'] } })
            .then((Question) => res.send(Question));
    } catch (err) {
        res.status(400).send(err);
    }
})

module.exports = router;
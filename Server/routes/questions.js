const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")

Questions = db.Question;


router.get("/", verify, async (req,res) => {    
    try {
    await Questions.findAll()
        .then((Questions) => res.send(Questions));
    } catch(err) {
        res.status(400).send(err);
    }
})

router.get("/:QuestionId", verify, async (req,res) => {
    id = req.params.QuestionId;
    try {
    await Questions.findByPk(id)
        .then((Question) => res.send(Question));
    } catch(err) {
        res.status(400).send(err);
    }
})

module.exports = router;
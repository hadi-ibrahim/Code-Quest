const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")

Questions = db.Question;


router.get("/", verify, (req,res) => {
    Questions.findAll()
        .then((Questions) => res.send(Questions));
})

router.get("/:QuestionId", verify, (req,res) => {
    id = req.params.QuestionId;

    Questions.findByPk(id)
        .then((Question) => res.send(Question));
})

module.exports = router;
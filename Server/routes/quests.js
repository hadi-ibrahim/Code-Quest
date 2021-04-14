const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")

Quests = db.Quest;


router.get("/", verify, (req,res) => {
    Quests.findAll()
        .then((quests) => res.send(quests));
})

router.get("/:questId", verify, (req,res) => {
    id = req.params.questId;

    Quests.findByPk(id)
        .then((quest) => res.send(quest));
})

module.exports = router;
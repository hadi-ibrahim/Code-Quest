const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")

Quests = db.Quest;


router.get("/", verify, async (req,res) => {
    try {
    await Quests.findAll()
        .then((quests) => res.send(quests));
    } catch(err) {
        res.status(400).send(err);
    }
})

router.get("/:questId", verify, async (req,res) => {
    id = req.params.questId;
    try {
    await Quests.findByPk(id)
        .then((quest) => res.send(quest));
    } catch(err) {
        res.status(400).send(err);
    }
})

module.exports = router;
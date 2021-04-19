const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")

PuzzleOptions = db.PuzzleOption;

router.get("/", verify, async (req,res) => {
    try {
    await PuzzleOptions.findAll()
        .then((ops) => {
             res.send(ops)
        });
    } catch (err) {
        res.status(400).send(err);
    }
})

router.get("/:PuzzleOptionId", verify, async (req,res) => {
    id = req.params.PuzzleOptionId;
    try {
    await PuzzleOptions.findByPk(id)
        .then((ops) => res.send(ops));
    } catch (err) {
        res.status(400).send(err);
    }
})

module.exports = router;
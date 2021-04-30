const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")

PuzzleOptions = db.PuzzleOption;
Puzzle = db.Puzzle;
router.get("/", verify, async (req, res) => {
    try {
        await PuzzleOptions.findAll({ include: Puzzle })
            .then((ops) => {
                res.send(ops)
            });
    } catch (err) {
        res.status(400).send(err);
    }
})

router.get("/:PuzzleOptionId", verify, async (req, res) => {
    id = req.params.PuzzleOptionId;
    try {
        await PuzzleOptions.findByPk(id, { include: Puzzle })
            .then((ops) => res.send(ops));
    } catch (err) {
        res.status(400).send(err);
    }
})

module.exports = router;
const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")

const puzzleOptionsRoute = require('./puzzleOptions');
router.use('/options' , puzzleOptionsRoute);

Puzzles = db.Puzzle;


router.get("/", verify, async (req,res) => {
    try {
    await Puzzles.findAll()
        .then((Puzzles) => res.send(Puzzles));
    }catch(err) {
        res.status(400).send(err);
    }
})

router.get("/:PuzzleId", verify, async (req,res) => {
    id = req.params.PuzzleId;
    try {
    await Puzzles.findByPk(id)
        .then((Puzzle) => res.send(Puzzle));
    } catch(err) {
        res.status(400).send(err);
    }
})

module.exports = router;
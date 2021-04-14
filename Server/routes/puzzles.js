const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")

const puzzleOptionsRoute = require('./puzzleOptions');
router.use('/options' , puzzleOptionsRoute);

Puzzles = db.Puzzle;


router.get("/", verify, (req,res) => {
    Puzzles.findAll()
        .then((Puzzles) => res.send(Puzzles));
})

router.get("/:PuzzleId", verify, (req,res) => {
    id = req.params.PuzzleId;

    Puzzles.findByPk(id)
        .then((Puzzle) => res.send(Puzzle));
})

module.exports = router;
const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")

PuzzleOptions = db.PuzzleOption;

router.get("/", verify, (req,res) => {
    PuzzleOptions.findAll()
        .then((ops) => {
             res.send(ops)
        });
})

router.get("/:PuzzleOptionId", verify, (req,res) => {
    id = req.params.PuzzleOptionId;

    PuzzleOptions.findByPk(id)
        .then((ops) => res.send(ops));
})

module.exports = router;
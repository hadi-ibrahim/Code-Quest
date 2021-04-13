const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")

const Quest = db.Quest;

router.get("/", verify, (req,res) => {
    res.json({
        categories: {
            title: "test"
        }
    })
})

module.exports = router;

const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")

Category = db.Category;
router.get("/", verify, async (req, res) => {
    try {
        await Category.findAll()
            .then((cats) => {
                res.send(cats)
            });
    } catch (err) {
        res.status(400).send(err);
    }
})

router.get("/:CategoryId", verify, async (req, res) => {
    id = req.params.CategoryId;
    try {
        await Category.findByPk(id,)
            .then((cats) => res.send(cats));
    } catch (err) {
        res.status(400).send(err);
    }
})

module.exports = router;
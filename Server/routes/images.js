const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")
const multer = require("multer");
const jwt = require("jsonwebtoken");
const path = require("path");
const fs = require("fs");

User = db.User;

const upload = multer({
    dest: "./src/temp"
  });

const handleError = (err, res) => {
    res
      .status(500)
      .contentType("text/plain")
      .end("Oops! Something went wrong!");
    };

router.post(
  "/upload",
  upload.single("image" /* name attribute of <file> element in your form */),
  verify,
  async (req, res) => {
      try {
    token = req.header('auth-token');
    const decoded = jwt.verify(token, process.env.TOKEN_SECRET); 
    const id = decoded.id;

    const tempPath = req.file.path;
    const targetPath = path.join(__dirname, "../src/images/Users/" + id + ".png");

    if (path.extname(req.file.originalname).toLowerCase() === ".png") {
      fs.rename(tempPath, targetPath, err => {
        if (err) return handleError(err, res);

        res
          .status(200)
          .contentType("text/plain")
          .send("File uploaded!");
      });
    } else {
      fs.unlink(tempPath, err => {
        if (err) return handleError(err, res);

        res
          .status(403)
          .contentType("text/plain")
          .send("Only .png files are allowed!");
      });
    }
  }catch(err) {
      res.status(400).send(err);
  }
}
);

module.exports = router;
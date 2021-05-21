const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")
const jwt = require("jsonwebtoken");

Quests = db.Quest;
Questions = db.Question;
Puzzles = db.Puzzles;
PuzzleOptions = db.PuzzleOptions;
User = db.User;
Category = db.Category;


router.get("/", async (req, res) => {
    try {
        await Quests.findAll({ include: Category })
            .then((quests) => res.send(quests));
    } catch (err) {
        res.status(400).send(err);
    }
})
router.post("/solve", verify, (req, res) => {
    try {
        id = req.body.quest.id;
        token = req.header('auth-token');
        const decoded = jwt.verify(token, process.env.TOKEN_SECRET);
        const userId = decoded.id;
        Quests.findByPk(id,
            {
                attributes: { exclude: ['createdAt', 'updatedAt', 'CategoryId'] },
                include: Category
            })
            .then((quest) => {
                allQuestions = [];
                allPuzzles = [];

                Questions.findAll({
                    where: {
                        QuestId: quest.id
                    },
                    attributes: { exclude: ["createdAt", "updatedAt", "QuestId"] }
                })
                    .then((questions) => {
                        questions.forEach(question => allQuestions.push(question));

                    });
                Puzzles.findAll({
                    include: PuzzleOptions,
                    where: { QuestId: quest.id },
                    attributes: { exclude: ["createdAt", "updatedAt", "QuestId"] }
                })
                    .then((puzzles) => {
                        puzzles.forEach(puzzle => 
                            allPuzzles.push(puzzle))
                        })
                        .then(() => {
                            if (areAnswersCorrect(allPuzzles, req.body.quest.puzzles, allQuestions, req.body.quest.questions)) {
                                User.findByPk(userId)
                                    .then((usr) => {
                                        usr.addCompletedQuest(id)
                                        usr.update();
                                        res.send({
                                            "success": true,
                                            "message": "Quest Completed!",
                                            "statusCode": 200
                                        });                                    })
                            }
                            else res.send({
                                "success": false,
                                "message": "Wrong answers, keep trying!",
                                "statusCode": 200
                            });      
                        })
                    })


    } catch (err) {
        res.status(400).send(err);
        console.log(err);
    }
})

function areAnswersCorrect(ansPuzzles, puzzles, ansQuestions, questions) {
    ans = true
    
    // todo : wrongPuzzlesArray
    ansPuzzles.forEach((ansPuz) => {
        console.log(ansPuz)
        const puz = puzzles.find(element => element.id == ansPuz.id)
        if (!puz)
            ans = false;
        correctOptions = []
        ansPuz.PuzzleOptions.forEach((ansOption) => {
            if (ansOption.isCorrect)
                correctOptions.push(ansOption.option);
        })

        selectedOptions = [];
        puz.options.forEach((option) => {
            console.log(option)
            if(option.isCorrect)
            selectedOptions.push(option.option);
        })

        const array2Sorted = selectedOptions.slice().sort();

        if (selectedOptions.length != correctOptions.length) {
            ans = false;
        }

        console.log(selectedOptions)
        console.log(correctOptions)

        correctOptions.slice().sort().forEach((value, index) => {
            if (value !== array2Sorted[index])
                ans = false;
            // add ans to wrongPuz
        });

    })
    // todo : wrongQuestionsArray

    ansQuestions.forEach((ansQuestion) => {
        const ques = questions.find(question => question.id == ansQuestion.id);
        if (!ques)
            ans = false

        if (ques.solution !== ansQuestion.solution)
            ans = false
        // add and to wrongQuestion
    })
    return ans;
}
router.get("/questionsAndPuzzles/:questId", verify, (req, res) => {
    try {
        id = req.params.questId;

        Quests.findByPk(id, { attributes: { exclude: ['createdAt', 'updatedAt', 'CategoryId'] } })
            .then((quest) => {
                allQuestions = [];
                allPuzzles = [];

                Questions.findAll({
                    where: {
                        QuestId: quest.id
                    },
                    attributes: { exclude: ["createdAt", "updatedAt", "QuestId", "solution"] }
                })
                    .then((questions) => {
                        questions.forEach(question => allQuestions.push(question));

                    });
                Puzzles.findAll({
                    where: {
                        QuestId: quest.id
                    },
                    attributes: { exclude: ["createdAt", "updatedAt", "QuestId"] }
                })
                    .then((puzzles) => {
                        puzzles.forEach(puzzle => {
                            if (puzzle.length != 0) {
                                PuzzleOptions.findAll({
                                    where: {
                                        PuzzleId: puzzle.id
                                    },
                                    attributes: { exclude: ["createdAt", "updatedAt", "PuzzleId"] }
                                })
                                    .then((options) => {
                                        allPuzzles.push({
                                            prompt: puzzle.prompt,
                                            id: puzzle.id,
                                            options: options
                                        })
                                        res.send({
                                            'questions': allQuestions,
                                            'puzzles': allPuzzles
                                        });

                                    })
                            }
                        });
                    })
            });
    } catch (err) {
        res.status(400).send(err);
        console.log(err);
    }
})
router.get("/answers", verify, (req, res) => {
    try {
        Quests.findAll({ attributes: { exclude: ['createdAt', 'updatedAt', 'CategoryId'] } })
            .then((quests) => {
                allQuests = [];
                quests.forEach(quest => {
                    allQuestions = [];
                    allPuzzles = [];

                    Questions.findAll({
                        where: {
                            QuestId: quest.id
                        },
                        attributes: { exclude: ["createdAt", "updatedAt", "QuestId"] },
                    })
                        .then((questions) => {
                            questions.forEach(question => allQuestions.push(question));

                        });
                    Puzzles.findAll({
                        where: {
                            QuestId: quest.id
                        },
                        attributes: { exclude: ["createdAt", "updatedAt", "QuestId"] }
                    })
                        .then((puzzles) => {
                            puzzles.forEach(puzzle => {
                                if (puzzle.length != 0) {
                                    PuzzleOptions.findAll({
                                        where: {
                                            PuzzleId: puzzle.id
                                        },
                                        attributes: { exclude: ["createdAt", "updatedAt", "PuzzleId"] }

                                    })
                                        .then((options) => {
                                            allPuzzles.push({
                                                prompt: puzzle.prompt,
                                                id: puzzle.id,
                                                options: options
                                            })
                                            allQuests.push({
                                                'Quest': quest,
                                                'questions': allQuestions,
                                                'puzzles': allPuzzles
                                            })
                                            res.send(allQuests);

                                        })
                                }
                            });

                        })

                });

            });

    } catch (err) {
        res.status(400).send(err);
        console.log(err);
    }
})

router.get("/answers/:questId", verify, (req, res) => {
    try {
        id = req.params.questId;

        Quests.findByPk(id, { attributes: { exclude: ['createdAt', 'updatedAt', 'CategoryId'] } })
            .then((quest) => {
                allQuestions = [];
                allPuzzles = [];

                Questions.findAll({
                    where: {
                        QuestId: quest.id
                    },
                    attributes: { exclude: ["createdAt", "updatedAt", "QuestId"] }
                })
                    .then((questions) => {
                        questions.forEach(question => allQuestions.push(question));

                    });
                Puzzles.findAll({
                    where: {
                        QuestId: quest.id
                    },
                    attributes: { exclude: ["createdAt", "updatedAt", "QuestId"] }
                })
                    .then((puzzles) => {
                        puzzles.forEach(puzzle => {
                            if (puzzle.length != 0) {
                                PuzzleOptions.findAll({
                                    where: {
                                        PuzzleId: puzzle.id
                                    },
                                    attributes: { exclude: ["createdAt", "updatedAt", "PuzzleId"] }
                                })
                                    .then((options) => {
                                        allPuzzles.push({
                                            prompt: puzzle.prompt,
                                            id: puzzle.id,
                                            options: options
                                        })
                                        res.send({
                                            'Quest': quest,
                                            'questions': allQuestions,
                                            'puzzles': allPuzzles
                                        });

                                    })
                            }
                        });
                    })
            });


    } catch (err) {
        res.status(400).send(err);
        console.log(err);
    }
})

router.get("/:questId", verify, async (req, res) => {
    id = req.params.questId;
    try {
        await Quests.findByPk(id, { include: Category })
            .then((quest) => res.send(quest));
    } catch (err) {
        res.status(400).send(err);
    }
})

module.exports = router;
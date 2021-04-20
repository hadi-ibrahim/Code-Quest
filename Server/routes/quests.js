const router = require("express").Router();
const db = require("../models");
const verify = require("./verifyToken")

Quests = db.Quest;
Questions = db.Question;
Puzzles = db.Puzzles;
PuzzleOptions = db.PuzzleOptions;


router.get("/", verify, async (req,res) => {
    try {
    await Quests.findAll()
        .then((quests) => res.send(quests));
    } catch(err) {
        res.status(400).send(err);
    }
})

router.get("/answers", verify, (req,res) => {
    try {
        Quests.findAll({attributes: {exclude:['createdAt', 'updatedAt', 'CategoryId']}})
        .then((quests) => {
            allQuests = [];
            quests.forEach(quest =>  {
                allQuestions = [];
                allPuzzles = [];

                 Questions.findAll({
                    where: {
                        QuestId : quest.id
                    },
                    attributes : {exclude: ["createdAt", "updatedAt","QuestId"]}
                })
                .then((questions) => {
                    questions.forEach(question => allQuestions.push(question));

                });
                 Puzzles.findAll({
                    where:{
                        QuestId: quest.id
                    },
                    attributes: {exclude: ["createdAt", "updatedAt","QuestId"]}
                })
                .then((puzzles)=> {
                    puzzles.forEach( puzzle => {
                        if(puzzle.length!=0){
                             PuzzleOptions.findAll({
                                where: {
                                    PuzzleId: puzzle.id
                                },
                                attributes: {exclude: ["createdAt", "updatedAt","PuzzleId"]}

                            })
                            .then((options) => {
                                allPuzzles.push ({
                                    Puzzle: {
                                        prompt:puzzle,
                                        Options: options
                                    }
                                })
                                allQuests.push({'Quest': quest,
                                'Questions': allQuestions,
                                'Puzzles':allPuzzles})
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

router.get("/answers/:questId", verify, (req,res) => {
    try {
        id = req.params.questId;

        Quests.findByPk(id,{attributes: {exclude:['createdAt', 'updatedAt', 'CategoryId']}})
        .then((quest) => {
            allQuestions = [];
            allPuzzles = [];

                Questions.findAll({
                where: {
                    QuestId : quest.id
                },
                attributes : {exclude: ["createdAt", "updatedAt","QuestId"]}
            })
            .then((questions) => {
                questions.forEach(question => allQuestions.push(question));

            });
                Puzzles.findAll({
                where:{
                    QuestId: quest.id
                },
                attributes: {exclude: ["createdAt", "updatedAt","QuestId"]}
            })
            .then((puzzles)=> {
                puzzles.forEach( puzzle => {
                    if(puzzle.length!=0){
                            PuzzleOptions.findAll({
                            where: {
                                PuzzleId: puzzle.id
                            },
                            attributes: {exclude: ["createdAt", "updatedAt","PuzzleId"]}
                        })
                        .then((options) => {
                            allPuzzles.push ({
                                Puzzle: {
                                    prompt:puzzle,
                                    Options: options
                                }
                            })
                            res.send({'Quest': quest,
                            'Questions': allQuestions,
                            'Puzzles':allPuzzles});

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
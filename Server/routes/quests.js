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


router.get("/", async (req,res) => {
    try {
    await Quests.findAll({include : Category})
        .then((quests) => res.send(quests));
    } catch(err) {
        res.status(400).send(err);
    }
})
router.post("/solve/:questId", verify, (req,res) => {
    try {
        id = req.params.questId;
        token = req.header('auth-token');
        const decoded = jwt.verify(token, process.env.TOKEN_SECRET); 
        const userId = decoded.id;
        Quests.findByPk(id,
            {
                attributes: {exclude:['createdAt', 'updatedAt', 'CategoryId']},
                include: Category
            })
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
                                prompt:puzzle.prompt,
                                id:puzzle.id,
                                Options: options
                            })


                            if(areAnswersCorrect(allPuzzles, req.body.Puzzles, allQuestions, req.body.Questions) ){
                                User.findByPk(userId)
                                .then((usr) => {
                                    usr.addCompletedQuest(id)
                                    usr.update();
                                    res.send("Quest completed successfully!")
                                })
                            }
                            else res.send("Wrong answers. Try again");
                            
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
                    attributes : {exclude: ["createdAt", "updatedAt","QuestId"]},
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
                                    prompt:puzzle.prompt,
                                    id:puzzle.id,
                                    Options: options
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
                                prompt:puzzle.prompt,
                                id:puzzle.id,
                                Options: options
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
    await Quests.findByPk(id, {include : Category})
        .then((quest) => res.send(quest));
    } catch(err) {
        res.status(400).send(err);
    }
})

function areAnswersCorrect(ansPuzzles, puzzles, ansQuestions, questions) {
    ans = true
    ansPuzzles.forEach((ansPuz) => {
        const puz = puzzles.find(element => element.id == ansPuz.id)
        if (!puz)
            ans = false;

        correctOptions = []
        ansPuz.Options.forEach((ansOption) => {
            if (ansOption.isCorrect)
                correctOptions.push(ansOption.id);
        })

        selectedOptions = []; 

        puz.Options.forEach((option) => {
            selectedOptions.push(option.id);
        })

        const array2Sorted = selectedOptions.slice().sort();
        
        if (selectedOptions.length != correctOptions.length){
            ans = false;
        }

        correctOptions.slice().sort().forEach( (value, index) =>{
        if (value !== array2Sorted[index])
            ans = false;
        });
        
    })
    ansQuestions.forEach((ansQuestion) => {
        const ques = questions.find(question => question.id == ansQuestion.id);
        if (!ques) 
            ans = false

        if(ques.solution !== ansQuestion.solution)
            ans = false
    })
    return ans;
}

module.exports = router;
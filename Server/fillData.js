const db = require("./models");
const bcrypt = require('bcrypt');
const { use } = require("./routes/user");
const saltRounds = 10;

Category = db.Category;
Quest = db.Quest;
Question = db.Question;
Puzzle = db.Puzzle;
PuzzleOptions = db.PuzzleOption;
Completed = db.Completed;
User = db.User ;

async function fillData() {
    await fillUsers();
    await fillCategories();
    await fillQuests();
    await fillQuestions(); 
    await fillPuzzles();
    await fillPuzzleOptions();
    await fillCompleted();
}

async function fillCategories() {
    try {
    await Category.create({
        title: "Back-end",
        imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Categories/backend.png'
    })

    await Category.create({
        title: "Front-end",
        imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Categories/frontend.png'
    })

    await Category.create({
        title: "Data Science",
        imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Categories/dataScience.png'
    })

    await Category.create({
        title: "IT",
        imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Categories/IT.png'
    })
} catch (err) {
    console.log(err);
}
}

async function fillQuests() {
    try {
    await Quest.create({
        title: "HTML",
        experience: 500,
        CategoryId: 2,
        trophy: 'bronze',
        imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Quests/html.png'
    })

    await Quest.create({
        title: "CSS",
        experience: 750,
        CategoryId: 2,
        trophy: 'silver',
        imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Quests/css.png'

    })

    await Quest.create({
        title: "Javascript",
        experience: 1000,
        CategoryId: 2,
        trophy: 'silver',
        imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Quests/js.png'

    })

    await Quest.create({
        title: "Angular",
        experience: 1250,
        CategoryId: 2,
        trophy: 'gold',
        imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Quests/angular.png'

    })

    await Quest.create({
        title: "React",
        experience: 1250,
        CategoryId: 2,
        trophy: 'gold',
        imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Quests/react.png'

    })

    await Quest.create({
        title: "C++",
        experience: 1000,
        CategoryId: 1,
        trophy: 'silver',
        imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Quests/cpp.png'
    })

    await Quest.create({
        title: "C#",
        experience: 1000,
        CategoryId: 1,
        trophy: 'silver',
        imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Quests/cSharp.png'
    })

    await Quest.create({
        title: "Logistic Regression",
        experience: 1000,
        CategoryId: 3,
        trophy: 'silver',
        imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Quests/regression.png'
    })

    await Quest.create({
        title: "Ethernet",
        experience: 500,
        CategoryId: 4,
        trophy: 'Bronze',
        imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Quests/ethernet.png'
    })
} catch (err) {
    console.log(err);
}
    
}

async function fillQuestions() {
try {
    await Question.create({
        prompt: "How can you make a numbered list?",
        solution: "<ol>",
        hint: "<tag here>",
        QuestId: 1
    });

    await Question.create({
        prompt: "Write the HTML5 semantic tag for the following legacy code: <div id='header'>",
        solution: "<header>",
        hint: "<tag here>",
        QuestId: 1
    });
} catch (err) {
    console.log(err);
}
}

async function fillPuzzles() {
    try {
        await Puzzle.create({
        prompt: "Which of the following tags are table elements?"
    });
} catch (err) {
    console.log(err);
}
}

async function fillPuzzleOptions() {
    try {
    await PuzzleOptions.create({
        PuzzleId : 1,
        option : '<Thead>',
        isCorrect : false
    });

    await PuzzleOptions.create({
        PuzzleId : 1,
        option : '<body>',
        isCorrect : false
    });

    await PuzzleOptions.create({
        PuzzleId : 1,
        option : '<table>',
        isCorrect : true
    });

    await PuzzleOptions.create({
        PuzzleId : 1,
        option : '<td>',
        isCorrect : true
    });

    await PuzzleOptions.create({
        PuzzleId : 1,
        option : '<Tfoot>',
        isCorrect : false
    });

    await PuzzleOptions.create({
        PuzzleId : 1,
        option : '<tr>',
        isCorrect : true
    });
} catch (err) {
    console.log(err);
}

}

async function fillCompleted() {
    try {
    await User.findByPk(2)
        .then((usr) => {
            usr.setCompletedQuest(7);
            usr.setCompletedQuest(5);
            usr.setCompletedQuest(3);
            usr.save();
        });
    } catch ( err) {
        console.log(err);
    }
}

function getMethods(obj) {
    var result = [];
    for (var id in obj) {
      try {
        if (typeof(obj[id]) == "function") {
          result.push(id + ": " + obj[id].toString());
        }
      } catch (err) {
        result.push(id + ": inaccessible");
      }
    }
    return result;
}

async function fillUsers () {
    try {
        hashed = await bcrypt.hash("test123",saltRounds)
        await User.create({
            username: "Rooronoa" ,
            email: "rooro@gmail.com",
            firstName: "Hadi",
            lastName: "Ibrahim",
            password: hashed,
            imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Categories/backend.png'


        })

        await User.create({
            username: "Raziel" ,
            email: "raz@gmail.com",
            firstName: "Mouhammed",
            lastName: "Soueidan",
            password: hashed,
            imgPath: 'http://' + process.env.SERVER_IP + ':3000/src/images/Categories/backend.png'

        })
    } catch (err) {
        console.log(err);
    }
}

module.exports = fillData;
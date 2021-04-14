const db = require("./models");

Category = db.Category;
Quest = db.Quest;
Question = db.Question;
Puzzle = db.Puzzle;
PuzzleOptions = db.PuzzleOption;


// fillCategories();
// fillQuests();
// fillQuestions(); 
// fillPuzzles();
// fillPuzzleOptions();

function fillCategories() {
    Category.create({
        title: "Back-end",
        imgPath: 'http://192.168.0.107:3000/src/images/Categories/backend.png'
    })

    Category.create({
        title: "Front-end",
        imgPath: 'http://192.168.0.107:3000/src/images/Categories/frontend.png'
    })

    Category.create({
        title: "Data Science",
        imgPath: 'http://192.168.0.107:3000/src/images/Categories/dataScience.png'
    })

    Category.create({
        title: "IT",
        imgPath: 'http://192.168.0.107:3000/src/images/Categories/IT.png'
    })
}

function fillQuests() {
    
    Quest.create({
        title: "HTML",
        experience: 500,
        CategoryId: 2,
        trophy: 'bronze',
        imgPath: 'http://192.168.0.107:3000/src/images/Quests/html.png'
    })

    Quest.create({
        title: "CSS",
        experience: 750,
        CategoryId: 2,
        trophy: 'silver',
        imgPath: 'http://192.168.0.107:3000/src/images/Quests/css.png'

    })

    Quest.create({
        title: "Javascript",
        experience: 1000,
        CategoryId: 2,
        trophy: 'silver',
        imgPath: 'http://192.168.0.107:3000/src/images/Quests/js.png'

    })

    Quest.create({
        title: "Angular",
        experience: 1250,
        CategoryId: 2,
        trophy: 'gold',
        imgPath: 'http://192.168.0.107:3000/src/images/Quests/angular.png'

    })

    Quest.create({
        title: "React",
        experience: 1250,
        CategoryId: 2,
        trophy: 'gold',
        imgPath: 'http://192.168.0.107:3000/src/images/Quests/react.png'

    })

    Quest.create({
        title: "C++",
        experience: 1000,
        CategoryId: 1,
        trophy: 'silver',
        imgPath: 'http://192.168.0.107:3000/src/images/Quests/cpp.png'
    })

    Quest.create({
        title: "C#",
        experience: 1000,
        CategoryId: 1,
        trophy: 'silver',
        imgPath: 'http://192.168.0.107:3000/src/images/Quests/cSharp.png'
    })
    
}

function fillQuestions() {

    Question.create({
        prompt: "How can you make a numbered list?",
        solution: "<ol>",
        hint: "<tag here>",
        QuestId: 1
    });

    Question.create({
        prompt: "Write the HTML5 semantic tag for the following legacy code: <div id='header'>",
        solution: "<header>",
        hint: "<tag here>",
        QuestId: 1
    });
}

function fillPuzzles() {
    Puzzle.create({
        prompt: "Which of the following tags are table elements?"
    });
}

function fillPuzzleOptions() {

    PuzzleOptions.create({
        PuzzleId : 1,
        option : '<Thead>',
        isCorrect : false
    });

    PuzzleOptions.create({
        PuzzleId : 1,
        option : '<body>',
        isCorrect : false
    });

    PuzzleOptions.create({
        PuzzleId : 1,
        option : '<table>',
        isCorrect : true
    });

    PuzzleOptions.create({
        PuzzleId : 1,
        option : '<td>',
        isCorrect : true
    });

    PuzzleOptions.create({
        PuzzleId : 1,
        option : '<Tfoot>',
        isCorrect : false
    });

    PuzzleOptions.create({
        PuzzleId : 1,
        option : '<tr>',
        isCorrect : true
    });

}
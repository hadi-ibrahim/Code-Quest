'use strict'
const { Model, DATE } = require('sequelize');


module.exports = (sequelize, dataTypes) => {
    class Puzzle extends Model {
        static associate(models){

            Puzzle.belongsTo(models.Quest);
            Puzzle.hasMany(models.PuzzleOption)
            

        }
    };
    Puzzle.init({
        prompt: {
            type: dataTypes.STRING,
            notNull:true,
            max:512
        }
        
    }, {
        sequelize,
        modelName: "Puzzle"
    })
    return Puzzle;
    
}
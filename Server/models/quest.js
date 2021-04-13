module.exports = (sequelize, dataTypes) => {
    const Quest = sequelize.define('Quest', {
        description: {
            type: dataTypes.STRING
        }
    })
    return Quest;
    
}
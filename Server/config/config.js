'use strict';

const fs = require('fs');
const dotenv = require('dotenv');

dotenv.config();

let configs = { 
    "development": {
        "username": process.env.DB_USER,
        "password": process.env.DB_PASS,
        "database": "codequest",
        "host": "127.0.0.1",
        "dialect": "mysql"
    },
    "test": {
        "username": "root",
        "password": null,
        "database": "database_test",
        "host": "127.0.0.1",
        "dialect": "mysql"
    },
    "production": {
        "username": "root",
        "password": null,
        "database": "database_production",
        "host": "127.0.0.1",
        "dialect": "mysql"
    }
};
 
let data = JSON.stringify(configs, null, 2);
fs.writeFileSync('config.json', data);
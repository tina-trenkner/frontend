const path = require('path');
const fs = require('fs');
const pify = require('pify');

const readFileP = pify(fs.readFile);
const writeFileP = pify(fs.writeFile);

const {public: publicDir, target} = require('../../config').paths;

module.exports = {
    description: 'Bundle shivs and shims',
    task: () => Promise.all([
        path.resolve(publicDir, 'javascripts', 'components', 'es5-shim', 'es5-shim.js'),
        path.resolve(publicDir, 'javascripts', 'components', 'html5shiv', 'html5shiv.js')
    ].map(file => readFileP(file, 'utf8')))
        .then(srcs => srcs.join(';'))
        .then(src => writeFileP(path.resolve(target, 'javascripts', 'es5-html5.js'), src))
};
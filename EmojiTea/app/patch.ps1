copy node_modules/react-scripts/config/webpack.config.js node_modules/react-scripts/config/webpack.config-bak.js
type node_modules/react-scripts/config/webpack.config-bak.js | findstr /V "ModuleScopePlugin" > node_modules/react-scripts/config/webpack.config.js
pause
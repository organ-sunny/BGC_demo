const path = require("path");
const htmlWebpackPlugin = require("html-webpack-plugin");
const copyWebpackPlugin = require("copy-webpack-plugin");
const vueLoaderPlugin = require("vue-loader/lib/plugin");
const miniCssExtractPlugin = require("mini-css-extract-plugin");

module.exports = {

    entry: {
        index: "./src/js/index.js",
        home: "./src/js/home.js"
	},

    output: {
        path: path.resolve("target"),
        filename: "js/[name].[hash].js"
    },

    mode: "development",

    module: {
        rules: [        
            {
                test: /\.css$/,
                use: [{
                    loader: miniCssExtractPlugin.loader,
                    options: {
                        publicPath: "../"
                    }
                }, "css-loader"]
            },
            {
                test: /\.(ttf|eot|svg|woff|woff2)$/,
                use: ["url-loader"]
            },
            {
                test: /\.vue$/,
                use: ["vue-loader"]
            },
            {
                test: /\.png$/,
                use: "url-loader?esModule=false"
            }
        ]
    },

    plugins: [
        new htmlWebpackPlugin({
            filename: "index.html",
            template: "./src/index.html",
            chunks: ["index"]
        }),
        new htmlWebpackPlugin({
            filename: "home.html",
            template: "./src/home.html",
            chunks: ["home"]
        }),
        new copyWebpackPlugin({
            patterns: [
                {
                    from: "./src/favicon.ico",
                    to: "favicon.ico"
                }
            ]
        }),
        new copyWebpackPlugin({
            patterns: [
                {
                    from: "./src/img",
                    to: "img"
                }
            ]
        }),
        new vueLoaderPlugin(),
        new miniCssExtractPlugin({
            filename: "css/[name].css"
        }),
        new copyWebpackPlugin({
            patterns: [
                {
                    from: "./src/lib",
                    to: "lib"
                }
            ]
        })
    ],

    devServer: {
        port: 10001,        
        disableHostCheck: true
    },

    performance: {
        hints: false
    }

};
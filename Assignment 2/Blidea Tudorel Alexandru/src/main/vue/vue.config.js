//const { defineConfig } = require('@vue/cli-service')
module.exports = {
  transpileDependencies: true,
  devServer: {
    proxy: "https://jsonplaceholder.typicode.com",
    changeOrigin: true,
  }
}

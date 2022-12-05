const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
  devServer: {
    proxy: 'http://localhost:8080'
  },
  lintOnSave: false,
  transpileDependencies: true
})

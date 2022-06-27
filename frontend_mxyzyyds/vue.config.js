module.exports = {
  lintOnSave: false,
  devServer: {
    disableHostCheck: true,
    proxy: {
      '/api': {
        target: 'http://xy.ydjsir.com.cn:8888',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    },


  }
}
